package com.excilys.computer_database.model;

import java.sql.Date;

public class Computer {
	private Long id, company_id;
	private String name;
	private Date introduced, discontinued;

	public Computer() {}

	public Computer(Long id, String name, Date introduced, Date discontinued, Long company_id) {
		this.id = id;
		this.company_id = company_id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Long company_id) {
		this.company_id = company_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}

	public String toString() {
		return this.getId() + "\t" + this.getName() + "\t" + this.getIntroduced() + "\t" + this.getDiscontinued() + "\t"
				+ this.getCompany_id();
	}
}
