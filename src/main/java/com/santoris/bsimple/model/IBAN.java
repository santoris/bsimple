package com.santoris.bsimple.model;

public class IBAN {
	
	final static private String IBAN_LABEL_SAMPLE = "FR70 3000 2005 5000 0015 7845 Z02";
	
	private String countryCode;
	
	private String checkDigits;
	
	private String bban;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCheckDigits() {
		return checkDigits;
	}

	public void setCheckDigits(String checkDigits) {
		this.checkDigits = checkDigits;
	}

	public String getBban() {
		return bban;
	}

	public void setBban(String bban) {
		this.bban = bban;
	}
	
	public String getLabel() {
		final StringBuilder label = new StringBuilder(IBAN_LABEL_SAMPLE.length());
		label.append(countryCode).append(checkDigits).append(" ");

		appendBbanWithOneWhitespaceEvery4Digits(label);
		
		return label.toString();
	}

	private void appendBbanWithOneWhitespaceEvery4Digits(final StringBuilder label) {
		for (int i = 0; i < bban.length(); i++) {
			if (i % 4 == 0 && i > 0) {
				label.append(" ");
			}
			label.append(bban.charAt(i));
		}
	}

	@Override
	public String toString() {
		return getLabel();
	}
}
