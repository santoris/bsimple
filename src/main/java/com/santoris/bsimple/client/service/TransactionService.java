package com.santoris.bsimple.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.santoris.bsimple.model.Transaction;

@RemoteServiceRelativePath("transaction")
public interface TransactionService extends RemoteService {
	
	  List<Transaction> getAllTransactions(String customerId);
	  
}
