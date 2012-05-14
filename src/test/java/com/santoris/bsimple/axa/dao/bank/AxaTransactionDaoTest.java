package com.santoris.bsimple.axa.dao.bank;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertTrue;
import static org.easymock.EasyMock.expect;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.santoris.bsimple.axa.dao.bank.AxaTransactionBankDAO;
import com.santoris.bsimple.axa.model.AxaTransaction;

public class AxaTransactionDaoTest {
	
	private AxaTransactionBankDAO transactionDao;
	
	@Before
	public void init() {
		transactionDao = createStrictMock(AxaTransactionBankDAO.class);
	}
	
	@Test
	public void testTransactionListOk() {
		final String accountId = "20000001500";
		final String customerId= "1000000";
		List<AxaTransaction> transactionListMock = Lists.newArrayList();
		transactionListMock.add(new AxaTransaction());
		transactionListMock.add(new AxaTransaction());
		transactionListMock.add(new AxaTransaction());
		Function<AxaTransaction, AxaTransaction> setLabelFunction = new Function<AxaTransaction, AxaTransaction>() {
            @Override
            public AxaTransaction apply(AxaTransaction o) {
                if (o == null) {
                    return null;
                }
                AxaTransaction toinit = (AxaTransaction) o;
                toinit.setLabel("virement");
                return toinit;
            }
        };
        List<AxaTransaction> buildList = Lists.transform(transactionListMock, setLabelFunction);
        
        
        expect(transactionDao.getTransactionList(accountId, customerId, "1")).andReturn(buildList);
        replay(transactionDao);
		List<AxaTransaction> transactionList = transactionDao.getTransactionList(accountId,customerId, "1");
		verify(transactionDao);
		
		assertTrue(transactionList.size() == 3);
		for (AxaTransaction t : transactionList) {
			assertTrue(t.getLabel().equals("virement"));
		}
		
	}
}
