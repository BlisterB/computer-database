package com.excilys.computer_database.database.services;

import java.util.List;

import com.excilys.computer_database.database.dao.CompanyDAO;
import com.excilys.computer_database.database.dao.DAOException;
import com.excilys.computer_database.entity.Company;
import com.excilys.computer_database.entity.Computer;
import com.excilys.computer_database.ui.Page;

public class CompaniesService {
	private CompanyDAO dao;

	public CompaniesService() {
		dao = CompanyDAO.getInstance();
	}

	public Page<Company> listSomeCompanies(int begining, int nbPerPage) throws DAOException{
		int pageNumber = begining/nbPerPage + 1;
		List<Company> list = dao.findSome(begining, nbPerPage);
		
		return new Page<Company>(list, pageNumber, nbPerPage);
	}
	
	public List<Company> listAllCompanies() throws DAOException {
		return dao.findAll();
	}

	public void delete(Computer comp) throws DAOException {
		dao.delete(comp.getId());
	}
	
	public void delete(Long id) throws DAOException {
		dao.delete(id);
	}
}
