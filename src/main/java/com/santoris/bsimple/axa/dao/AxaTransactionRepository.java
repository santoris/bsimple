package com.santoris.bsimple.axa.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.santoris.bsimple.axa.model.AxaTransaction;

public interface AxaTransactionRepository extends MongoRepository<AxaTransaction, Long> {

}
