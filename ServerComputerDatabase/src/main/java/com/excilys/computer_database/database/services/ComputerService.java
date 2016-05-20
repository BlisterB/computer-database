package com.excilys.computer_database.database.services;

import java.util.List;

import com.excilys.computer_database.database.DBManager;
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
    public Computer getComputerById(Long id) throws DAOException {
        Computer computer = computerDAO.find(id);
        DBManager.closeConnection();
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
        Page<ComputerDTO> page = computerDAO.listComputersDTO(column, order, search, begining, pageSize);
        DBManager.closeConnection();
        return page;
    }

    public int countListResult(String search) {
        int c = computerDAO.countSearchResult(search);
        DBManager.closeConnection();
        return c;
    }

    /**
     * Return the list of all computers.
     * @return The list of all computers
     * @throws DAOException In case of DAO problem
     */
    public List<Computer> listAllComputers() throws DAOException {
        List<Computer> list = computerDAO.findAll();
        DBManager.closeConnection();
        return list;
    }

    /**
     * Update the computer in the DB.
     * @param comp The computer to update
     * @return The computer
     * @throws DAOException In case of DAO problem
     */
    public Computer update(Computer comp) throws DAOException {
        Computer computer = computerDAO.update(comp);
        DBManager.closeConnection();
        return computer;
    }

    /**
     * Create the computer in the DB.
     * @param computer The computer to put in the DB
     * @return The computer
     * @throws DAOException In case of DAO problem
     */
    public Computer createComputer(Computer computer) throws DAOException {
        Computer c = computerDAO.create(computer);
        DBManager.closeConnection();
        return c;
    }

    /**
     * Delete the computer from the DB.
     * @param comp The computer to delete from the DB
     * @throws DAOException In case of DAO problem
     */
    public void delete(Computer comp) throws DAOException {
        computerDAO.delete(comp.getId());
        DBManager.closeConnection();
    }

    /**
     * Delete the computer from the DB.
     * @param id The id of the computer to delete from the DB
     * @throws DAOException In case of DAO problem
     */
    public void delete(Long id) throws DAOException {
        computerDAO.delete(id);
        DBManager.closeConnection();
    }

    public void deleteComputerList(Long[] idList) {
        computerDAO.deleteComputerList(idList);
        DBManager.closeConnection();
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