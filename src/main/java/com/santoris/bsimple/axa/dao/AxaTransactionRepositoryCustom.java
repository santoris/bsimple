package com.santoris.bsimple.axa.dao;

import java.util.List;

import com.santoris.bsimple.axa.model.AxaTransaction;

public interface AxaTransactionRepositoryCustom {

	public List<AxaTransaction> findByAccounts(List<Long> accountIds);
	
}
