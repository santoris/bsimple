package com.santoris.bsimple.axa.dao.bank;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.santoris.bsimple.BaseSpringConfiguration;
import com.santoris.bsimple.axa.dao.bank.AxaBalanceBankDAO;
import com.santoris.bsimple.axa.model.AxaBalance;

public class AxaAccountBalanceTestIntegration extends BaseSpringConfiguration {
	
	
	@Autowired
	private AxaBalanceBankDAO balancesDAO;
	
	
	@Test
	public void getBalanceForAccount() {
		String customerId = "1000000";
		String accountId  = "20000001500";
		AxaBalance balance = balancesDAO.getBalanceForAccount(accountId, customerId);
		assertNotNull(balance.getAccount());
		assertNotNull(balance.getAmount());
		assertNotNull(balance.getCurrency());
	}
	
	@Test
	public void getBalanceListForAccount() {
		String customerId = "1000000";
		String accountId  = "20000001500";
		List<AxaBalance> balances = balancesDAO.getBalanceListForAccount(accountId, customerId);
		for (AxaBalance b : balances) {
			assertNotNull(b.getDate());
			assertNotNull(b.getAccount());
			assertNotNull(b.getAmount());
			assertNotNull(b.getCurrency());
		}
	}

}
