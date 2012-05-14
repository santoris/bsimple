package com.santoris.bsimple.axa.dao.bank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.santoris.bsimple.axa.model.AxaOutstandingTransaction;
import com.santoris.bsimple.axa.model.AxaTransaction;
import com.santoris.bsimple.axa.model.AxaTransactionsWrapper;
import com.santoris.bsimple.helpers.BSimpleConstant;

@Repository
public class AxaTransactionBankDAO {

	@Value("${base_url}")
	private String baseUrl;

	@Value("${client_id}")
	private String clientId;

	@Value("${access_token}")
	private String accessToken;

	private RestTemplate restTemplate = new RestTemplate();

	public List<AxaTransaction> getTransactionList(String accountId,
			String customerId, String page) {
		AxaTransactionsWrapper transactionsWrapper = restTemplate
				.getForObject(
						baseUrl
								+ "/accounts/{accountId}/transactions?client_id={clientId}&access_token={accessToken}&customer_id={customerId}&count={count}&page={page}",
						AxaTransactionsWrapper.class, accountId, clientId,
						accessToken, customerId, BSimpleConstant.PAGE_SIZE,
						page);

		return Arrays.asList(transactionsWrapper.getTransactions());
	}

	public List<AxaOutstandingTransaction> getAllOutstandingTransactionsByAccount(Long accountId, Long customerId) {
		AxaOutstandingTransaction[] transactions = restTemplate.getForObject(baseUrl 
					+ "/accounts/{accountId}/outstanding_transactions?client_id={clientId}&access_token={accessToken}&customer_id={customerId}", 
					AxaOutstandingTransaction[].class, accountId,clientId, accessToken, customerId);
		return Arrays.asList(transactions);
	}

	public List<AxaTransaction> getAllTransactionsByAccount(Long accountId,
			Long customerId) {
		List<AxaTransaction> transactions = new ArrayList<AxaTransaction>();
		
		int page = 1;
		AxaTransactionsWrapper wrapper;
		do {
			wrapper = restTemplate
					.getForObject(
							baseUrl
									+ "/accounts/{accountId}/transactions?client_id={clientId}&access_token={accessToken}&customer_id={customerId}&count={count}&page={page}",
							AxaTransactionsWrapper.class, accountId, clientId,
							accessToken, customerId, BSimpleConstant.LARGE_PAGE_SIZE,
							page);
			transactions.addAll(Arrays.asList(wrapper.getTransactions()));
			page++;
		} while (wrapper.isHasMore() && wrapper.getTransactions().length > 0);
		
		return transactions;
	}

}
