package com.excilys.computer_database.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computer_database.core.dto.CompanyDTO;
import com.excilys.computer_database.core.entity.Company;
import com.excilys.computer_database.core.page.SimplePage;
import com.excilys.computer_database.persistence.dao.CompanyDAO;
import com.excilys.computer_database.persistence.dao.ComputerDAO;

@Service("companyService")
@Transactional
public class CompanyServiceImpl implements CompanyService{

	@Autowired
	private CompanyDAO companyDAO;
	@Autowired
	private ComputerDAO computerDAO;

	/** Default constructor. */
	public CompanyServiceImpl() { }

	public SimplePage<Company> listSomeCompanies(int pageNumber, int size) {
		Page<Company> page = companyDAO.findAll(new PageRequest(pageNumber, size));
		return new SimplePage<Company>(page.getContent(), pageNumber, size, (int) page.getTotalElements(),
				page.getTotalPages());
	}

	public Iterable<Company> listAllCompanies() {
		return companyDAO.findAll();
	}

	public List<CompanyDTO> getDTOList() {
		Iterable<Company> companies = listAllCompanies();
		List<CompanyDTO> dtoList = new LinkedList<CompanyDTO>();
		for (Company company : companies) {
			dtoList.add(new CompanyDTO(company));
		}

		return dtoList;
	}

	public void delete(Long id) {
		// Delete related computers
		computerDAO.removeByCompany_Id(id);

		// Delete the company
		companyDAO.delete(id);
	}

	public Company find(Long id) {
		return companyDAO.findOne(id);
	}


	// Getters/Setters
	
	/**
	 * @return the companyDAO
	 */
	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	/**
	 * @param companyDAO
	 *            the companyDAO to set
	 */
	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	/**
	 * @return the computerDAO
	 */
	public ComputerDAO getComputerDAO() {
		return computerDAO;
	}

	/**
	 * @param computerDAO
	 *            the computerDAO to set
	 */
	public void setComputerDAO(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}
}