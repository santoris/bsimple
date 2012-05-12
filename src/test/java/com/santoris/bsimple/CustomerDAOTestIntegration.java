package com.santoris.bsimple;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.santoris.bsimple.dao.CustomerDAO;
import com.santoris.bsimple.model.Customer;

public class CustomerDAOTestIntegration extends BaseSpringConfiguration {
	
	@Autowired
	private CustomerDAO customerDAO;
	
	
	@Test
	public void testGetOneAccountWithRestTemplate() {
		Customer customer = customerDAO.getCustomerById("1000000");
		assertNotNull(customer.getId());
		assertNotNull(customer.getThirdPartyId());
		assertNotNull(customer.getLabel());
	}

}
