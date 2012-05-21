package com.santoris.bsimple.model;

import java.io.Serializable;
import java.util.Date;

import com.google.common.base.Objects;

public class Period implements Serializable {

	private Date startDate;
	
	private Date endDate;
	
	public Period() {
	}
	
	public Period(final Date startDate, final Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("startDate", startDate)
				.add("endDate", endDate)
				.toString();
	}
}
