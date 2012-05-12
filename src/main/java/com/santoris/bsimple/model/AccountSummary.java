package com.santoris.bsimple.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * {@link Account}
 *
 */
public class AccountSummary implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long customer;

	private String type;
	
	private String currency;
	
	private String bic;

	private String[] iban;

	private String label;
	
	/**
	 * Amount is in fact the Balance
	 */
	private BigDecimal amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getCustomer() {
		return customer;
	}

	public void setCustomer(Long customer) {
		this.customer = customer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public String[] getIban() {
		return iban;
	}

	public void setIban(String[] iban) {
		this.iban = iban;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getAccount() {
		return id;
	}

	public void setAccount(Long account) {
		this.id = account;
	}

}
