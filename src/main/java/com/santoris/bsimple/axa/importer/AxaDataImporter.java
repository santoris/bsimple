package com.santoris.bsimple.axa.importer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santoris.bsimple.axa.dao.AxaBalanceRepository;
import com.santoris.bsimple.axa.dao.AxaCustomerRepository;
import com.santoris.bsimple.axa.dao.AxaOutstandingTransactionRepository;
import com.santoris.bsimple.axa.dao.AxaTransactionRepository;
import com.santoris.bsimple.axa.dao.bank.AxaBalanceBankDAO;
import com.santoris.bsimple.axa.dao.bank.AxaCustomerBankDAO;
import com.santoris.bsimple.axa.dao.bank.AxaTransactionBankDAO;
import com.santoris.bsimple.axa.model.AxaAccount;
import com.santoris.bsimple.axa.model.AxaBalance;
import com.santoris.bsimple.axa.model.AxaCustomer;
import com.santoris.bsimple.axa.model.AxaOutstandingTransaction;
import com.santoris.bsimple.axa.model.AxaTransaction;

@Service
public class AxaDataImporter {

	private final Long[] customers = { 1000000L, 1000001L, 1000002L, 1500000L };
	
	@Autowired
	private AxaCustomerBankDAO customerDAO;
	
	@Autowired
	private AxaTransactionBankDAO transactionDAO;
	
	@Autowired
	private AxaBalanceBankDAO balanceDAO;
	
	@Autowired
	private AxaCustomerRepository customerRepository;
	
	@Autowired
	private AxaTransactionRepository transactionRepository;
	
	@Autowired
	private AxaOutstandingTransactionRepository outstandingTransactionRepository;
	
	@Autowired
	private AxaBalanceRepository balanceRepository;
	
	public void importAllData() {
		Set<Long> transactionBankId = new HashSet<Long>(8000);
		Set<Long> outstandingTransactionBankId = new HashSet<Long>(8000);
		int i = 0;
		int j = 0;
		for (Long customerId : customers) {
			AxaCustomer customer = customerDAO.getCustomerById(customerId);
			customerRepository.save(customer);
			
			for (AxaAccount account : customer.getAccounts()) {
				Long accountId = account.getId();
				
				List<AxaTransaction> transactions = transactionDAO.getAllTransactionsByAccount(accountId, customerId);
				for (AxaTransaction transaction : transactions) {
					if (transactionBankId.contains(transaction.getId())) {
						i++;
					} else {
						transactionBankId.add(transaction.getId());
					}
				}
				transactionRepository.save(transactions);
				
				List<AxaOutstandingTransaction> outstandingTransactions = transactionDAO.getAllOutstandingTransactionsByAccount(accountId, customerId);
				for (AxaOutstandingTransaction transaction : outstandingTransactions) {
					if (outstandingTransactionBankId.contains(transaction.getId())) {
						j++;
					} else {
						outstandingTransactionBankId.add(transaction.getId());
					}
				}
				outstandingTransactionRepository.save(outstandingTransactions);
				
				List<AxaBalance> balances = balanceDAO.getAllBalancesByAccount(accountId, customerId);
				balanceRepository.save(balances);
			}
		}
		System.out.println("i: " + i);
		System.out.println("j: " + j);
	}
}
