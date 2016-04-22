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
		System.out.println("acaoiucaoiza");
		StringBuffer sb = new StringBuffer();
		sb.append("coucou je suis toString");

		// The list
		for (Object o : list) {
			sb.append(o);
		}

		// The previous
		if (pageNumber > 0)
			sb.append("\tp : Previous\t)");

		// The next
		if (list.size() < nbPerPage)
			sb.append("\tn : Next");

		// Enter to fish
		sb.append("\tEnter : Validate");

		return sb.length() + "10";
	}
}