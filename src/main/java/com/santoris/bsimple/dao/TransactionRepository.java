package com.santoris.bsimple.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.santoris.bsimple.model.Transaction;

public interface TransactionRepository extends MongoRepository<Transaction, String>, TransactionRepositoryCustom {

	public List<Transaction> findByBankAccountId(Long bankAccountId);

}
