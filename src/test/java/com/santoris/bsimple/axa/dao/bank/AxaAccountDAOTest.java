package com.santoris.bsimple.axa.dao.bank;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.santoris.bsimple.axa.dao.bank.AxaAccountBankDAO;
import com.santoris.bsimple.axa.model.AxaAccount;
import com.santoris.bsimple.axa.model.AxaAccountSummary;

public class AxaAccountDAOTest {
	
	private AxaAccountBankDAO accountDao;
	
	@Before
	public void init() {
		accountDao = createStrictMock(AxaAccountBankDAO.class);
	}
	
	
	@Test
	public void testGetOneAccountWithRestTemplate() {
		
		AxaAccount mock =  new AxaAccount();
		mock.setCurrency("EUR");
		mock.setCustomer(123456L);
		mock.setLabel("CC");
		expect(accountDao.getAccountById(eq("20000001500"), eq("1000000"))).andReturn(mock);
		replay(accountDao);
		AxaAccount account = accountDao.getAccountById("20000001500", "1000000");
		verify(accountDao);
		
		assertNotNull(account.getCurrency());
		assertNotNull(account.getCustomer());
		assertNotNull(account.getLabel());
	}
	
	@Test
	public void testGetListOfAccountWithRestTemplate() {
		String customerId = "1000000";
		List<AxaAccountSummary> accountListMock = new ArrayList<AxaAccountSummary>();
		AxaAccountSummary mock1 = new AxaAccountSummary();
		mock1.setAccount(12345L);
		mock1.setLabel("CC");
		AxaAccountSummary mock2 = new AxaAccountSummary();
		mock2.setAccount(12345L);
		mock2.setLabel("CC");
		accountListMock.add(mock1);
		accountListMock.add(mock2);
		expect(accountDao.getAccountListForCustomer(customerId)).andReturn(accountListMock);
		replay(accountDao);
		List<AxaAccountSummary> accountList = accountDao.getAccountListForCustomer(customerId);
		verify(accountDao);
		assertTrue(accountList.size() > 0);
		for (AxaAccountSummary summary : accountList) {
			assertNotNull(summary.getAccount());
			assertTrue(summary.getLabel().equals("CC"));
			assertNotNull(summary.getLabel());
		}
		
	}

}
