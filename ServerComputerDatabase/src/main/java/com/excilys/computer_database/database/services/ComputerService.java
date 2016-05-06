package com.excilys.computer_database.database.services;

import java.util.List;

import com.excilys.computer_database.database.DBManager;
import com.excilys.computer_database.database.dao.ComputerDAO;
import com.excilys.computer_database.database.dao.DAOException;
import com.excilys.computer_database.database.dtos.ComputerDTO;
import com.excilys.computer_database.entity.Computer;
import com.excilys.computer_database.ui.Page;

public class ComputerService {
    public static enum COLUMN   { COMPUTER_NAME, INTRODUCED, DISCONTINUED, COMPANY_NAME };
    public static enum ORDER    { ASC, DESC }

    private ComputerDAO dao;

    /** Constructor. */
    public ComputerService() {
        dao = ComputerDAO.getInstance();
    }

    /**
     * Return the computer of id id.
     * @param id The id
     * @return The computer with the id field to "id"
     * @throws DAOException In case of DAO problem
     */
    public Computer getComputerById(Long id) throws DAOException {
        Computer computer = dao.find(id);
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
    public Page<ComputerDTO> listComputersDTO(COLUMN column, ORDER order, String search, int begining, int pageSize) throws DAOException {
        Page<ComputerDTO> page = dao.listComputersDTO(column, order, search, begining, pageSize);
        DBManager.closeConnection();
        return page;
    }

    public int countListResult(String search){
        int c = dao.countSearchResult(search);
        DBManager.closeConnection();
        return c;
    }

    /**
     * Return the list of all computers.
     * @return The list of all computers
     * @throws DAOException In case of DAO problem
     */
    public List<Computer> listAllComputers() throws DAOException {
        List<Computer> list = dao.findAll();
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
        Computer computer = dao.update(comp);
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
        Computer c= dao.create(computer);
        DBManager.closeConnection();
        return c;
    }

    /**
     * Delete the computer from the DB.
     * @param comp The computer to delete from the DB
     * @throws DAOException In case of DAO problem
     */
    public void delete(Computer comp) throws DAOException {
        dao.delete(comp.getId());
        DBManager.closeConnection();
    }

    /**
     * Delete the computer from the DB.
     * @param id The id of the computer to delete from the DB
     * @throws DAOException In case of DAO problem
     */
    public void delete(Long id) throws DAOException {
        dao.delete(id);
        DBManager.closeConnection();
    }

    public void deleteComputerList(Long[] idList){
        dao.deleteComputerList(idList);
        DBManager.closeConnection();
    }
}