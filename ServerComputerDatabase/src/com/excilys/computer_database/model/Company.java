package com.excilys.computer_database.model;

public class Company {
	private String name;
	private Long id;
	
	public Company(){
	}
	public Company(Long id, String name){
		this.name = name;
		this.id = id;
	}
	public Company(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String toString(){
		return this.getId() + "\t" + this.getName();
	}
}
