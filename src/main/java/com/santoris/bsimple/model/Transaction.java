package com.santoris.bsimple.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.Objects;

@Document
public class Transaction implements Serializable {

	private String id;

	private String customerId;

	private Long bankId;
	
	private Long bankAccountId;

	private String type;

	private Date date;

	private String currency;

	private BigDecimal amount;

	private String label;

	private String locationCity;

	private boolean oustanding;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Long getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(Long bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLocationCity() {
		return locationCity;
	}

	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}
	
	public boolean isOustanding() {
		return oustanding;
	}

	public void setOustanding(boolean oustanding) {
		this.oustanding = oustanding;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("id", id)
				.add("customerId", customerId)
				.add("bankId", bankId)
				.add("bankAccountId", bankAccountId)
				.add("type", type)
				.add("date", date)
				.add("currency", currency)
				.add("amount", amount)
				.add("label", label)
				.add("locationCity", locationCity)
				.add("oustanding", oustanding)
				.toString();
	}

}
