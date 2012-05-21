package com.santoris.bsimple.server;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
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
	public Page<Transaction> findTransactionsByCustomerIdByAccountIdsByPeriod(
			final String customerId, final List<Long> accountIds,
			final Period period, String labelPart, final PageRequest pageRequest) {
		final org.springframework.data.domain.PageRequest springPageRequest = new org.springframework.data.domain.PageRequest(
				pageRequest.getPageNumber(), pageRequest.getPageSize());
		final Sort sort = new Sort(new Order(Direction.ASC, "date"));
		final org.springframework.data.domain.Page<Transaction> springPage = transactionRepository
				.findByAccountsAndPeriod(customerId, accountIds, period,
						labelPart, springPageRequest, sort);
		final Page<Transaction> page = new Page<Transaction>(pageRequest,
				newArrayList(springPage.getContent()),
				springPage.getTotalElements());
		return page;
	}

}
