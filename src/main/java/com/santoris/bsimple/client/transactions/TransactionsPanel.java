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
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
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
	protected FlowPanel cellPanel;

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
			transactionAsyncDataProvider.updateRowCount((int) page.getTotal(), true);
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
			// see http://stackoverflow.com/questions/17944/how-to-round-up-the-result-of-integer-division for an explanation of the following computation
			int pageNumber = (length + ShowMorePagerPanel.DEFAULT_INCREMENT - 1) / ShowMorePagerPanel.DEFAULT_INCREMENT - 1;
			int pageSize = ShowMorePagerPanel.DEFAULT_INCREMENT;
			updateList(pageNumber, pageSize);
		}
	}

	private AsyncDataProvider<Transaction> transactionAsyncDataProvider = new TransactionAsyncDataProvider();

	private final Map<Element, Account> accountByNavLinkElement = new HashMap<Element, Account>();

	private Account selectedAccount;

	private Dashboard dashboard;

	private ShowMorePagerPanel transactionListPanel;

	private List<Long> searchAccountIds;

	private Period searchedPeriod;
	
	private String searchedTransactionLabelPart;

	private DragAndDropCellList<Transaction> cellList;

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

			sb.appendHtmlConstant("<table>");
			sb.appendHtmlConstant("<tr><td style='font-size:95%;'>");
			sb.appendEscaped(value.getLabel());
			sb.appendHtmlConstant("</td></tr><tr><td>");
			sb.appendEscaped(value.getAmount().toString());
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
				System.out.println("Account: " + selectedAccount);
				System.out.println("Search text: " + searchTextBox.getText());
				initSearchFields();
				TransactionsPanel.this.cellList.setVisibleRangeAndClearData(new Range(0, ShowMorePagerPanel.DEFAULT_INCREMENT), true);
			}
		});

		initSearchFields();
		
		transactionListPanel = createList();
		cellPanel.add(transactionListPanel);
	}
	
	private void initSearchFields() {
		searchAccountIds = getAccountIds(selectedAccount);
		searchedPeriod = getPeriod();
		searchedTransactionLabelPart = this.searchTextBox.getText();
	}
	
	private List<Long> getAccountIds(Account account) {
		List<Long> accountIds = newArrayList();
		List<Account> accounts = account.getBankId() == null ? dashboard.getUser().getCustomer().getAccounts() : newArrayList(account);
		for (Account currentAccount : accounts) {
			accountIds.add(currentAccount.getBankId());
		}
		return accountIds;
	}

	private Period getPeriod() {
		Date startDate = new Date(2007 - 1900, 1 - 1, 1);
		Date endDate = new Date(2012 - 1900, 2 - 1, 1);
		return new Period(startDate, endDate);
	}
	
	private void updateList(final int pageNumber, final int pageSize) {
		PageRequest pageRequest = new PageRequest(pageNumber, pageSize);
		final String customerId = TransactionsPanel.this.dashboard
				.getUser().getCustomer().getId();
		TransactionsPanel.this.transactionService
				.findTransactionsByCustomerIdByAccountIdsByPeriod(
						customerId, searchAccountIds, searchedPeriod, searchedTransactionLabelPart, pageRequest,
						getTransactionsCallback);
	}

	private void updateHeading() {
		final StringBuilder html = new StringBuilder();
		int rowCount = TransactionsPanel.this.cellList == null ? 0 : TransactionsPanel.this.cellList.getRowCount();
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
		html.append(" au cours du mois de <b>mai</b>:");

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
		cellList = new DragAndDropCellList<Transaction>(
				transactionCell, TRANSACTION_KEY_PROVIDER);
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
}
