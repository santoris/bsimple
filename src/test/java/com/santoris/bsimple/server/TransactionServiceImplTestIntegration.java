package com.santoris.bsimple.server;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.santoris.bsimple.BaseSpringConfiguration;
import com.santoris.bsimple.dao.TransactionRepository;
import com.santoris.bsimple.model.Period;
import com.santoris.bsimple.model.Transaction;
import com.santoris.bsimple.page.Page;
import com.santoris.bsimple.page.PageRequest;

public class TransactionServiceImplTestIntegration extends
		BaseSpringConfiguration {

	@Autowired
	TransactionServiceImpl transactionServiceImpl;

	@Autowired
	TransactionRepository transactionRepository;

	@Test
	public void testGetTransactionsByAccountIdsByPeriod() {
		int pageNumber = 0;
		int pageSize = 20;
		PageRequest pageRequest = new PageRequest(pageNumber, pageSize);
		String customerId = "4fb7f64c8bce45519169d293";
		List<Long> accountIds = newArrayList(20000041504L);

		Date startDate = new Date(2012 - 1900, 1 - 1, 1);
		Date endDate = new Date(2012 - 1900, 2 - 1, 1);
		Period period = new Period(startDate, endDate);
		String labelPart = "";

		Page<Transaction> page = transactionServiceImpl
				.findTransactionsByCustomerIdByAccountIdsByPeriod(customerId,
						accountIds, period, labelPart, pageRequest);

		Assert.assertEquals(32, page.getContent().size());
	}

}
