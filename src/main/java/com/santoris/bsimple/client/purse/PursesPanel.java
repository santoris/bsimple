package com.santoris.bsimple.client.purse;

import gwtquery.plugins.droppable.client.DroppableOptions.AcceptFunction;
import gwtquery.plugins.droppable.client.events.DragAndDropContext;
import gwtquery.plugins.droppable.client.events.DropEvent;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.gwt.DragAndDropCellList;
import gwtquery.plugins.droppable.client.gwt.DroppableWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class PursesPanel extends Composite {

	@UiField
	FlowPanel cellPanel;

	private static PursesPanelUiBinder uiBinder = GWT
			.create(PursesPanelUiBinder.class);

	interface PursesPanelUiBinder extends UiBinder<Widget, PursesPanel> {
	}

	public PursesPanel() {
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
			purse.addAmountAndSetLabel(1.0);
		}

	}

	public void init() {
		cellPanel.add(createPurse());
		cellPanel.add(createPurse());
		cellPanel.add(createPurse());
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
	private DroppableWidget<Purse> createPurse() {
		Purse purse = new Purse();

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
}
