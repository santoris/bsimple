package com.santoris.bsimple.axa.dao;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.santoris.bsimple.axa.model.AxaOutstandingTransaction;

public class AxaOutstandingTransactionRepositoryImpl implements AxaOutstandingTransactionRepositoryCustom  {

	@Autowired
	MongoTemplate template;
	
	@Override
	public List<AxaOutstandingTransaction> findByAccounts(List<Long> accountIds) {
		List<AxaOutstandingTransaction> transactions = template.find(query(where("account").in(accountIds)), AxaOutstandingTransaction.class);
		return transactions;
	}

}
