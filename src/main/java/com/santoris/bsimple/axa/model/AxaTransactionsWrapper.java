package com.santoris.bsimple.axa.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class AxaTransactionsWrapper {

	/**
	 * hasMore is equal to false if all data is contained in the array else false.
	 */
	@JsonProperty(value = "has_more")
	private boolean hasMore;
	
	private AxaTransaction[] transactions;

	public boolean isHasMore() {
		return hasMore;
	}

	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}

	public AxaTransaction[] getTransactions() {
		return transactions;
	}

	public void setTransactions(AxaTransaction[] transactions) {
		this.transactions = transactions;
	}

}
