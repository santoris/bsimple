package com.santoris.bsimple.client;

import com.github.gwtbootstrap.client.ui.CodeBlock;
import com.santoris.bsimple.client.resources.CustomResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;


public class Forms extends Composite {

	private static FormsUiBinder uiBinder = GWT.create(FormsUiBinder.class);

	@UiField CodeBlock basicFormCode;
	@UiField CodeBlock searchFormCode;
	@UiField CodeBlock inlineFormCode;
	
	interface FormsUiBinder extends UiBinder<Widget, Forms> {
	}

	public Forms() {
		initWidget(uiBinder.createAndBindUi(this));
		basicFormCode.setHTML(CustomResources.RESOURCES.basicFormCode().getText());
		searchFormCode.setHTML(CustomResources.RESOURCES.searchFormCode().getText());
		inlineFormCode.setHTML(CustomResources.RESOURCES.inlineFormCode().getText());
	}

}
