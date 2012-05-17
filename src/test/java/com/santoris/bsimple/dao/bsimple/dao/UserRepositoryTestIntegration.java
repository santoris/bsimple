package com.santoris.bsimple.dao.bsimple.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.santoris.bsimple.BaseSpringConfiguration;
import com.santoris.bsimple.dao.CustomerRepository;
import com.santoris.bsimple.dao.UserRepository;
import com.santoris.bsimple.model.Account;
import com.santoris.bsimple.model.Customer;
import com.santoris.bsimple.model.IBAN;
import com.santoris.bsimple.model.User;

public class UserRepositoryTestIntegration extends BaseSpringConfiguration {

	private static final String LOGIN = "tabalea";

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	CustomerRepository customerRepo;
	
	@Autowired
	MongoTemplate template;

	@Before
	public void setup() {
		IBAN iban = new IBAN();
		iban.setCountryCode("FR");
		iban.setCheckDigits("**");
		iban.setBban("12548****************21");
		
		Account account = new Account();
		account.setCurrency("EUR");
		account.setBic("AXABFRPPXXX");
		account.setBankId(20000001500L);
		account.setLabel("CC");
		account.setType("checking");
		account.setIban(iban);
		
		Customer customer = new Customer();
		customer.setBankId(1000000L);
		customer.setLabel("THEO");
		customer.addAcount(account);
		
		customerRepo.save(customer);

		User user = new User();
		user.setLogin(LOGIN);
		user.setCustomer(customer);

		userRepo.save(user);
	}

	@After
	public void teardown() {
		customerRepo.deleteAll();
		userRepo.deleteAll();
	}

	@Test
	public void testFindByLogin() {
		User user = userRepo.findByLogin(LOGIN);
		assertNotNull(user);
		System.out.println("user : " + user);
	}
	
	@Test
	public void testQueryEmbedded() {
		List<Customer> customers = template.find(query(where("accounts.bankId").is(20000001500L)), Customer.class);
		assertEquals(1, customers.size());
		System.out.println("customers: " + customers.get(0));
	}
}
