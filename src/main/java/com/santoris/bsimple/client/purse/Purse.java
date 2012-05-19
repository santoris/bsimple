package com.santoris.bsimple.client.purse;


import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;


public class Purse extends Composite {
	private Double amount = 0.0;

	private InlineLabel label;

	public Purse() {
		init();
	}

	private FlowPanel createPurseWrapper() {
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

		this.label = new InlineLabel();
		addAmountAndSetLabel(0.0);
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
		purseWrapper.addStyleName("pursePagerWrapper");

		return purseWrapper;
	}

	private void init() {
		FlowPanel purseWrapper = createPurseWrapper();
		initWidget(purseWrapper);
	}

	public void addAmountAndSetLabel(Double newAmount) {
		this.amount += newAmount;
		label.setText(Double.toString(amount));
	}
}
