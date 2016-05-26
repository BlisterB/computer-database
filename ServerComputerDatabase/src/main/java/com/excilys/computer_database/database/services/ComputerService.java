package com.excilys.computer_database.database.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computer_database.database.dao.ComputerDAO;
import com.excilys.computer_database.database.dao.DAOException;
import com.excilys.computer_database.database.dtos.ComputerDTO;
import com.excilys.computer_database.entity.Company;
import com.excilys.computer_database.entity.Computer;
import com.excilys.computer_database.ui.Page;

@Service
@Transactional
public class ComputerService {
    @Autowired
    private ComputerDAO computerDAO;

    public static enum COLUMN {
        COMPUTER_NAME, INTRODUCED, DISCONTINUED, COMPANY_NAME
    };

    public static enum ORDER {
        ASC, DESC
    }

    /**
     * Return the computer of id id.
     * @param id The id
     * @return The computer with the id field to "id"
     * @throws DAOException In case of DAO problem
     */
    public Computer getComputerById(Long id) throws DAOException {
        Computer computer = computerDAO.find(id);
        return computer;
    }

    /**
     * @param begining
     * @param nbPerPage
     * @param orderBy "id", "name", "introduced", "discontinued" or "company"
     * @return
     * @throws DAOException
     */
    public Page<ComputerDTO> listComputersDTO(COLUMN column, ORDER order, String search, int begining, int pageSize)
            throws DAOException {
        return computerDAO.listComputersDTO(column, order, search, begining, pageSize);
    }

    public int countListResult(String search) {
        return computerDAO.countSearchResult(search);
    }

    /**
     * Return the list of all computers.
     * @return The list of all computers
     * @throws DAOException In case of DAO problem
     */
    public List<Computer> listAllComputers() throws DAOException {
        return computerDAO.findAll();
    }

    /**
     * Update the computer in the DB.
     * @param comp The computer to update
     * @return The computer
     * @throws DAOException In case of DAO problem
     */
    public Computer update(Computer comp) throws DAOException {
        return computerDAO.update(comp);
    }
    public Computer update(ComputerDTO comp) throws DAOException {
        Computer c = new Computer.ComputerBuilder(comp.getName())
                .id(comp.getId())
                .introduced(comp.getIntroduced())
                .discontinued(comp.getDiscontinued())
                .company(new Company(comp.getCompanyId(), comp.getCompanyName()))
                .build();
        return computerDAO.update(c);
    }

    /**
     * Create the computer in the DB.
     * @param computer The computer to put in the DB
     * @return The computer
     * @throws DAOException In case of DAO problem
     */
    public Computer createComputer(Computer computer) throws DAOException {
        return computerDAO.create(computer);
    }

    public Computer createComputer(ComputerDTO comp) throws DAOException {
        Computer c = new Computer.ComputerBuilder(comp.getName())
                .introduced(comp.getIntroduced())
                .discontinued(comp.getDiscontinued())
                .company(new Company(comp.getCompanyId(), comp.getCompanyName()))
                .build();
        return computerDAO.create(c);
    }

    /**
     * Delete the computer from the DB.
     * @param comp The computer to delete from the DB
     * @throws DAOException In case of DAO problem
     */
    public void delete(Computer comp) throws DAOException {
        computerDAO.delete(comp.getId());
    }

    /**
     * Delete the computer from the DB.
     * @param id The id of the computer to delete from the DB
     * @throws DAOException In case of DAO problem
     */
    public void delete(Long id) throws DAOException {
        computerDAO.delete(id);
    }

    public void deleteComputerList(Long[] idList) {
        computerDAO.deleteComputerList(idList);
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