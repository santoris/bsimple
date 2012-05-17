package com.santoris.bsimple.axa.importer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santoris.bsimple.axa.dao.AxaBalanceRepository;
import com.santoris.bsimple.axa.dao.AxaCustomerRepository;
import com.santoris.bsimple.axa.dao.AxaOutstandingTransactionRepository;
import com.santoris.bsimple.axa.dao.AxaTransactionRepository;
import com.santoris.bsimple.axa.model.AxaAccount;
import com.santoris.bsimple.axa.model.AxaCustomer;
import com.santoris.bsimple.dao.CustomerRepository;
import com.santoris.bsimple.dao.TransactionRepository;
import com.santoris.bsimple.dao.UserRepository;
import com.santoris.bsimple.model.Account;
import com.santoris.bsimple.model.Customer;
import com.santoris.bsimple.model.IBAN;
import com.santoris.bsimple.model.User;

@Service
public class AxaDataToBsimpleModelImporter {
	
	@Autowired
	private AxaCustomerRepository axaCustomerRepository;
	
	@Autowired
	private AxaTransactionRepository axaTransactionRepository;
	
	@Autowired
	private AxaOutstandingTransactionRepository axaOutstandingTransactionRepository;
	
	@Autowired
	private AxaBalanceRepository axaBalanceRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	public User importDataForAUser(String login, Long bankCustomerId) {
		AxaCustomer axaCustomer = axaCustomerRepository.findOne(bankCustomerId);
		Customer customer = new Customer();
		customer.setBankId(axaCustomer.getId());
		customer.setLabel(axaCustomer.getLabel());
		for (AxaAccount axaAccount : axaCustomer.getAccounts()) {
			Account account = new Account();
			account.setBankId(axaAccount.getId());
			account.setBic(axaAccount.getBic());
			account.setCurrency(axaAccount.getCurrency());
			
			String[] ibanArray = axaAccount.getIban();
			if (ibanArray == null) {
				ibanArray = new String[]{"**", "**", "***********************"};
			}
			IBAN iban = new IBAN();
			iban.setCountryCode(ibanArray[0]);
			iban.setCheckDigits(ibanArray[1]);
			iban.setBban(ibanArray[2]);
			account.setIban(iban);
			
			account.setLabel(axaAccount.getLabel());
			account.setType(axaAccount.getType());
			
			customer.addAcount(account);
		}
		
		customerRepository.save(customer);
		
		User user = new User();
		user.setLogin(login);
		user.setCustomer(customer);
		
		userRepository.save(user);
		
		return user;
	}
}
