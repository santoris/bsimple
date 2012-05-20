package com.santoris.bsimple.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.santoris.bsimple.model.Period;
import com.santoris.bsimple.model.Transaction;
import com.santoris.bsimple.page.Page;
import com.santoris.bsimple.page.PageRequest;

@RemoteServiceRelativePath("transaction")
public interface TransactionService extends RemoteService {
	
	  Page<Transaction> findTransactionsByAccountIdsByPeriod(final PageRequest pageRequest, final String userId, final List<Long> accountIds, final Period period);
	  
}
