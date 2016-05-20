package com.excilys.computer_database.database.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.excilys.computer_database.database.dao.ComputerDAO;
import com.excilys.computer_database.database.dao.DAOException;
import com.excilys.computer_database.database.dtos.ComputerDTO;
import com.excilys.computer_database.entity.Computer;
import com.excilys.computer_database.ui.Page;

public class ComputerService {
    public static enum COLUMN {
        COMPUTER_NAME, INTRODUCED, DISCONTINUED, COMPANY_NAME
    };

    public static enum ORDER {
        ASC, DESC
    }

    private ComputerDAO computerDAO;

    /**
     * Return the computer of id id.
     * @param id The id
     * @return The computer with the id field to "id"
     * @throws DAOException In case of DAO problem
     */
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
    public Page<ComputerDTO> listComputersDTO(COLUMN column, ORDER order, String search, int begining, int pageSize)
            throws DAOException {
        Page<ComputerDTO> page = computerDAO.listComputersDTO(column, order, search, begining, pageSize);
        return page;
    }

    @Transactional(readOnly=true)
    public int countListResult(String search) {
        int c = computerDAO.countSearchResult(search);
        return c;
    }

    /**
     * Return the list of all computers.
     * @return The list of all computers
     * @throws DAOException In case of DAO problem
     */
    @Transactional(readOnly=true)
    public List<Computer> listAllComputers() throws DAOException {
        List<Computer> list = computerDAO.findAll();
        return list;
    }

    /**
     * Update the computer in the DB.
     * @param comp The computer to update
     * @return The computer
     * @throws DAOException In case of DAO problem
     */
    @Transactional(readOnly=false)
    public Computer update(Computer comp) throws DAOException {
        Computer computer = computerDAO.update(comp);
        return computer;
    }

    /**
     * Create the computer in the DB.
     * @param computer The computer to put in the DB
     * @return The computer
     * @throws DAOException In case of DAO problem
     */
    @Transactional(readOnly=false)
    public Computer createComputer(Computer computer) throws DAOException {
        Computer c = computerDAO.create(computer);
        return c;
    }

    /**
     * Delete the computer from the DB.
     * @param comp The computer to delete from the DB
     * @throws DAOException In case of DAO problem
     */
    @Transactional(readOnly=false)
    public void delete(Computer comp) throws DAOException {
        computerDAO.delete(comp.getId());
    }

    /**
     * Delete the computer from the DB.
     * @param id The id of the computer to delete from the DB
     * @throws DAOException In case of DAO problem
     */
    @Transactional(readOnly=false)
    public void delete(Long id) throws DAOException {
        computerDAO.delete(id);
    }

    @Transactional(readOnly=false)
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