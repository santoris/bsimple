package com.santoris.bsimple.dao;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.santoris.bsimple.model.Account;
import com.santoris.bsimple.model.AccountSummary;
import com.santoris.bsimple.model.Balance;
import com.santoris.bsimple.model.Customer;

@Repository
public class CustomerDAO {
	
	private Logger logger  = Logger.getLogger(getClass());
	
	@Value("${base_url}")
	private String baseUrl;
	
	@Value("${client_id}")
	private String clientId;
	
	@Value("${access_token}")
	private String accessToken;
	
	@Autowired
	private BalanceDAO balanceDAO;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public Customer getCustomerById(String customerId) {
		Customer customer = restTemplate.getForObject(baseUrl + "/customers/{customerId}?client_id={clientId}&access_token={accessToken}",
				Customer.class,
				customerId,clientId,accessToken);
		
		return customer;
	}
	
}
