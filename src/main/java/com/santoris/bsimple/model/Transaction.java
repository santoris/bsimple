package com.santoris.bsimple.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * https://developer.axabanque.fr/api/resource_TransactionService.html#path__transactions_-uid-.html
 * 
 * https://developer.axabanque.fr/api/resource_AccountService.html#path__accounts_-accountUid-_transactions.html
 * 
 * https://sandbox-api.axabanque.fr/accounts/20000001500/transactions?client_id=263552891477590181&access_token=649946458505090179&customer_id=1000000&count=2&page=0
 * https://sandbox-api.axabanque.fr/accounts/20000001500/transactions?client_id=263552891477590181&access_token=649946458505090179&customer_id=1000000&count=10&page=0
 * 
 * {"has_more":true,
 *  "transactions":
 *    [{"id":2002834,"account":20000001500,"type":"check","date":"2012-01-26","currency":"EUR","amount":-45.440,"label":"CHEQUE No.4978494"},
 *     {"id":2002833,"account":20000001500,"type":"check","date":"2012-01-25","currency":"EUR","amount":-412.990,"label":"CHEQUE No.4978493"},
 *     {"id":2002831,"account":20000001500,"type":"atm","date":"2012-01-24","currency":"EUR","amount":-60.000,"label":"RETRAIT DAB 21/01 MONNAIE","atm":{"retrieval_date":"2012-01-24","amount":-60.000,"location":{"name":"SOCIETE GENERALE ","city":"COLOMIERS LE PER ","country":"250 "},"atm_id":{"bank_id":"30003 ","id":"00905783 "}}},
 *     {"id":2002832,"account":20000001500,"type":"credit","date":"2012-01-24","currency":"EUR","amount":3900.000,"label":"VIRT DE SALAIRE JANVIER 2012"},
 *     {"id":2002830,"account":20000001500,"type":"debit","date":"2012-01-24","currency":"EUR","amount":-2700.000,"label":"VIR REMISE 0 GASQUET CHEMINE"},
 *     {"id":2002828,"account":20000001500,"type":"direct_debit","date":"2012-01-20","currency":"EUR","amount":-121.670,"label":"PRLV AGIPI"},
 *     {"id":2002829,"account":20000001500,"type":"debit","date":"2012-01-20","currency":"EUR","amount":-300.000,"label":"VIR REVERSEMENT CAF"},
 *     {"id":2002827,"account":20000001500,"type":"direct_debit","date":"2012-01-20","currency":"EUR","amount":-1500.000,"label":"PRLV FRANFINANCE"},
 *     {"id":2002826,"account":20000001500,"type":"credit","date":"2012-01-18","currency":"EUR","amount":1000.000,"label":"VIRT DE SALAIRE JANVIER 2012"},
 *     {"id":2002825,"account":20000001500,"type":"debit","date":"2012-01-16","currency":"EUR","amount":-75.000,"label":"VIR ETRENNES MAMIE"}]}
 *
 */
public class Transaction {

	private Long id;

	private Long account;

	@JsonProperty(value = "type")
	private String transactionType;

	/**
	 * entryDate or accountingDate or transactionDate !?
	 */
	@JsonProperty(value = "date")
	private Date entryDate;

	private String currency;

	private BigDecimal amount;
	
	/**
	 * [ currency, amount ]
	 */
	@JsonProperty(value = "foreign_amount")
	private String[] foreignAmount;
	
	private String label;

	@JsonProperty(value = "service_charge")
	private String[] serviceCharge;
	
	private ATM atm;

	@JsonProperty(value = "point_of_sale")
	private PointOfSale pointOfSale;
	
	public Transaction() {
	}

	public Transaction(String txType, String label, BigDecimal amount,
			String currency, Date date, Long account) {

		this.transactionType = txType;
		this.label = label;
		this.amount = amount;
		this.currency = currency;
		this.entryDate = date;
		this.account = account;
	}

	protected String getResourceType() {
		return "entry";
	}

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

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public ATM getAtm() {
		return atm;
	}

	public void setAtm(ATM atm) {
		this.atm = atm;
	}

	public PointOfSale getPointOfSale() {
		return pointOfSale;
	}

	public void setPointOfSale(PointOfSale pointOfSale) {
		this.pointOfSale = pointOfSale;
	}

	public String[] getForeignAmount() {
		return foreignAmount;
	}

	public String[] getServiceCharge() {
		return serviceCharge;
	}

}
