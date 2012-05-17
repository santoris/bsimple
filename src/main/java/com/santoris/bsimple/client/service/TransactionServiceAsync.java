package com.santoris.bsimple.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.santoris.bsimple.model.Transaction;

public interface TransactionServiceAsync {
	
	void getAllTransactions(String customerId, AsyncCallback<List<Transaction>> callback);
	
}
