package com.santoris.bsimple.axa.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

import com.google.common.base.Objects;

public class AxaPointOfSale {

	@JsonProperty(value = "pos_transaction_date")
	private Date transactionDate;
	
	private BigDecimal amount;
	
	/**
	 * [ currency, amount ]
	 */
	@JsonProperty(value = "foreign_amount")
	private AxaForeignAmount foreignAmount;
	
	private AxaLocation location;
	
	@JsonProperty(value = "merchant_registration_number")
	private String merchantRegistrationNumber;
	
	@JsonProperty(value = "merchant_category_code")
	private String merchantCategoryCode;

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
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

	public String getMerchantRegistrationNumber() {
		return merchantRegistrationNumber;
	}

	public void setMerchantRegistrationNumber(String merchantRegistrationNumber) {
		this.merchantRegistrationNumber = merchantRegistrationNumber;
	}

	public String getMerchantCategoryCode() {
		return merchantCategoryCode;
	}

	public void setMerchantCategoryCode(String merchantCategoryCode) {
		this.merchantCategoryCode = merchantCategoryCode;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
                .add("transactionDate", transactionDate)
                .add("amount", amount)
                .add("foreignAmount", foreignAmount)
                .add("location", location)
                .add("amount", amount)
                .add("location", location)
                .add("merchantRegistrationNumber", merchantRegistrationNumber)
                .add("merchantRegistrationNumber", merchantRegistrationNumber)
                .toString();
	}

}
