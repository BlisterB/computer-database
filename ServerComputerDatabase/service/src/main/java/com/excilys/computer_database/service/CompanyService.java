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
import com.excilys.computer_database.persistence.DAOException;
import com.excilys.computer_database.persistence.dao.CompanyDAO;
import com.excilys.computer_database.persistence.dao.ComputerDAO;

@Service
@Transactional
public class CompanyService {
    @Autowired
    private CompanyDAO companyDAO;
    @Autowired
    private ComputerDAO computerDAO;

    /** Default constructor. */
    public CompanyService() {

    }

    /**
     * @return A page containing a number of "nbPerPage" companies from the
     *         position "begining" (when ranked by if).
     * @param begining The begining of the listed companies in the DB
     * @param nbPerPage Number of Companies to fetch
     * @throws DAOException
     */
    public SimplePage<Company> listSomeCompanies(int pageNumber, int size) {
        Page<Company> page = companyDAO.findAll(new PageRequest(pageNumber, size));
        return new SimplePage<Company>(page.getContent(), pageNumber, size, (int)page.getTotalElements(), page.getTotalPages());
    }

    /**
     * Return the lis of all the companies.
     * @return The list of all listed companies
     * @throws DAOException
     */
    public Iterable<Company> listAllCompanies() {
        return companyDAO.findAll();
    }

    /**
     * Delete the company of id "id", and the associated computers via a
     * transaction.
     * @param id The computer's id to delete
     * @throws DAOException
     */
    public void delete(Long id) {
        // Delete related computers
        computerDAO.removeByCompany_Id(id);

        // Delete the company
        companyDAO.delete(id);
    }

    public Company find(Long id) {
        return companyDAO.findOne(id);
    }

    /**
     * Return the list of all companies (DTO form)
     * @return Return the list of all companies (DTO form)
     * @throws DAOException If an error occurs in the DAO
     */
    public List<CompanyDTO> getDTOList() throws DAOException {
        Iterable<Company> companies = listAllCompanies();
        List<CompanyDTO> dtoList = new LinkedList<CompanyDTO>();
        for (Company company : companies) {
            dtoList.add(new CompanyDTO(company));
        }

        return dtoList;
    }

    /**
     * @return the companyDAO
     */
    public CompanyDAO getCompanyDAO() {
        return companyDAO;
    }

    /**
     * @param companyDAO the companyDAO to set
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
     * @param computerDAO the computerDAO to set
     */
    public void setComputerDAO(ComputerDAO computerDAO) {
        this.computerDAO = computerDAO;
    }
}