package com.santoris.bsimple.axa.dao.bank;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.santoris.bsimple.BaseSpringConfiguration;
import com.santoris.bsimple.axa.dao.bank.AxaCustomerBankDAO;
import com.santoris.bsimple.axa.model.AxaCustomer;

public class AxaCustomerDAOTestIntegration extends BaseSpringConfiguration {
	
	@Autowired
	private AxaCustomerBankDAO customerDAO;
	
	
	@Test
	public void testGetOneAccountWithRestTemplate() {
		AxaCustomer customer = customerDAO.getCustomerById(1000000L);
		assertNotNull(customer.getId());
		assertNotNull(customer.getThirdPartyId());
		assertNotNull(customer.getLabel());
	}

}
