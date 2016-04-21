package com.excilys.computer_database.database.services;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computer_database.database.dao.CompanyDAO;
import com.excilys.computer_database.entity.Company;
import com.excilys.computer_database.entity.Computer;
import com.excilys.computer_database.ui.Page;

public class CompaniesService {
	private CompanyDAO dao;

	public CompaniesService() {
		dao = CompanyDAO.getInstance();
	}

	public Page<Company> listSomeCompanies(int begining, int nbPerPage) throws SQLException{
		boolean first = (begining == 0);
		List<Company> list = dao.findSome(begining, nbPerPage);
		boolean last = list.size() <= nbPerPage;
		
		return new Page<Company>(list, first, last);
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
