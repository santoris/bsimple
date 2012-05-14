package com.santoris.bsimple.axa.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * {@link AxaBalance}
 * 
 */
public class AxaBalancesWrapper {

	/**
	 * hasMore is equal to false if all data is contained in the array else
	 * false.
	 */
	@JsonProperty(value = "has_more")
	private boolean hasMore;

	private AxaBalance[] balances;

	public AxaBalance[] getBalances() {
		return balances;
	}

	public void setBalances(AxaBalance[] balances) {
		this.balances = balances;
	}

	public boolean isHasMore() {
		return hasMore;
	}

	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}

}
