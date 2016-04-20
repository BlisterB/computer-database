package com.excilys.computer_database.database.services;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computer_database.database.dao.CompanyDAO;
import com.excilys.computer_database.model.Company;

public class CompaniesService {
	private CompanyDAO dao;

	public CompaniesService() {
		dao = CompanyDAO.getInstance();
	}

	public List<Company> listAllCompanies() throws SQLException {
		return dao.findAll();
	}
}
