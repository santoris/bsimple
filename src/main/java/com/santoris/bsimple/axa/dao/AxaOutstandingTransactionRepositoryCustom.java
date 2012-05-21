package com.santoris.bsimple.axa.dao;

import java.util.List;

import com.santoris.bsimple.axa.model.AxaOutstandingTransaction;

public interface AxaOutstandingTransactionRepositoryCustom {
	
	public List<AxaOutstandingTransaction> findByAccounts(List<Long> accountIds);
	
}
