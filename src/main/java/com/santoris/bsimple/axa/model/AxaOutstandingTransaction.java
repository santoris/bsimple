package com.santoris.bsimple.axa.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class AxaOutstandingTransaction {
	
	private Long id;
	
	private Long account;

	private String type;

	/**
	 * entryDate or accountingDate or transactionDate !?
	 */
	@JsonProperty(value = "date")
	private Date transactionDate;

	private String currency;

	private BigDecimal amount;
	
	/**
	 * [ currency, amount ]
	 */
	@JsonProperty(value = "foreign_amount")
	private AxaForeignAmount foreignAmount;
	
	private String label;

	@JsonProperty(value = "service_charge")
	private AxaServiceCharge[] serviceCharge;
	
	private AxaATM atm;

	@JsonProperty(value = "point_of_sale")
	private AxaPointOfSale pointOfSale;
	
	public AxaOutstandingTransaction() {
	}

	public AxaOutstandingTransaction(String txType, String label, BigDecimal amount,
			String currency, Date date, Long account) {

		this.type = txType;
		this.label = label;
		this.amount = amount;
		this.currency = currency;
		this.transactionDate = date;
		this.account = account;
	}

	protected String getResourceType() {
		return "entry";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAccount() {
		return account;
	}

	public void setAccount(Long account) {
		this.account = account;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
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

	public AxaATM getAtm() {
		return atm;
	}

	public void setAtm(AxaATM atm) {
		this.atm = atm;
	}

	public AxaPointOfSale getPointOfSale() {
		return pointOfSale;
	}

	public void setPointOfSale(AxaPointOfSale pointOfSale) {
		this.pointOfSale = pointOfSale;
	}

	public AxaForeignAmount getForeignAmount() {
		return foreignAmount;
	}

	public AxaServiceCharge[] getServiceCharge() {
		return serviceCharge;
	}

}
