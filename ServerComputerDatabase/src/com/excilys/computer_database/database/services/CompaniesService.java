package com.excilys.computer_database.database.services;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computer_database.database.dao.CompanyDAO;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;

public class CompaniesService {
	private CompanyDAO dao;

	public CompaniesService() {
		dao = CompanyDAO.getInstance();
	}

	public List<Company> listAllCompanies() throws SQLException {
		return dao.findAll();
	}

	public void delete(Computer comp) throws SQLException {
		dao.delete(comp.getId());
	}
	
	public void delete(Long id) throws SQLException {
		dao.delete(id);
	}
}
