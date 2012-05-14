package com.santoris.bsimple.axa.model;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * https://developer.axabanque.fr/api/resource_AccountService.html#path__accounts_-accountUid-_balances.html
 * 
 * Use {@link AxaBalancesWrapper} to retrieve balances
 * 
 * https://sandbox-api.axabanque.fr/accounts/20000001500/balances?client_id=263552891477590181&access_token=649946458505090179&customer_id=1000000
 * 
 * {"balances":[{"date":"2011-11-30","account":20000001500,"currency":"EUR","amount":0.00}]}
 * 
 * 
 * https://sandbox-api.axabanque.fr/accounts/20000001500/balances?client_id=263552891477590181&access_token=649946458505090179&customer_id=1000000&on=2011-11-30
 * https://sandbox-api.axabanque.fr/accounts/20000001500/balances?client_id=263552891477590181&access_token=649946458505090179&customer_id=1000000&from_date=2011-11-01&to_date=2011-11-30&count=2&page=0
 * 
 *
 */
public class AxaBalance {
	private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	private String id;
	
	private Long customerId;

	private Date date;
	
	private Long account;
	
	private String currency;
	
	private BigDecimal amount;

	public AxaBalance() {
	}

	public AxaBalance(BigDecimal value, Date date, Long account) {
		this.amount = value;
		this.date = date;
		this.account = account;
	}

	protected String getResourceType() {
		return "balance";
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getAccount() {
		return account;
	}

	public void setAccount(Long account) {
		this.account = account;
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

	public void initializeId() {
		this.id = this.account + "|" + DATE_FORMAT.format(this.date);
	}
}
