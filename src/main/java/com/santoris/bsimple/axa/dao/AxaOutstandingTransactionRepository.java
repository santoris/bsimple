package com.santoris.bsimple.axa.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.santoris.bsimple.axa.model.AxaOutstandingTransaction;

public interface AxaOutstandingTransactionRepository extends MongoRepository<AxaOutstandingTransaction, Long> {

}
