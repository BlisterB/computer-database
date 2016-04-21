package com.excilys.computer_database.entity;

import java.time.LocalDateTime;

public class Computer {
	private Long id;
	private String name;
	private LocalDateTime introduced, discontinued;
	private Company company;

	/** Implementation of the Builder pattern */
	public static class ComputerBuilder {
		private Long id;
		private String name;
		private LocalDateTime introduced, discontinued;
		private Company company;
		
		public ComputerBuilder(String name){
			this.name = name;
		}
		public Computer build(){
			return new Computer(this);
		}
		
		public ComputerBuilder id(Long id){
			this.id = id;
			return this;
		}
		public ComputerBuilder name(String name){
			this.name = name;
			return this;
		}
		public ComputerBuilder introduced(LocalDateTime introduced){
			this.introduced = introduced;
			return this;
		}
		public ComputerBuilder discontinued(LocalDateTime discontinued){
			this.discontinued = discontinued;
			return this;
		}
		public ComputerBuilder company(Company company){
			this.company = company;
			return this;
		}
	}
	
	private Computer(ComputerBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company = builder.company;
	}

	public String toString() {
		return this.getId() + "\t" + this.getName() + "\t" + this.getIntroduced() + "\t" + this.getDiscontinued() + "\t"
				+ this.getCompany();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDateTime introduced) {
		this.introduced = introduced;
	}

	public LocalDateTime getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDateTime discontinued) {
		this.discontinued = discontinued;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
