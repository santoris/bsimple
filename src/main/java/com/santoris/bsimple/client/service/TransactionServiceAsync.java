package com.santoris.bsimple.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.santoris.bsimple.model.Period;
import com.santoris.bsimple.model.Transaction;
import com.santoris.bsimple.page.Page;
import com.santoris.bsimple.page.PageRequest;

public interface TransactionServiceAsync {

	void findTransactionsByAccountIdsByPeriod(final PageRequest pageRequest, final String userId,
			final List<Long> accountIds, final Period period, final AsyncCallback<Page<Transaction>> callback);

}
