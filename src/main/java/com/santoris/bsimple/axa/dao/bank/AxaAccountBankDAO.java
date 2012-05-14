package com.santoris.bsimple.axa.dao.bank;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.santoris.bsimple.axa.model.AxaAccount;
import com.santoris.bsimple.axa.model.AxaAccountSummary;
import com.santoris.bsimple.axa.model.AxaBalance;

@Repository
public class AxaAccountBankDAO {
	
	private Logger logger  = Logger.getLogger(getClass());
	
	@Value("${base_url}")
	private String baseUrl;
	
	@Value("${client_id}")
	private String clientId;
	
	@Value("${access_token}")
	private String accessToken;
	
	@Autowired
	private AxaBalanceBankDAO balanceDAO;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public List<AxaAccountSummary> getAccountListForCustomer(String customerId) {
		AxaAccountSummary [] accounts = restTemplate.getForObject(baseUrl +"/customers/{customerId}/accounts?client_id={clientId}&access_token={accessToken}&customer_id={customerId}", 
				AxaAccountSummary[].class, 
				customerId, clientId, accessToken, customerId);
		logger.debug("CLIENT ID : " + clientId + " ACCESS TOKEN" + accessToken);
		List<AxaAccountSummary> accountList = Arrays.asList(accounts);
		for (AxaAccountSummary acc : accountList) {
			AxaBalance b = balanceDAO.getBalanceForAccount(String.valueOf(acc.getAccount()), customerId);
			acc.setAmount(b.getAmount());
		}
		return accountList;
	}
	
	public List<AxaAccount> getAllAccountsByCustomer(Long customerId) {
		AxaAccount [] accounts = restTemplate.getForObject(baseUrl +"/customers/{customerId}/accounts?client_id={clientId}&access_token={accessToken}&customer_id={customerId}", 
				AxaAccount[].class, 
				customerId, clientId, accessToken, customerId);
		return Arrays.asList(accounts);
	}
	
	public AxaAccount getAccountById(String accountId,String customerId) {
		AxaAccount account = restTemplate.getForObject(baseUrl + "/accounts/{accountId}?client_id={clientId}&access_token={accessToken}&customer_id={customerId}",
				AxaAccount.class,
				accountId,clientId,accessToken, customerId);
		
		return account;
	}
	
}
