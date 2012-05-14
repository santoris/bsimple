package com.santoris.bsimple.axa.dao.bank;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.santoris.bsimple.BaseSpringConfiguration;
import com.santoris.bsimple.axa.dao.bank.AxaTransactionBankDAO;
import com.santoris.bsimple.axa.model.AxaOutstandingTransaction;
import com.santoris.bsimple.axa.model.AxaTransaction;

public class AxaTransactionDaoTestIntegration extends BaseSpringConfiguration {
	
	@Autowired
	private AxaTransactionBankDAO transactionDao;
	
	@Test
	public void testTransactionListOk() {
		final String accountId = "20000001500";
		final String customerId= "1000000";
		List<AxaTransaction> transactionList = transactionDao.getTransactionList(accountId,customerId, "1");
		assertTrue(transactionList.size() > 0);
		for (AxaTransaction t : transactionList) {
			System.out.println(t.getLabel());
		}
	}

	@Test
	public void testOutstandingTransactionListOk() {
		final Long accountId = 20000001500L;
		final Long customerId= 1000000L;
		List<AxaOutstandingTransaction> transactionList = transactionDao.getAllOutstandingTransactionsByAccount(accountId,customerId);
		assertTrue(transactionList.size() > 0);
		for (AxaOutstandingTransaction t : transactionList) {
			System.out.println(t.getLabel());
		}
	}

	@Test
	public void testAllTransactionsByAccount() {
		final Long accountId = 20000001500L;
		final Long customerId= 1000000L;
		List<AxaTransaction> transactionList = transactionDao.getAllTransactionsByAccount(accountId,customerId);
		assertTrue(transactionList.size() > 0);
		System.out.println("transactions size:" + transactionList.size());
	}
}
