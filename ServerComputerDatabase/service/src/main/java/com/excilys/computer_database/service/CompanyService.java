package com.excilys.computer_database.service;

import java.util.List;

import com.excilys.computer_database.core.dto.CompanyDTO;
import com.excilys.computer_database.core.entity.Company;
import com.excilys.computer_database.core.page.SimplePage;

public interface CompanyService {
	/**
	 * @return A page containing the companies.
	 * @param pageNumber
	 *            The page number
	 * @param size
	 *            The page size
	 */
	public SimplePage<Company> listSomeCompanies(int pageNumber, int size);

	/**
	 * @return An Iterable of all the companies
	 */
	public Iterable<Company> listAllCompanies();

	/**
	 * Return the list of all companyDTO
	 * 
	 * @return Return the list of all companyDTO
	 */
	public List<CompanyDTO> getDTOList() ;

	/**
	 * @return The company to find
	 * @param id
	 *            The id
	 */
	public Company find(Long id);

	/**
	 * Delete the corresponding company, and the associated computers.
	 * 
	 * @param id
	 *            The computer's id to delete
	 */
	public void delete(Long id);
}
