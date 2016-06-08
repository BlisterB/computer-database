package com.excilys.computer_database.webapp.rest;

import java.util.LinkedList;
import java.util.List;

import com.excilys.computer_database.core.dto.ComputerDTO;
import com.excilys.computer_database.core.entity.Company;

public class RestResponse {
	List<ComputerDTO> listComputer = new LinkedList<>();
	List<Company> listCompany = new LinkedList<>();

	public List<ComputerDTO> getListComputer() {
		return listComputer;
	}

	public void setListComputer(List<ComputerDTO> listComputer) {
		this.listComputer = listComputer;
	}

	public List<Company> getListCompany() {
		return listCompany;
	}

	public void setListCompany(List<Company> listCompany) {
		this.listCompany = listCompany;
	}
}
