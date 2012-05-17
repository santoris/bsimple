package com.santoris.bsimple.client;

import com.github.gwtbootstrap.client.ui.Nav;
import com.github.gwtbootstrap.client.ui.config.Configurator;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.santoris.bsimple.client.purse.PursesPanel;
import com.santoris.bsimple.client.transactions.TransactionsPanel;
import com.santoris.bsimple.model.Dashboard;

public class DashboardPanel extends Composite {

	public void onModuleLoad() {
	}

	@UiField
	Nav nav;

	@UiField
	FlowPanel transactionsContainer;
	
	@UiField
	FlowPanel pursesContainer;
	
	private static DashboardPanelUiBinder uiBinder = GWT
			.create(DashboardPanelUiBinder.class);

	interface DashboardPanelUiBinder extends UiBinder<Widget, DashboardPanel> {
	}

	public DashboardPanel(Dashboard dashboard) {
		initWidget(uiBinder.createAndBindUi(this));

		pursesContainer.add(new PursesPanel());
		transactionsContainer.add(new TransactionsPanel());
	}

}
