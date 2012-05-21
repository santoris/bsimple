package com.santoris.bsimple.axa.dao;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.santoris.bsimple.axa.model.AxaTransaction;

public class AxaTransactionRepositoryImpl implements AxaTransactionRepositoryCustom  {

	@Autowired
	MongoTemplate template;
	
	@Override
	public List<AxaTransaction> findByAccounts(List<Long> accountIds) {
		List<AxaTransaction> transactions = template.find(query(where("account").in(accountIds)), AxaTransaction.class);
		return transactions;
	}

}
