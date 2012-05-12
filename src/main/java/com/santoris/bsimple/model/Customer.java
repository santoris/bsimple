package com.santoris.bsimple.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * https://developer.axabanque.fr/api/resource_CustomerService.html#path__customers_-uid-.html
 * 
 * https://sandbox-api.axabanque.fr/customers/1000000?client_id=263552891477590181&access_token=649946458505090179
 * 
 * {"id":1000000,"third_party_id":"a12f90","label":"THEO"}
 *
 */
public class Customer {
	
	private Long id;
	
	@JsonProperty(value = "third_party_id")
	private String thirdPartyId;
	
	private String label;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getThirdPartyId() {
		return thirdPartyId;
	}

	public void setThirdPartyId(String thirdPartyId) {
		this.thirdPartyId = thirdPartyId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
