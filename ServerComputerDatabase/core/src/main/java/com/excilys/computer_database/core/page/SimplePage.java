package com.excilys.computer_database.core.page;

import java.util.List;

public class SimplePage<T> {
	private List<T> list;
	private int pageNumber, size, elementTotalCount, pageTotalCount;

	public SimplePage() {
		
	}
	
	public SimplePage(List<T> list, int pageNumber, int size, int totalCount, int pageTotalCount) {
		super();
		this.list = list;
		this.pageNumber = pageNumber;
		this.size = size;
		this.elementTotalCount = totalCount;
		this.pageTotalCount = pageTotalCount;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getElementTotalCount() {
		return elementTotalCount;
	}

	public void setElementTotalCount(int elementTotalCount) {
		this.elementTotalCount = elementTotalCount;
	}

	public int getPageTotalCount() {
		return pageTotalCount;
	}

	public void setPageTotalCount(int pageTotalCount) {
		this.pageTotalCount = pageTotalCount;
	}
}
