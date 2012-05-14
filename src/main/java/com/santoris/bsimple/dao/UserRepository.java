package com.santoris.bsimple.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.santoris.bsimple.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	public User findByLogin(String login);

}
