package com.santoris.bsimple.client.transactions;

import static com.google.common.collect.Lists.newArrayList;
import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.DraggableOptions.HelperType;
import gwtquery.plugins.droppable.client.DroppableOptions.AcceptFunction;
import gwtquery.plugins.droppable.client.events.DragAndDropContext;
import gwtquery.plugins.droppable.client.events.DropEvent;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.gwt.DragAndDropCellList;
import gwtquery.plugins.droppable.client.gwt.DroppableWidget;

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
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.santoris.bsimple.client.ContactDatabase;
import com.santoris.bsimple.client.ContactDatabase.Category;
import com.santoris.bsimple.client.ContactDatabase.ContactInfo;
import com.santoris.bsimple.client.widget.DropdownHTMLEntry;
import com.santoris.bsimple.model.Account;
import com.santoris.bsimple.model.Dashboard;
import com.santoris.bsimple.model.IBAN;

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

	/**
	 * The images used for this example.
	 */
	static interface Images extends ClientBundle {
		public Images INSTANCE = GWT.create(Images.class);

		ImageResource contact();
	}

	protected interface AccountListEntryTemplates extends SafeHtmlTemplates {
		@Template("<a href=\"javascript:;\" style=\"width: 350px; float: left; padding: 0px 0px;\"><label style=\"float: left; width: 100%;\"><span style=\"margin-right:10px; float:left; padding-left:15px;\"><b>"
				+ "{0}</b></span><span style=\"float:right; padding: 0 15px\">"
				+ "{1}</span></label></a>")
		SafeHtml entry(String accountLabel, String accountIBAN);
	}

	private static final AccountListEntryTemplates ACCOUNT_LIST_ENTRY_TEMPLATES = GWT
			.create(AccountListEntryTemplates.class);

	private final Map<Element, Account> accountByNavLinkElement = new HashMap<Element, Account>();

	private Account selectedAccount;

	/**
	 * The Cell used to render a {@link ContactInfo}. Code coming from the GWT
	 * showcase
	 * 
	 */
	private static class ContactCell extends AbstractCell<ContactInfo> {

		/**
		 * The html of the image used for contacts.
		 * 
		 */
		private final String imageHtml;

		public ContactCell(ImageResource image) {
			this.imageHtml = AbstractImagePrototype.create(image).getHTML();
		}

		@Override
		public void render(com.google.gwt.cell.client.Cell.Context context,
				ContactInfo value, SafeHtmlBuilder sb) {
			// Value can be null, so do a null check..
			if (value == null) {
				return;
			}

			sb.appendHtmlConstant("<table>");

			// Add the contact image.
			sb.appendHtmlConstant("<tr><td rowspan='3'><div>");
			sb.appendHtmlConstant(imageHtml);
			sb.appendHtmlConstant("</div></td>");

			// Add the name and address.
			sb.appendHtmlConstant("<td style='font-size:95%;'>");
			sb.appendEscaped(value.getFullName());
			sb.appendHtmlConstant("</td></tr><tr><td>");
			sb.appendEscaped(value.getAddress());
			sb.appendHtmlConstant("</td></tr></table>");
		}
	}

	/**
	 * Object handling the drop event.
	 * 
	 * @author Julien Dramaix (julien.dramaix@gmail.com)
	 * 
	 */
	private class DropHandler implements DropEventHandler {

		@SuppressWarnings("unchecked")
		public void onDrop(DropEvent event) {
			// retrieve the category linked to panel where the draggable was
			// dropped.
			DroppableWidget<ShowMorePagerPanel> droppabelWidget = (DroppableWidget<ShowMorePagerPanel>) event
					.getDroppableWidget();
			ShowMorePagerPanel dropPanel = droppabelWidget.getOriginalWidget();
			Category dropCategory = dropPanel.getCategory();

			// retrieve the ContactInfo associated with the draggable element
			ContactInfo draggedContact = event.getDraggableData();
			Category oldCategory = draggedContact.getCategory();

			if (oldCategory == dropCategory) {
				return;
			}

			// change the category of the contact that was being dragged and
			// prevent
			// the data source.
			draggedContact.setCategory(dropCategory);
			ContactDatabase.get().moveContact(draggedContact, oldCategory);
		}

	}

	public void init(Dashboard dashboard) {
		initializeAccountsListBox(dashboard.getUser().getCustomer()
				.getAccounts());
		
		searchButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				System.out.println("Account: " + selectedAccount);
				System.out.println("Search text: " + searchTextBox.getText());
				updateHeading();
			}
		});
		
		cellPanel.add(createList(Category.OTHERS));
	}
	
	private void updateHeading() {
		final StringBuilder html = new StringBuilder();
		html.append("transactions appartenant à <b>");
		html.append(selectedAccount.getLabel().toLowerCase());
		html.append("</b>");
		if (!searchTextBox.getText().isEmpty()) {
			html.append(" contenant <b>");
			html.append(searchTextBox.getText());
		}
		html.append("</b>");
		html.append(" au cours du mois de <b>mai</b>:");

		this.transactionListsDescription.getElement().setInnerHTML(html.toString());
	}
	
	private void selectAccount(final Account account) {
		selectedAccount = account;
		accountButtonLabel.setText(selectedAccount.getLabel().toLowerCase());
		updateHeading();
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
			SafeHtml html = ACCOUNT_LIST_ENTRY_TEMPLATES.entry(account.getLabel().toLowerCase(), account.getIban().getLabel());
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

	/**
	 * Code coming from GWT showcase.
	 * 
	 * We just use a {@link DragAndDropCellList} instead of a {@link CellList}
	 * and make the pager panel droppable.
	 * 
	 * @param contactForm
	 * 
	 * @return
	 */
	private DroppableWidget<ShowMorePagerPanel> createList(
			final Category category) {

		// Create a ConcactCel
		ContactCell contactCell = new ContactCell(Images.INSTANCE.contact());

		// Create a drag and drop cell list
		DragAndDropCellList<ContactInfo> cellList = new DragAndDropCellList<ContactInfo>(
				contactCell, ContactDatabase.ContactInfo.KEY_PROVIDER);
		// The cell of this cell list are only draggable
		cellList.setCellDraggableOnly();
		// setup the drag operation
		cellList.setDraggableOptions(createDraggableOptions());

		cellList.setPageSize(30);
		cellList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		final SingleSelectionModel<ContactInfo> selectionModel = new SingleSelectionModel<ContactInfo>(
				ContactDatabase.ContactInfo.KEY_PROVIDER);
		cellList.setSelectionModel(selectionModel);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						// contactForm.setContact(selectionModel.getSelectedObject());
					}
				});

		ContactDatabase.get().addDataDisplay(cellList, category);

		ShowMorePagerPanel pagerPanel = new ShowMorePagerPanel(category);
		pagerPanel.setDisplay(cellList);

		// make the pager panel droppable.
		DroppableWidget<ShowMorePagerPanel> droppabelPanel = new DroppableWidget<ShowMorePagerPanel>(
				pagerPanel);
		// setup the drop operation
		// droppabelPanel.setDroppableHoverClass("orange-border");
		// droppabelPanel.setActiveClass("yellow-border");
		droppabelPanel.addDropHandler(new DropHandler());
		// use an AcceptFunction to accept only draggable coming from an other
		// panel
		droppabelPanel.setAccept(new AcceptFunction() {

			public boolean acceptDrop(DragAndDropContext ctx) {
				// retrieve the dragging ContactInfo
				ContactInfo draggedContact = ctx.getDraggableData();
				Category dragCategory = draggedContact.getCategory();
				// accept only contact coming from an other panel.
				return dragCategory != category;
			}

		});

		return droppabelPanel;
	}
}
