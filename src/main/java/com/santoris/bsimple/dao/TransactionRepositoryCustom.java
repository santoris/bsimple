package com.santoris.bsimple.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import com.santoris.bsimple.model.Period;
import com.santoris.bsimple.model.Transaction;

public interface TransactionRepositoryCustom {

	public Page<Transaction> findByAccountsAndPeriod(
			Pageable pageable, final String userId,
			final List<Long> accountIds, final Period period);

}
