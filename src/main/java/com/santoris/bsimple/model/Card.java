package com.santoris.bsimple.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * https://developer.axabanque.fr/api/resource_AccountService.html#path__accounts_-accountUid-_cards.html
 * 
 * https://sandbox-api.axabanque.fr/accounts/20000001500/cards?client_id=263552891477590181&access_token=649946458505090179&customer_id=1000000
 * 
 * [{"id":4000010,"account":20000001500,"payment_account":20000001500,"payment_bic":"AXABFRPPXXX","payment_iban":["FR","**","12548029980123456789021"],"type":"visa","number":"************5000","label":"PREMIER"}]
 *
 */
public class Card {

	private Long id;
	
	private Long account;
	
	@JsonProperty(value = "payment_account")
	private Long paymentAccount;
	
	@JsonProperty(value = "payment_bic")
	private String paymentBic;
	
	/**
	 * [ iban_country_code, _iban_check_digits, iban_bban ] 
	 */
	@JsonProperty(value = "payment_iban")
	private String[] paymentIban;
	
	private String type;
	
	private String number;
	
	private String label;

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

	public Long getPaymentAccount() {
		return paymentAccount;
	}

	public void setPaymentAccount(Long paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	public String getPaymentBic() {
		return paymentBic;
	}

	public void setPaymentBic(String paymentBic) {
		this.paymentBic = paymentBic;
	}

	public String[] getPaymentIban() {
		return paymentIban;
	}

	public void setPaymentIban(String[] paymentIban) {
		this.paymentIban = paymentIban;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
