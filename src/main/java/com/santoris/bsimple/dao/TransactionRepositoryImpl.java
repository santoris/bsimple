package com.santoris.bsimple.dao;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.QueryUtils;

import com.santoris.bsimple.model.Period;
import com.santoris.bsimple.model.Transaction;

public class TransactionRepositoryImpl implements TransactionRepositoryCustom {

	@Autowired
	MongoTemplate template;

	@Override
	@Cacheable("transactions")
	public Page<Transaction> findByAccountsAndPeriod(String customerId,
			List<Long> accountIds, Period period, String labelPart, Pageable pageable, Sort sort) {
		long currentTimeMillis = System.currentTimeMillis();
		System.out.println("*** page number: " + pageable.getPageNumber() + ", page size : " + pageable.getPageSize());

		Query query = query(where("customerId").is(customerId)).addCriteria(
				where("bankAccountId").in(accountIds)).addCriteria(
				where("date").gte(period.getStartDate()).andOperator(
						where("date").lt(period.getEndDate())));
		
		if (labelPart != null && !labelPart.isEmpty()) {
			query = query.addCriteria(where("label").regex(labelPart, "i"));
		}

		long count = template.count(query, Transaction.class);
		
		Query queryWithPagination = QueryUtils.applyPagination(query, pageable);
		Query queryWithPaginationAndSort = QueryUtils.applySorting(
				queryWithPagination, sort);
		List<Transaction> transactions = template.find(
				queryWithPaginationAndSort, Transaction.class);
		System.out.println("*** page number: " + pageable.getPageNumber() + ", page size : " + pageable.getPageSize() + ", ms: " + (System.currentTimeMillis() - currentTimeMillis));
		
		return new PageImpl<Transaction>(transactions, pageable, count);
	}

}
