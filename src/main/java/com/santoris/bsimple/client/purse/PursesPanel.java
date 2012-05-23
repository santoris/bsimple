package com.santoris.bsimple.client.purse;

import static com.google.gwt.query.client.GQuery.$;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gwtquery.plugins.droppable.client.DroppableOptions.AcceptFunction;
import gwtquery.plugins.droppable.client.events.DragAndDropContext;
import gwtquery.plugins.droppable.client.events.DropEvent;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.gwt.DragAndDropCellList;
import gwtquery.plugins.droppable.client.gwt.DroppableWidget;

import com.github.gwtbootstrap.client.ui.Alert;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Modal;
import com.github.gwtbootstrap.client.ui.base.TextBox;
import static com.google.common.collect.Lists.newArrayList;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.santoris.bsimple.model.Period;
import com.santoris.bsimple.model.Transaction;


public class PursesPanel extends Composite {

	@UiField
	FlowPanel cellPanel;
	
	@UiField
	Button createCategoryButton;

	@UiField
	Modal createCategoryModal;
	
	@UiField
	TextBox categoryNameTextBox;
	
	@UiField
	Button createCategorieValidateButton;
	
	public static PursesPanel INSTANCE;
	
	private List<Purse> purses = newArrayList();
	
	private Map<String, List<Purse>> pursesByTransactionId = new HashMap<String, List<Purse>>();

	private KeyUpHandler categoryNameKeyUpHandler = new KeyUpHandler() {

		@Override
		public void onKeyUp(KeyUpEvent event) {
			final String login = categoryNameTextBox.getText();
			if (login.isEmpty()) {
				createCategorieValidateButton.setEnabled(false);
			} else {
				createCategorieValidateButton.setEnabled(true);
			}
		}

	};
	
	private static PursesPanelUiBinder uiBinder = GWT
			.create(PursesPanelUiBinder.class);


	interface PursesPanelUiBinder extends UiBinder<Widget, PursesPanel> {
	}

	public PursesPanel() {
		INSTANCE = this;
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	/**
	 * Object handling the drop event.
	 * 
	 */
	private class DropHandler implements DropEventHandler {

		@SuppressWarnings("unchecked")
		public void onDrop(DropEvent event) {
			// retrieve the purse linked to panel where the draggable was
			// dropped.
			DroppableWidget<Purse> droppabelWidget = (DroppableWidget<Purse>) event
					.getDroppableWidget();
			Purse purse = droppabelWidget.getOriginalWidget();
			Element transactionEntryElement = event.getDragDropContext().getDraggable();
			Transaction transaction = event.getDragDropContext().getDraggableData();
			purse.addTransaction(transaction, transactionEntryElement);
		}

	}

	public void init() {
		createCategoryButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				createCategoryModal.show();
			}
		});
		
		categoryNameTextBox.addKeyUpHandler(categoryNameKeyUpHandler);

		createCategorieValidateButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				createCategoryModal.hide();
				final String categoryName = categoryNameTextBox.getText();
				categoryNameTextBox.setText("");
				HTMLPanel htmlPanel = new HTMLPanel("");
				htmlPanel.add(createPurse(categoryName));
				htmlPanel.addStyleName("purseContainer");
				cellPanel.add(htmlPanel);
			}
		});
	}

	/**
	 * Code coming from GWT showcase.
	 * 
	 * We just use a {@link DragAndDropCellList} instead of a {@link CellList}
	 * and make the pager panel droppable.
	 * @param categoryName 
	 * 
	 * @param contactForm
	 * 
	 * @return
	 */
	private DroppableWidget<Purse> createPurse(String categoryName) {
		Purse purse = new Purse(categoryName, this);
		this.purses.add(purse);

		// make the pager panel droppable.
		DroppableWidget<Purse> droppabelPanel = new DroppableWidget<Purse>(
				purse);
		// setup the drop operation
		droppabelPanel.setDroppableHoverClass("orange-border");
		droppabelPanel.setActiveClass("yellow-border");
		droppabelPanel.addDropHandler(new DropHandler());
		// use an AcceptFunction to accept only draggable coming from an other
		// panel
		droppabelPanel.setAccept(new AcceptFunction() {

			public boolean acceptDrop(DragAndDropContext ctx) {
				// // retrieve the dragging ContactInfo
				// ContactInfo draggedContact = ctx.getDraggableData();
				// Category dragCategory = draggedContact.getCategory();
				// // accept only contact coming from an other panel.
				// return dragCategory != category;
				return true;
			}

		});

		return droppabelPanel;
	}
	
	public void updatePursesByTransactionId(Purse purse, Transaction transaction) {
		List<Purse> purses = this.pursesByTransactionId.get(transaction.getId());
		if (purses == null) {
			purses = newArrayList();
			pursesByTransactionId.put(transaction.getId(), purses);
		}
		purses.add(purse);
	}
	
	public List<Purse> getPursesByTransactionId(String transactionId) {
		return pursesByTransactionId.get(transactionId);
	}

	public void onPeriodChanged(Period period) {
		for (Purse purse : purses) {
			purse.onPeriodChanged(period);
		}
	}
}
