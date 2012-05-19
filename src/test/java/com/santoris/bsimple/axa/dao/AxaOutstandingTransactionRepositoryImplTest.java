package com.santoris.bsimple.axa.dao;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.santoris.bsimple.BaseSpringConfiguration;
import com.santoris.bsimple.axa.model.AxaOutstandingTransaction;

public class AxaOutstandingTransactionRepositoryImplTest extends BaseSpringConfiguration {

	@Autowired
	AxaOutstandingTransactionRepository repository;
	
	@Test
	public void testFindByAccounts() {
		List<Long> accountIds = newArrayList(20000001500L, 20000005100L);
		List<AxaOutstandingTransaction> transactions = repository.findByAccounts(accountIds);
		Assert.assertEquals(4, transactions.size()); // 4 + 0
	}
	
}
