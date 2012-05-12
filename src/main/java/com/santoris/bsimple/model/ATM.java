package com.santoris.bsimple.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

import com.google.common.base.Objects;

public class ATM {

	@JsonProperty(value = "retrieval_date")
	private Date retrievalDate;

	private BigDecimal amount;

	private Location location;
	
	@JsonProperty(value = "atm_id")
	private ATMId atmId;

	public Date getRetrievalDate() {
		return retrievalDate;
	}

	public void setRetrievalDate(Date retrievalDate) {
		this.retrievalDate = retrievalDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public ATMId getAtmId() {
		return atmId;
	}

	public void setAtmId(ATMId atmId) {
		this.atmId = atmId;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
                .add("retrievalDate", retrievalDate)
                .add("amount", amount)
                .add("location", location)
                .add("atmId", atmId)
                .toString();
	}

}
