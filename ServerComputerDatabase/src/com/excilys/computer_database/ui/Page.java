package com.excilys.computer_database.ui;

import java.util.List;

public class Page<T> {
	public List<T> list;
	public boolean first, last;
	
	public Page(List<T> list, boolean first, boolean last) {
		this.list = list;
		this.first = first;
		this.last = last;
	}

	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		
		// The list
		for(Object o : list){
			sb.append(o);
		}
		
		// The previous
		if(!first)
			sb.append("\tp : Previous\t)");
		
		// The next
		if(!last)
			sb.append("\tn : Next");
		
		// Enter to fish
		sb.append("\tEnter : Validate");
	
		return sb.toString();
	}
}