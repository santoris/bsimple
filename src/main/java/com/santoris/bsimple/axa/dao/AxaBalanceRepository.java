package com.santoris.bsimple.axa.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.santoris.bsimple.axa.model.AxaBalance;

public interface AxaBalanceRepository extends MongoRepository<AxaBalance, Long> {

}
