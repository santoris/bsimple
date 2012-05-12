package com.santoris.bsimple.model;

import org.codehaus.jackson.annotate.JsonProperty;

import com.google.common.base.Objects;

public class Location {

	private String name;
	
	private String city;
	
	@JsonProperty(value = "postal_code")
	private String postalCode;
	
	private String country;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
                .add("name", name)
                .add("city", city)
                .add("postalCode", postalCode)
                .add("country", country)
                .toString();
	}

}
