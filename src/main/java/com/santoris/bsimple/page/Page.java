package com.santoris.bsimple.page;

import java.io.Serializable;
import java.util.List;

import com.google.common.base.Objects;

public class Page<T> implements Serializable {

	private PageRequest pageRequest;
	
	private List<T> content;

	private long total;

	public Page() {
		this.pageRequest = null;
		this.content = null;
		this.total = 0;
	}
	
	public Page(final PageRequest pageRequest, final List<T> content, final long total) {
		this.pageRequest = pageRequest;
		this.content = content;
		this.total = total;
	}
	
	public List<T> getContent() {
		return content;
	}

	public PageRequest getPageRequest() {
		return pageRequest;
	}

	public long getTotal() {
		return total;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("pageRequest", pageRequest)
				.add("total", total)
				.toString();
	}
}
