package com.santoris.bsimple.axa.dao.bank;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.santoris.bsimple.BaseSpringConfiguration;
import com.santoris.bsimple.axa.dao.bank.AxaAccountBankDAO;
import com.santoris.bsimple.axa.dao.bank.AxaBalanceBankDAO;
import com.santoris.bsimple.axa.model.AxaAccount;
import com.santoris.bsimple.axa.model.AxaAccountSummary;
import com.santoris.bsimple.axa.model.AxaBalance;

public class AxaAccountDAOTestIntegration extends BaseSpringConfiguration {
	
	@Autowired
	private AxaAccountBankDAO accountDao;
	
	@Autowired
	private AxaBalanceBankDAO balancesDAO;
	
	@Test
	public void testGetOneAccountWithRestTemplate() {
		AxaAccount account = accountDao.getAccountById("20000001500", "1000000");
		assertNotNull(account.getCurrency());
		assertNotNull(account.getCustomer());
		assertNotNull(account.getLabel());
	}
	
	@Test
	public void testGetListOfAccountWithRestTemplate() {
		String customerId = "1000000";
		List<AxaAccountSummary> accountList = accountDao.getAccountListForCustomer(customerId);
		assertTrue(accountList.size() > 0);
		for (AxaAccountSummary summary : accountList) {
			assertNotNull(summary.getAccount());
			assertNotNull(summary.getLabel());
		}
		
	}
	
	@Test
	public void testGetListOfAccountAndBalanceWithRestTemplate() {
		String customerId = "1000000";
		List<AxaAccountSummary> accountList = accountDao.getAccountListForCustomer(customerId);
		assertTrue(accountList.size() > 0);
		for (AxaAccountSummary summary : accountList) {
			assertNotNull(summary.getAccount());
			assertNotNull(summary.getLabel());
			AxaBalance b = balancesDAO.getBalanceForAccount(String.valueOf(summary.getAccount()), customerId);
			assertNotNull(b.getAmount());
		}
		
	}

}
