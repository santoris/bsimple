package com.santoris.bsimple.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.santoris.bsimple.helpers.BSimpleConstant;
import com.santoris.bsimple.model.Transaction;
import com.santoris.bsimple.model.TransactionsWrapper;

@Repository
public class TransactionDAO {
	
	@Value("${base_url}")
	private String baseUrl;
	
	@Value("${client_id}")
	private String clientId;
	
	@Value("${access_token}")
	private String accessToken;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public List<Transaction> getTransactionList(String accountId, String customerId, String page) {
		TransactionsWrapper transactionsWrapper = restTemplate.getForObject(baseUrl 
				+ "/accounts/{accountId}/transactions?client_id={clientId}&access_token={accessToken}&customer_id={customerId}&count={count}&page={page}", 
				TransactionsWrapper.class, accountId,clientId, accessToken, customerId, BSimpleConstant.PAGE_SIZE, page);
		
//		for (Transaction transaction : transactionsWrapper.getTransactions()) {
//			if (transaction.getAtm() != null || transaction.getPointOfSale() != null || transaction.getForeignAmount() != null ||  transaction.getServiceCharge() != null) {
//				System.out.println("transaction " + transaction.getId() + " (" + transaction.getLabel() + "), amount " + transaction.getAmount() + ", date " + transaction.getEntryDate() + ", atm <" + transaction.getAtm() + ">, point of sale <" + transaction.getPointOfSale() + ">, foreign amount <" + transaction.getForeignAmount() + ">");
//			}
//		}
		
		return Arrays.asList(transactionsWrapper.getTransactions());
	}

	public List<Transaction> getOutstandingTransactionList(String accountId, String customerId, String page) {
		Transaction[] transactions = restTemplate.getForObject(baseUrl 
					+ "/accounts/{accountId}/outstanding_transactions?client_id={clientId}&access_token={accessToken}&customer_id={customerId}&count={count}&page={page}", 
					Transaction[].class, accountId,clientId, accessToken, customerId, BSimpleConstant.PAGE_SIZE, page);	
			return Arrays.asList(transactions);
	}

}
