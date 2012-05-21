package com.santoris.bsimple.page;

import java.io.Serializable;

import com.google.common.base.Objects;

public class PageRequest implements Serializable {

	private int pageNumber;

	private int pageSize;

	public PageRequest() {
		pageNumber = 0;
		pageSize = 0;
	}
	
	public PageRequest(final int pageNumber, final int pageSize) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("pageNumber", pageNumber)
				.add("pageSize", pageSize)
				.toString();
	}
}
