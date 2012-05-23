package com.santoris.bsimple.client;

import java.util.Date;
import java.util.List;

import com.github.gwtbootstrap.client.ui.Nav;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.base.UnorderedList;
import static com.google.common.collect.Lists.newArrayList;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
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
	
	@UiField
	protected HasText startDateLabel;

	@UiField
	protected HasText endDateLabel;
	
	@UiField
	protected UnorderedList startDateList;
	
	@UiField
	protected UnorderedList endDateList;
	
	private static DashboardPanelUiBinder uiBinder = GWT
			.create(DashboardPanelUiBinder.class);

	interface DashboardPanelUiBinder extends UiBinder<Widget, DashboardPanel> {
	}
	
	private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("MMMM yyyy");
	
	private class StartDateClickHandler implements ClickHandler {
		private Date date;
		
		public StartDateClickHandler(Date date) {
			this.date = date;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			startDateLabel.setText(formatDate(date));
		}
	}

	private class EndDateClickHandler implements ClickHandler {
		private Date date;
		
		public EndDateClickHandler(Date date) {
			this.date = date;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			endDateLabel.setText(formatDate(date));
		}
	}
	
	public DashboardPanel(Dashboard dashboard) {
		initWidget(uiBinder.createAndBindUi(this));
		
		List<Date> dates = newArrayList(new Date(2012 - 1900, 4 - 1, 1), new Date(2012 - 1900, 5 - 1, 1));
		
		for (Date date : dates) {
			NavLink startDateLink = new NavLink(formatDate(date), "#");
			startDateLink.addClickHandler(new StartDateClickHandler(date));
			startDateList.add(startDateLink);
			
			NavLink endDateLink = new NavLink(formatDate(date), "#");
			endDateLink.addClickHandler(new EndDateClickHandler(date));
			endDateList.add(endDateLink);
		}

		startDateLabel.setText(formatDate(dates.get(0)));
		endDateLabel.setText(formatDate(dates.get(dates.size() - 1)));

		pursesContainer.add(new PursesPanel());
		transactionsContainer.add(new TransactionsPanel(dashboard));
	}

	private static String formatDate(Date date) {
		return DATE_FORMAT.format(date).toLowerCase();
	}
}
