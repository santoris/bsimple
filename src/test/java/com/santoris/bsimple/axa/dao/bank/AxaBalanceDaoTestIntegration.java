package com.santoris.bsimple.axa.dao.bank;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.santoris.bsimple.BaseSpringConfiguration;
import com.santoris.bsimple.axa.dao.bank.AxaBalanceBankDAO;
import com.santoris.bsimple.axa.model.AxaBalance;

public class AxaBalanceDaoTestIntegration extends BaseSpringConfiguration {
	
	@Autowired
	private AxaBalanceBankDAO balanceDao;

	@Test
	public void testAllBalancesByAccount() {
		final Long accountId = 20000001500L;
		final Long customerId= 1000000L;
		List<AxaBalance> balances = balanceDao.getAllBalancesByAccount(accountId,customerId);
		assertTrue(balances.size() > 0);
		System.out.println("balances size:" + balances.size());
	}
}
