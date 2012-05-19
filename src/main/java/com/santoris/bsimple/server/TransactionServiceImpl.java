package com.santoris.bsimple.server;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.springframework.stereotype.Service;

import com.santoris.bsimple.client.service.TransactionService;
import com.santoris.bsimple.model.Transaction;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Override
	public List<Transaction> getAllTransactions(String customerId) {
		// TODO Auto-generated method stub
		return newArrayList();
	}

}
