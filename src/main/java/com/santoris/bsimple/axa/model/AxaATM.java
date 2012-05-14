package com.santoris.bsimple.axa.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

import com.google.common.base.Objects;

public class AxaATM {

	@JsonProperty(value = "retrieval_date")
	private Date retrievalDate;

	private BigDecimal amount;

	private AxaLocation location;
	
	@JsonProperty(value = "atm_id")
	private AxaATMId atmId;

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

	public AxaLocation getLocation() {
		return location;
	}

	public void setLocation(AxaLocation location) {
		this.location = location;
	}

	public AxaATMId getAtmId() {
		return atmId;
	}

	public void setAtmId(AxaATMId atmId) {
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
