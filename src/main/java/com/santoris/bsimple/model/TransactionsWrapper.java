package com.santoris.bsimple.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class TransactionsWrapper {

	@JsonProperty(value = "has_more")
	private boolean hasMore;
	
	private Transaction[] transactions;

	public boolean isHasMore() {
		return hasMore;
	}

	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}

	public Transaction[] getTransactions() {
		return transactions;
	}

	public void setTransactions(Transaction[] transactions) {
		this.transactions = transactions;
	}

}
