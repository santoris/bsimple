package com.santoris.bsimple.client.transactions;

import static com.google.common.collect.Lists.newArrayList;
import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.DraggableOptions.HelperType;
import gwtquery.plugins.droppable.client.gwt.DragAndDropCellList;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Legend;
import com.github.gwtbootstrap.client.ui.base.TextBox;
import com.github.gwtbootstrap.client.ui.base.UnorderedList;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.resources.client.CssResource.ImportedWithPrefix;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList.Resources;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.santoris.bsimple.client.purse.Purse;
import com.santoris.bsimple.client.purse.PursesPanel;
import com.santoris.bsimple.client.service.TransactionService;
import com.santoris.bsimple.client.service.TransactionServiceAsync;
import com.santoris.bsimple.client.widget.DropdownHTMLEntry;
import com.santoris.bsimple.model.Account;
import com.santoris.bsimple.model.Dashboard;
import com.santoris.bsimple.model.IBAN;
import com.santoris.bsimple.model.Period;
import com.santoris.bsimple.model.Transaction;
import com.santoris.bsimple.page.Page;
import com.santoris.bsimple.page.PageRequest;

public class TransactionsPanel extends Composite {

	private static final String ALL_ACCOUNTS_LABEL = "Tous mes comptes";

	@UiField
	protected Legend transactionListsDescription;

	@UiField
	protected HasText accountButtonLabel;

	@UiField
	protected UnorderedList accountsList;

	@UiField
	protected TextBox searchTextBox;

	@UiField
	protected Button searchButton;

	@UiField
	protected FlowPanel transactionsListPanel;

	private static TransactionsPanelUiBinder uiBinder = GWT
			.create(TransactionsPanelUiBinder.class);

	interface TransactionsPanelUiBinder extends
			UiBinder<Widget, TransactionsPanel> {
	}

	public TransactionsPanel(Dashboard dashboard) {
		initWidget(uiBinder.createAndBindUi(this));
		init(dashboard);
	}

	protected interface AccountListEntryTemplates extends SafeHtmlTemplates {
		@Template("<a href=\"javascript:;\" style=\"width: 350px; float: left; padding: 0px 0px;\"><label style=\"float: left; width: 100%;\"><span style=\"margin-right:10px; float:left; padding-left:15px;\"><b>"
				+ "{0}</b></span><span style=\"float:right; padding: 0 15px\">"
				+ "{1}</span></label></a>")
		SafeHtml entry(String accountLabel, String accountIBAN);
	}

	private static final AccountListEntryTemplates ACCOUNT_LIST_ENTRY_TEMPLATES = GWT
			.create(AccountListEntryTemplates.class);

	protected interface TransactionEntryTemplates extends SafeHtmlTemplates {
		@Template(" <div class=\"badge badge-{0}\" style=\"float: right;font-size:13px;margin-top:1px; margin-right:2px\">{1}</div>")
		SafeHtml amount(String amountColorCssSuffix, String amount);
	}
	
	private static final TransactionEntryTemplates TRANSACTION_ENTRY_TEMPLATES = GWT
			.create(TransactionEntryTemplates.class);

	/**
	 * The key provider that provides the unique ID of a transaction.
	 */
	public static final ProvidesKey<Transaction> TRANSACTION_KEY_PROVIDER = new ProvidesKey<Transaction>() {
		public Object getKey(Transaction item) {
			return item == null ? null : item.getId();
		}
	};
	
	private final TransactionServiceAsync transactionService = GWT
			.create(TransactionService.class);

	private AsyncCallback<Page<Transaction>> getTransactionsCallback = new AsyncCallback<Page<Transaction>>() {

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Page<Transaction> page) {
			final int start = page.getPageRequest().getPageNumber()
					* page.getPageRequest().getPageSize();
			transactionAsyncDataProvider.updateRowCount((int) page.getTotal(),
					true);
			transactionAsyncDataProvider
					.updateRowData(start, page.getContent());
			updateHeading();
		}

	};

	private class TransactionAsyncDataProvider extends
			AsyncDataProvider<Transaction> {

		@Override
		protected void onRangeChanged(HasData<Transaction> display) {
			final Range range = display.getVisibleRange();
			System.out.println("*** range " + range + " " + new Date());
			int length = range.getLength();
			// see
			// http://stackoverflow.com/questions/17944/how-to-round-up-the-result-of-integer-division
			// for an explanation of the following computation
			int pageNumber = (length + ShowMorePagerPanel.DEFAULT_INCREMENT - 1)
					/ ShowMorePagerPanel.DEFAULT_INCREMENT - 1;
			int pageSize = ShowMorePagerPanel.DEFAULT_INCREMENT;
			callTheRPCServiceAndUpdateList(pageNumber, pageSize);
		}
	}

	private final static DateTimeFormat TRANSACTION_DATE_FORMAT = DateTimeFormat.getFormat("d MMM yyyy");

	private static final DateTimeFormat PERIOD_DATE_FORMAT = DateTimeFormat.getFormat("MMM yyyy");
	
	private AsyncDataProvider<Transaction> transactionAsyncDataProvider = new TransactionAsyncDataProvider();

	private final Map<Element, Account> accountByNavLinkElement = new HashMap<Element, Account>();

	private Account selectedAccount;

	private Dashboard dashboard;

	private ShowMorePagerPanel transactionListPanel;

	private List<Long> searchAccountIds;

	private String searchedTransactionLabelPart;

	private DragAndDropCellList<Transaction> cellList;

	private Period period;

	/**
	 * The Cell used to render a {@link Transaction}.
	 */
	private static class TransactionCell extends AbstractCell<Transaction> {
		@Override
		public void render(Context context, Transaction value,
				SafeHtmlBuilder sb) {
			// Value can be null, so do a null check..
			if (value == null) {
				return;
			}

			String date =  TRANSACTION_DATE_FORMAT.format(value.getDate());
			
			sb.appendHtmlConstant("<table><tr><td>");
			sb.appendHtmlConstant("<div style=\"float:left; border: 1px solid #DDDDDD; padding: 3px; margin-top: -3px;margin-left: -2px;\">");
			sb.appendHtmlConstant("    <div style=\"width:300px; float:left;\">");
			sb.appendHtmlConstant("      <div style=\"margin-top:1px; \">");
			sb.appendHtmlConstant("          <div style=\"float:left; width:220px\"><b>");
			sb.appendEscaped(value.getLabel().toLowerCase());
			sb.appendHtmlConstant("</b></div>");
			sb.appendHtmlConstant("        <div style=\"text-align:right; color:#555\">");
			sb.appendHtmlConstant(date);
			sb.appendHtmlConstant("        </div>");
			sb.appendHtmlConstant("      </div>");
			sb.appendHtmlConstant("    <div id=\"categories\" style=\"width:300px; float:left;\">");
			
			List<Purse> purses = PursesPanel.INSTANCE.getPursesByTransactionId(value.getId());
			if (purses != null) {
				for (Purse purse : purses) {
					sb.appendHtmlConstant("      <div class=\"label label-info\" style=\"float: left; margin-top:1px; margin-right:2px\">");
					sb.appendEscaped(purse.getCategoryName());
					sb.appendHtmlConstant("      </div>");
				}
			}


			sb.appendHtmlConstant(TRANSACTION_ENTRY_TEMPLATES.amount(value.getAmount().signum() < 0 ? "important" : "success", value.getAmount().setScale(2).toString()).asString());

			sb.appendHtmlConstant("    </div>");
			sb.appendHtmlConstant("</div>");
			sb.appendHtmlConstant("</td></tr></table>");
		}
	}

	public void init(Dashboard dashboard) {
		this.dashboard = dashboard;

		initializeAccountsListBox(dashboard.getUser().getCustomer()
				.getAccounts());

		searchButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				initSearchFields();
				refreshCellList();
			}
		});

		initSearchFields();

		transactionListPanel = createList();
		transactionsListPanel.add(transactionListPanel);
	}

	private void initSearchFields() {
		searchAccountIds = getAccountIds(selectedAccount);
		searchedTransactionLabelPart = this.searchTextBox.getText();
	}
	
	private void refreshCellList() {
		TransactionsPanel.this.cellList.setVisibleRangeAndClearData(
				new Range(0, ShowMorePagerPanel.DEFAULT_INCREMENT),
				true);
	}

	private List<Long> getAccountIds(Account account) {
		List<Long> accountIds = newArrayList();
		List<Account> accounts = account.getBankId() == null ? dashboard
				.getUser().getCustomer().getAccounts() : newArrayList(account);
		for (Account currentAccount : accounts) {
			accountIds.add(currentAccount.getBankId());
		}
		return accountIds;
	}

	private void callTheRPCServiceAndUpdateList(final int pageNumber, final int pageSize) {
		PageRequest pageRequest = new PageRequest(pageNumber, pageSize);
		final String customerId = TransactionsPanel.this.dashboard.getUser()
				.getCustomer().getId();
		TransactionsPanel.this.transactionService
				.findTransactionsByCustomerIdByAccountIdsByPeriod(customerId,
						searchAccountIds, period,
						searchedTransactionLabelPart, pageRequest,
						getTransactionsCallback);
	}
	
	private boolean isOnlyOneMonthOfDifference(Date startDate, Date endDate) {
		return startDate.getYear() * 12 + startDate.getMonth() + 1 == endDate.getYear() * 12 + endDate.getMonth();
	}

	private void updateHeading() {
		final StringBuilder html = new StringBuilder();
		int rowCount = TransactionsPanel.this.cellList == null ? 0
				: TransactionsPanel.this.cellList.getRowCount();
		html.append("<b>");
		html.append(rowCount);
		html.append("</b> transactions appartenant à <b>");
		html.append(selectedAccount.getLabel().toLowerCase());
		html.append("</b>");
		if (!searchTextBox.getText().isEmpty()) {
			html.append(" contenant <b>");
			html.append(searchTextBox.getText());
		}
		html.append("</b>");
		
		final String periodSentencePart;
		if (isOnlyOneMonthOfDifference(period.getStartDate(), period.getEndDate())) {
			String dateLabel = PERIOD_DATE_FORMAT.format(period.getStartDate());
			periodSentencePart = " au cours du mois de <b>" + dateLabel + "</b>:";
		} else {
			String startDateLabel = PERIOD_DATE_FORMAT.format(period.getStartDate());
			String endDateLabel = PERIOD_DATE_FORMAT.format(period.getEndDate());		
			periodSentencePart = " durant la période de <b>" + startDateLabel + "</b> à <b>" + endDateLabel + "</b>:";
		}
		html.append(periodSentencePart);

		this.transactionListsDescription.getElement().setInnerHTML(
				html.toString());
	}

	private void selectAccount(final Account account) {
		selectedAccount = account;
		accountButtonLabel.setText(selectedAccount.getLabel().toLowerCase());
	}

	private void initializeAccountsListBox(List<Account> accounts) {
		Account fakeAllAccount = new Account();
		fakeAllAccount.setLabel(ALL_ACCOUNTS_LABEL);
		IBAN iban = new IBAN();
		iban.setCountryCode("");
		iban.setCheckDigits("");
		iban.setBban("");
		fakeAllAccount.setIban(iban);

		selectAccount(fakeAllAccount);

		List<Account> displayedAccounts = newArrayList();
		displayedAccounts.add(fakeAllAccount);
		displayedAccounts.addAll(accounts);
		for (Account account : displayedAccounts) {
			SafeHtml html = ACCOUNT_LIST_ENTRY_TEMPLATES.entry(account
					.getLabel().toLowerCase(), account.getIban().getLabel());
			DropdownHTMLEntry entry = new DropdownHTMLEntry(html.asString());
			accountByNavLinkElement.put(entry.getElement(), account);
			accountsList.add(entry);
			entry.addDomHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					Account account = accountByNavLinkElement.get(event
							.getRelativeElement());
					selectAccount(account);
				}
			}, ClickEvent.getType());
		}
	}

	private DraggableOptions createDraggableOptions() {
		DraggableOptions options = new DraggableOptions();
		// use a clone of the original cell as drag helper
		options.setHelper(HelperType.CLONE);
		// set the opacity of the drag helper
		options.setOpacity((float) 0.9);
		options.setZIndex(1000);
		// append the drag helper to the body element
		options.setAppendTo("body");
		return options;

	}

	private ShowMorePagerPanel createList() {

		// Create a ConcactCel
		TransactionCell transactionCell = new TransactionCell();

		// Create a drag and drop cell list
		cellList = new DragAndDropCellList<Transaction>(transactionCell,
				TRANSACTION_KEY_PROVIDER);
		// The cell of this cell list are only draggable
		cellList.setCellDraggableOnly();
		// setup the drag operation
		cellList.setDraggableOptions(createDraggableOptions());

		cellList.setPageSize(ShowMorePagerPanel.DEFAULT_INCREMENT);
		cellList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		final SingleSelectionModel<Transaction> selectionModel = new SingleSelectionModel<Transaction>(
				TRANSACTION_KEY_PROVIDER);
		cellList.setSelectionModel(selectionModel);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						// contactForm.setContact(selectionModel.getSelectedObject());
					}
				});

		this.transactionAsyncDataProvider.addDataDisplay(cellList);

		ShowMorePagerPanel pagerPanel = new ShowMorePagerPanel();
		pagerPanel.setDisplay(cellList);

		return pagerPanel;
	}

	public void onPeriodChanged(Period period) {
		this.period = period;
		refreshCellList();
	}

}
