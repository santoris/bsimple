package com.santoris.bsimple.server;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.google.common.collect.Lists.newArrayList;
import com.santoris.bsimple.BaseSpringConfiguration;
import com.santoris.bsimple.model.Period;
import com.santoris.bsimple.model.Transaction;
import com.santoris.bsimple.page.Page;
import com.santoris.bsimple.page.PageRequest;

public class TransactionServiceImplTestIntegration extends BaseSpringConfiguration {

	@Autowired
	TransactionServiceImpl transactionServiceImpl;
	
	@Test
	public void testGetTransactionsByAccountIdsByPeriod() {
		int pageNumber = 0;
		int pageSize = 20;
		PageRequest pageRequest = new PageRequest(pageNumber, pageSize);
		
		String userId = "4fb6cbaa8bceff6f4c034a6e";

		List<Long> accountIds = newArrayList(20000041504L);

		Date startDate = new Date(2012 - 1900, 1 - 1, 1);
		Date endDate = new Date(2012 - 1900, 1 - 1, 31);
		Period period = new Period(startDate, endDate);

		Page<Transaction> page = transactionServiceImpl.findTransactionsByAccountIdsByPeriod(pageRequest, userId, accountIds, period);
		
		Assert.assertEquals(pageSize, page.getContent().size());
	}
}
