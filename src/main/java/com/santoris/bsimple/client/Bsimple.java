package com.santoris.bsimple.client;

import java.util.HashMap;
import java.util.Map;

import com.github.gwtbootstrap.client.ui.base.TextBox;
import com.github.gwtbootstrap.client.ui.config.Configurator;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.santoris.bsimple.client.service.DashboardService;
import com.santoris.bsimple.client.service.DashboardServiceAsync;
import com.santoris.bsimple.client.service.UserService;
import com.santoris.bsimple.client.service.UserServiceAsync;
import com.santoris.bsimple.model.Dashboard;

public class Bsimple extends Composite implements EntryPoint {

	private static final String[][] CUSTOMERS = { { "1000000", "Theo" },
			{ "1000001", "Antonin" }, { "1000002", "Lea" },
			{ "1500000", "Martin" } };

	private final Map<String, Integer> customerListBoxIndexByBankCustomerId = new HashMap<String, Integer>();

	@UiField
	HTMLPanel loadingPanel;
	
	@UiField
	Image loaderImage;
	
	@UiField
	protected FocusWidget connectionButton;

	@UiField
	protected TextBox loginTextBox;

	@UiField
	protected ListBox customerListBox;
	
	private boolean newLogin;

	private final UserServiceAsync userService = GWT.create(UserService.class);

	private final DashboardServiceAsync dashboardService = GWT.create(DashboardService.class);

	private ClickHandler connectionButtonClickHandler = new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			connect();
		}
		
	};

	private KeyUpHandler loginKeyUpHandler = new KeyUpHandler() {

		@Override
		public void onKeyUp(KeyUpEvent event) {
			final String login = loginTextBox.getText();
			if (login.isEmpty()) {
				connectionButton.setEnabled(false);
			} else {
				connectionButton.setEnabled(true);
				userService.getBankCustomerIdByLogin(login,
						getBankCustomerIdByLoginCallback);
			}
		}

	};

	private AsyncCallback<Long> getBankCustomerIdByLoginCallback = new AsyncCallback<Long>() {

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Long customerId) {
			if (customerId == null) {
				newLogin = true;
				customerListBox.setEnabled(true);
			} else {
				newLogin = false;
				customerListBox.setEnabled(false);
				Integer index = customerListBoxIndexByBankCustomerId.get(String.valueOf(customerId));
				customerListBox.setSelectedIndex(index);
			}
		}

	};

	private AsyncCallback<Dashboard> getDashboardCallback = new AsyncCallback<Dashboard>() {

		@Override
		public void onFailure(Throwable caught) {
			loadingPanel.setVisible(false);
		}

		@Override
		public void onSuccess(Dashboard dashboard) {
			loadingPanel.setVisible(false);
			displayDashboard(dashboard);
		}
		
	};

	public void onModuleLoad() {
	}

	private static BsimpleUiBinder uiBinder = GWT.create(BsimpleUiBinder.class);

	interface BsimpleUiBinder extends UiBinder<Widget, Bsimple> {
	}

	public Bsimple() {

		Configurator configurator = GWT.create(Configurator.class);

		GWT.log(String.valueOf(configurator.hasResponsiveDesign()));

		initWidget(uiBinder.createAndBindUi(this));

		initializeCustomerListBox();

		connectionButton.addClickHandler(connectionButtonClickHandler);
		
		loginTextBox.addKeyUpHandler(loginKeyUpHandler);
		
		RootPanel.get("content").add(this);
		
		//dashboardService.getDashboard("thierry", Long.valueOf("1000001"), getDashboardCallback);
	}

	private void initializeCustomerListBox() {
		Integer index = 0;
		for (String[] customer : CUSTOMERS) {
			String bankCustomerId = customer[0];
			String customerName = customer[1];
			customerListBox.addItem(customerName, bankCustomerId);
			customerListBoxIndexByBankCustomerId.put(bankCustomerId,
					index);
			index++;
		}
	}

	private void connect() {
		final String login = loginTextBox.getText();

		final int customerIndex = customerListBox.getSelectedIndex();
		final String customerId = customerListBox.getValue(customerIndex);
		
		if (this.newLogin) {
			loadingPanel.setVisible(true);
			loaderImage.getElement().focus();
		}

		dashboardService.getDashboard(login, Long.valueOf(customerId), getDashboardCallback);
	}


	private void displayDashboard(Dashboard dashboard) {
		DashboardPanel dashBoardPanel = new DashboardPanel(dashboard);
		RootPanel.get("content").remove(Bsimple.this);
		RootPanel.get("content").add(dashBoardPanel);
	}
}
