package com.santoris.bsimple.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.Objects;

@SuppressWarnings("serial")
@Document
public class Customer implements Serializable {

	private String id;
	
	private Long bankId;

	private String label;

	private List<Account> accounts = new ArrayList<Account>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long id) {
		this.bankId = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	public void addAcount(Account account) {
		this.accounts.add(account);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("id", id)
				.add("bankId", bankId)
				.add("label", label)
				.toString();
	}
}
