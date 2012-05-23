package com.santoris.bsimple.client;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.github.gwtbootstrap.client.ui.Nav;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.base.UnorderedList;
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
import com.santoris.bsimple.model.Period;

public class DashboardPanel extends Composite {

	private static final int NB_OF_AVAILABLE_MONTHS = 12;

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
	
	private PursesPanel pursesPanel;

	private TransactionsPanel transactionsPanel;
	
	private Date selectedStartDate;

	private Date selectedEndDate;

	private class StartDateClickHandler implements ClickHandler {
		private final Date date;
		private final int index;
		
		public StartDateClickHandler(Date date, int index) {
			this.date = date;
			this.index = index;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			startDateLabel.setText(formatDate(date));
			selectedStartDate = date;
			
			if (selectedStartDate.after(selectedEndDate) || selectedStartDate.equals(selectedEndDate)) {
				selectedEndDate = addOneMonth(selectedStartDate);
				endDateLabel.setText(formatDate(selectedStartDate));
			}
			
			onPeriodChanged();
		}
	}

	private class EndDateClickHandler implements ClickHandler {
		private final Date date;
		
		public EndDateClickHandler(Date date) {
			this.date = date;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			endDateLabel.setText(formatDate(removeOneMonth(date)));
			selectedEndDate = date;
			
			if (selectedStartDate.after(selectedEndDate) || selectedStartDate.equals(selectedEndDate)) {
				selectedStartDate = removeOneMonth(selectedEndDate);
				startDateLabel.setText(formatDate(selectedStartDate));
			}
			
			onPeriodChanged();
		}
	}
	
	private void onPeriodChanged() {
		Period period = new Period(selectedStartDate, selectedEndDate);
		DashboardPanel.this.transactionsPanel.onPeriodChanged(period);
		DashboardPanel.this.pursesPanel.onPeriodChanged(period);
	}
	
	private List<Date> getAvailableDates() {
		Date currentDate = new Date();
		int currentYear = currentDate.getYear();
		int currentMonth = currentDate.getMonth();
		
		List<Date> dates = newArrayList();
		for (int i = 0; i < NB_OF_AVAILABLE_MONTHS; i++) {
			Date availableDate = new Date(currentYear, currentMonth, 1);
			dates.add(availableDate);

			if (currentMonth == 0) {
				currentYear -= 1;
				currentMonth = 11;
			} else {
				currentMonth--;
			}
		}
		
		Collections.reverse(dates);
		
		return dates;
	}

	private Date addOneMonth(Date date) {
		final int endDateMonth;
		final int endDateYear;
		if (date.getMonth() == 11) {
			endDateMonth = 0;
			endDateYear = date.getYear() + 1;
		} else {
			endDateMonth = date.getMonth() + 1;
			endDateYear = date.getYear();
		}
		
		return new Date(endDateYear, endDateMonth, 1);
	}
	
	private Date removeOneMonth(Date date) {
		final int endDateMonth;
		final int endDateYear;
		if (date.getMonth() == 0) {
			endDateMonth = 11;
			endDateYear = date.getYear() - 1;
		} else {
			endDateMonth = date.getMonth() - 1;
			endDateYear = date.getYear();
		}
		
		return new Date(endDateYear, endDateMonth, 1);
	}
	
	private void initializeDatesRange() {
		List<Date> dates = getAvailableDates();
		
		int index = 0;
		for (Date date : dates) {
			String dateLabel = formatDate(date);
			NavLink startDateLink = new NavLink(dateLabel, "#");
			startDateLink.addClickHandler(new StartDateClickHandler(date, index));
			startDateList.add(startDateLink);
			
			NavLink endDateLink = new NavLink(dateLabel, "#");
			Date endDate = addOneMonth(date);
			endDateLink.addClickHandler(new EndDateClickHandler(endDate));
			endDateList.add(endDateLink);
			
			index++;
		}
		
		int currentMonthDateIndex = dates.size() - 1;
		int defaultMonthDate = currentMonthDateIndex;

		Date defaultMonth = dates.get(defaultMonthDate);
		String defaultMonthLabel = formatDate(defaultMonth);
		
		selectedStartDate = defaultMonth;
		Date endDate = addOneMonth(defaultMonth);
		selectedEndDate = endDate;
		
		startDateLabel.setText(defaultMonthLabel);
		endDateLabel.setText(defaultMonthLabel);
	}
	
	public DashboardPanel(Dashboard dashboard) {
		initWidget(uiBinder.createAndBindUi(this));
		
		initializeDatesRange();

		Period period = new Period(selectedStartDate, selectedEndDate);
		
		pursesPanel = new PursesPanel();
		pursesContainer.add(pursesPanel);
		transactionsPanel = new TransactionsPanel(dashboard, period);
		transactionsContainer.add(transactionsPanel);
		
	
		onPeriodChanged();
	}

	private static String formatDate(Date date) {
		return DATE_FORMAT.format(date).toLowerCase();
	}
}
