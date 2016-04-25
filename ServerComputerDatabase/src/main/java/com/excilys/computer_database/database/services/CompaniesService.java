package com.excilys.computer_database.database.services;

import java.util.List;

import com.excilys.computer_database.database.dao.CompanyDAO;
import com.excilys.computer_database.database.dao.DAOException;
import com.excilys.computer_database.entity.Company;
import com.excilys.computer_database.entity.Computer;
import com.excilys.computer_database.ui.Page;

public class CompaniesService {
    private CompanyDAO dao;

    /** Constructor by default. */
    public CompaniesService() {
        dao = CompanyDAO.getInstance();
    }

    /** @return A page containing a number of "nbPerPage" companies from the position "begining" (when ranked by if).
     *  @param begining The begining of the listed companies in the DB
     *  @param nbPerPage Number of Companies to fetch
     *  @throws DAOException */
    public Page<Company> listSomeCompanies(int begining, int nbPerPage) throws DAOException {
        int pageNumber = begining / nbPerPage + 1;
        List<Company> list = dao.findSome(begining, nbPerPage);

        return new Page<Company>(list, pageNumber, nbPerPage);
    }

    /** Return the lis of all the companies.
     * @return The list of all listed companies
     * @throws DAOException */
    public List<Company> listAllCompanies() throws DAOException {
        return dao.findAll();
    }

    /** Delete the computer "comp" using its id.
     *  @param comp The computer to delete
     *  @throws DAOException */
    public void delete(Computer comp) throws DAOException {
        dao.delete(comp.getId());
    }

    /** Delete the computer of id "id".
     *  @param id The computer's id to delete
     *  @throws DAOException */
    public void delete(Long id) throws DAOException {
        dao.delete(id);
    }
}