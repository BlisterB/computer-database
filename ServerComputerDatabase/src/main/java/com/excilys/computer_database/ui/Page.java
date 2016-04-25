package com.excilys.computer_database.ui;

import java.util.List;

public class Page<T> {
	public List<T> list;
	int pageNumber, nbPerPage;

	public Page(List<T> list, int pageNumber, int nbPerPage) {
		this.list = list;
		this.pageNumber = pageNumber;
		this.nbPerPage = nbPerPage;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("Page ").append(pageNumber).append(" :\n");

		// The list
		for (Object o : list) {
			sb.append("\t").append(o.toString()).append("\n");
		}

		//
		sb.append("\n");
		// The previous
		if (pageNumber > 0)
			sb.append("\tp : Previous\t");

		// The next
		if (list.size() >= nbPerPage)
			sb.append("\tn : Next");

		// Enter to fish
		sb.append("\tEnter : Validate");

		return sb.toString();
	}
}