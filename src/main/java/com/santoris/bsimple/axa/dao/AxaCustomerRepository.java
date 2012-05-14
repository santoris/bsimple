package com.santoris.bsimple.axa.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.santoris.bsimple.axa.model.AxaCustomer;

public interface AxaCustomerRepository extends MongoRepository<AxaCustomer, Long> {

}
