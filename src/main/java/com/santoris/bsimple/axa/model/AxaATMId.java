package com.santoris.bsimple.axa.model;

import org.codehaus.jackson.annotate.JsonProperty;

import com.google.common.base.Objects;

public class AxaATMId {

	@JsonProperty(value = "bank_id")
	private String bankId;
	
	private String id;

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
                .add("bankId", bankId)
                .add("id", id)
                .toString();
	}

}
