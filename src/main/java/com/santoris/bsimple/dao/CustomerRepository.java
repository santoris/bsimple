package com.santoris.bsimple.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.santoris.bsimple.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

}
