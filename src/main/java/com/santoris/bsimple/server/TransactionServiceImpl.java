package com.santoris.bsimple.server;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santoris.bsimple.client.service.TransactionService;
import com.santoris.bsimple.dao.TransactionRepository;
import com.santoris.bsimple.model.Period;
import com.santoris.bsimple.model.Transaction;
import com.santoris.bsimple.page.Page;
import com.santoris.bsimple.page.PageRequest;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	TransactionRepository transactionRepository;

	@Override
	public Page<Transaction> findTransactionsByAccountIdsByPeriod(
			final PageRequest pageRequest, final String userId,
			final List<Long> accountIds, final Period period) {
		final org.springframework.data.domain.PageRequest springPageRequest = new org.springframework.data.domain.PageRequest(
				pageRequest.getPageNumber(), pageRequest.getPageSize());
		final org.springframework.data.domain.Page<Transaction> springPage = transactionRepository
				.findByAccountsAndPeriod(springPageRequest, userId, accountIds, period);
		final Page<Transaction> page = new Page<Transaction>(pageRequest,
				newArrayList(springPage.getContent()),
				springPage.getTotalElements());
		return page;
	}

}
