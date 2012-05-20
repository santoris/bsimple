package com.santoris.bsimple.dao;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.santoris.bsimple.model.Period;
import com.santoris.bsimple.model.Transaction;

@Repository
public class TransactionRepositoryCustomImpl implements
		TransactionRepositoryCustom {

	@Autowired
	MongoTemplate template;

	@Override
	public Page<Transaction> findByAccountsAndPeriod(
			Pageable pageable, String userId, List<Long> accountIds,
			Period period) {
		List<Transaction> transactions = template.find(query(where("account").in(accountIds)), Transaction.class);
		return new PageImpl(transactions);
	}

}
