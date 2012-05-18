/*
 *  Copyright 2012 GWT-Bootstrap
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.santoris.bsimple.client.purse;

import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.DraggableOptions.HelperType;
import gwtquery.plugins.droppable.client.DroppableOptions.AcceptFunction;
import gwtquery.plugins.droppable.client.events.DragAndDropContext;
import gwtquery.plugins.droppable.client.events.DropEvent;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.gwt.DragAndDropCellList;
import gwtquery.plugins.droppable.client.gwt.DroppableWidget;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.santoris.bsimple.client.ContactDatabase;
import com.santoris.bsimple.client.ContactDatabase.Category;
import com.santoris.bsimple.client.ContactDatabase.ContactInfo;

public class PursesPanel extends Composite {

	//@UiField
	//FlowPanel contactFormPanel;

	@UiField
	FlowPanel cellPanel;
	
	private static PursesPanelUiBinder uiBinder = GWT
			.create(PursesPanelUiBinder.class);

	interface PursesPanelUiBinder extends
			UiBinder<Widget, PursesPanel> {
	}

	public PursesPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	/**
	 * The images used for this example.
	 */
	static interface Images extends ClientBundle {
		public Images INSTANCE = GWT.create(Images.class);

		ImageResource contact();
	}

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
			// retrieve the purse linked to panel where the draggable was
			// dropped.
			DroppableWidget<Purse> droppabelWidget = (DroppableWidget<Purse>) event
					.getDroppableWidget();
			Purse purse = droppabelWidget.getOriginalWidget();
			purse.addAmountAndSetLabel(1.0);
		}

	}

	/*
	 * Form displaying info of the selected contact
	 */
	private ContactInfoForm contactForm;

	public void init() {

		// add the contact form
		contactForm = new ContactInfoForm();
		//RootPanel.get("contactForm").add(contactForm);
		//contactFormPanel.add(contactForm);

		// add the 4 lists for the 4 different categories
		//cellPanel.add(createList(Category.OTHERS));
		cellPanel.add(createList(Category.FAMILY));
		cellPanel.add(createList(Category.FRIENDS));
		cellPanel.add(createList(Category.BUSINESS));
	}

	private DraggableOptions createDraggableOptions() {
		DraggableOptions options = new DraggableOptions();
		// use a clone of the original cell as drag helper
		options.setHelper(HelperType.CLONE);
		// set the opacity of the drag helper
		options.setOpacity((float) 0.9);
		// append the drag helper to the body element
		options.setAppendTo("body");
		return options;

	}

	private FlowPanel createPurse() {
		final FlowPanel purse = new FlowPanel();
		purse.setStyleName("pursePager");

		final FlowPanel outerPurse = new FlowPanel();
		outerPurse.setStyleName("outer-pursePager");
		purse.add(outerPurse);
		
		final FlowPanel middlePurse = new FlowPanel();
		middlePurse.setStyleName("middle-pursePager");
		purse.add(middlePurse);
		
		// text to put here
		final FlowPanel innerPurse = new FlowPanel();
		innerPurse.setStyleName("inner-pursePager");
		purse.add(innerPurse);
		
		InlineLabel label = new InlineLabel();
		label.setText("1");
		innerPurse.add(label);
		
		purse.addDomHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				outerPurse.addStyleName("hover");
				middlePurse.addStyleName("hover");
			}
		}, MouseOverEvent.getType());
		
		purse.addDomHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				outerPurse.removeStyleName("hover");
				middlePurse.removeStyleName("hover");
				
			}
		}, MouseOutEvent.getType());

		 FlowPanel purseWrapper = new FlowPanel();
		 purseWrapper.add(purse);
		 purseWrapper.addStyleName("pagerWrapper");
	   
		return purseWrapper;
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
	private DroppableWidget<Purse> createList(
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
						contactForm.setContact(selectionModel
								.getSelectedObject());
					}
				});

		ContactDatabase.get().addDataDisplay(cellList, category);


		

		
		//ShowMorePagerPanel pagerPanel = new ShowMorePagerPanel(category);
		Purse purse = new Purse();
		//pagerPanel.setDisplay(cellList);

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
//				// retrieve the dragging ContactInfo
//				ContactInfo draggedContact = ctx.getDraggableData();
//				Category dragCategory = draggedContact.getCategory();
//				// accept only contact coming from an other panel.
//				return dragCategory != category;
				return true;
			}

		});

		return droppabelPanel;
	}
}
