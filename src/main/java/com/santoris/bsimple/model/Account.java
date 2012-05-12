package com.santoris.bsimple.model;

/**
 * https://developer.axabanque.fr/api/resource_AccountService.html#path__accounts_-accountUid-.html
 * 
 * https://sandbox-api.axabanque.fr/accounts/20000001500?client_id=263552891477590181&access_token=649946458505090179&customer_id=1000000
 * 
 * {"id":20000001500,"customer":1000000,"type":"checking","currency":"EUR","bic":"AXABFRPPXXX","iban":["FR","**","12548****************21"],"label":"CC"}
 * 
 * 
 * https://developer.axabanque.fr/api/resource_CustomerService.html#path__customers_-customerUid-_accounts.html
 * 
 * https://sandbox-api.axabanque.fr/customers/1000000/accounts?client_id=263552891477590181&access_token=649946458505090179&customer_id=1000000
 * 
 * [{"id":20000001500,"customer":1000000,"type":"checking","currency":"EUR","bic":"AXABFRPPXXX","iban":["FR","**","12548****************21"],"label":"CC"},
 *  {"id":20000001510,"customer":1000000,"type":"checking","currency":"EUR","bic":"AXABFRPPXXX","iban":["FR","**","12548****************61"],"label":"CC"},
 *  {"id":20000001520,"customer":1000000,"type":"checking","currency":"EUR","bic":"AXABFRPPXXX","iban":["FR","**","12548****************85"],"label":"CC"},
 *  {"id":20000005100,"customer":1000000,"type":"savings","currency":"EUR","bic":"AXABFRPPXXX","iban":["FR","**","12548****************39"],"label":"LIVRET AXA BANQUE"},
 *  {"id":20000005110,"customer":1000000,"type":"savings","currency":"EUR","bic":"AXABFRPPXXX","iban":["FR","**","12548****************22"],"label":"LIVRET AXA BANQUE"},
 *  {"id":20000005120,"customer":1000000,"type":"savings","currency":"EUR","bic":"AXABFRPPXXX","iban":["FR","**","12548****************58"],"label":"LIVRET AXA BANQUE"},
 *  {"id":20000005200,"customer":1000000,"type":"savings","currency":"EUR","bic":"AXABFRPPXXX","iban":["FR","**","12548****************71"],"label":"LIVRET JEUNE"},
 *  {"id":20000005400,"customer":1000000,"type":"savings","currency":"EUR","bic":"AXABFRPPXXX","iban":["FR","**","12548****************78"],"label":"CODEVI"},
 *  {"id":20000005500,"customer":1000000,"type":"savings","currency":"EUR","bic":"AXABFRPPXXX","iban":["FR","**","12548****************56"],"label":"CEL"},
 *  {"id":20000005600,"customer":1000000,"type":"savings","currency":"EUR","bic":"AXABFRPPXXX","iban":["FR","**","12548****************42"],"label":"PEL"},
 *  {"id":20000005800,"customer":1000000,"type":"savings","currency":"EUR","bic":"AXABFRPPXXX","iban":["FR","**","12548****************21"],"label":"LIVRET A"}]
 *
 */
public class Account {
	private Long id;
	
	private Long customer;

	private String type;
	
	private String currency;
	
	private String bic;

	private String[] iban;

	private String label;
			
	public Account() {
	}

	protected String getResourceType() {
		return "account";
	}

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

	
}
