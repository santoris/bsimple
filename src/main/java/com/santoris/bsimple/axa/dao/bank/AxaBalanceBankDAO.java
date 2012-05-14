package com.santoris.bsimple.axa.dao.bank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.santoris.bsimple.axa.model.AxaBalance;
import com.santoris.bsimple.axa.model.AxaBalancesWrapper;
import com.santoris.bsimple.axa.model.AxaTransaction;
import com.santoris.bsimple.axa.model.AxaTransactionsWrapper;
import com.santoris.bsimple.helpers.BSimpleConstant;

@Repository
public class AxaBalanceBankDAO {

	private Logger logger = Logger.getLogger(getClass());

	@Value("${base_url}")
	private String baseUrl;

	@Value("${client_id}")
	private String clientId;

	@Value("${access_token}")
	private String accessToken;

	private RestTemplate restTemplate = new RestTemplate();

	public AxaBalance getBalanceForAccount(String accountId, String customerId) {
		AxaBalance balance;
		List<AxaBalance> balances = getBalanceListForAccount(accountId,
				customerId);
		if (balances.size() == 0) {
			balance = new AxaBalance();
			balance.setAmount(BigDecimal.ZERO);
			logger.error("API Didn't give any balance for account " + accountId);
			logger.error("clientID :" + clientId + " accesstoken : "
					+ accessToken);
		} else {
			balance = balances.get(0);
		}
		return balance;
	}

	public List<AxaBalance> getBalanceListForAccount(String accountId,
			String customerId) {
		AxaBalancesWrapper wrapper = restTemplate
				.getForObject(
						baseUrl
								+ "/accounts/{accountId}/balances?client_id={clientId}&access_token={accessToken}&customer_id={customerId}",
						AxaBalancesWrapper.class, accountId, clientId,
						accessToken, customerId);
		return Arrays.asList(wrapper.getBalances());
	}

	public List<AxaBalance> getAllBalancesByAccount(Long accountId,
			Long customerId) {
		List<AxaBalance> balances = new ArrayList<AxaBalance>();

		int page = 1;
		AxaBalancesWrapper wrapper;
		do {
			wrapper = restTemplate
					.getForObject(
							baseUrl
									+ "/accounts/{accountId}/balances?client_id={clientId}&access_token={accessToken}&customer_id={customerId}&from_date=2000-01-01&to_date=2012-12-31&count={count}&page={page}",
							AxaBalancesWrapper.class, accountId, clientId,
							accessToken, customerId,
							BSimpleConstant.LARGE_PAGE_SIZE, page);
			balances.addAll(Arrays.asList(wrapper.getBalances()));
			page++;
		} while (wrapper.isHasMore() && wrapper.getBalances().length > 0);

		for (AxaBalance balance : balances) {
			balance.initializeId();
		}
		
		return balances;
	}
}
