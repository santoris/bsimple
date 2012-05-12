package com.santoris.bsimple.dao;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.santoris.bsimple.model.Balance;
import com.santoris.bsimple.model.BalancesWrapper;


@Repository
public class BalanceDAO {
	
	private Logger logger  = Logger.getLogger(getClass());

	@Value("${base_url}")
	private String baseUrl;
	
	@Value("${client_id}")
	private String clientId;
	
	@Value("${access_token}")
	private String accessToken;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public Balance getBalanceForAccount(String accountId,String customerId) {
		Balance balance;
			List<Balance> balances = getBalanceListForAccount(accountId, customerId);
			if (balances.size() == 0) {
				balance = new Balance();
				balance.setAmount(BigDecimal.ZERO);
				logger.error("API Didn't give any balance for account " + accountId);
				logger.error("clientID :" + clientId + " accesstoken : " + accessToken);
			} else {
				balance = balances.get(0);
			}
		return balance;
	}

	public List<Balance> getBalanceListForAccount(String accountId,
			String customerId) {
		BalancesWrapper wrapper = restTemplate.getForObject(baseUrl + "/accounts/{accountId}/balances?client_id={clientId}&access_token={accessToken}&customer_id={customerId}",
				BalancesWrapper.class,
				accountId,clientId, accessToken, customerId);
		return Arrays.asList(wrapper.getBalances());
	}
}
