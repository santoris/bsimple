package com.santoris.bsimple.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.Objects;

@SuppressWarnings("serial")
@Document
public class Account implements Serializable {
	
	private Long bankId;
	
	private AccountType type;
	
	private String currency;
	
	private String label;
	
	private String bic;
	
	private IBAN iban;

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public IBAN getIban() {
		return iban;
	}

	public void setIban(IBAN iban) {
		this.iban = iban;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("bankId", bankId)
				.add("type", type)
				.add("currency", currency)
				.add("label", label)
				.add("bic", bic)
				.add("iban", iban)
				.toString();
	}
}
