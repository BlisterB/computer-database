package com.excilys.computer_database.database.services;

import java.util.LinkedList;
import java.util.List;

import com.excilys.computer_database.database.dao.ComputerDAO;
import com.excilys.computer_database.database.dao.DAOException;
import com.excilys.computer_database.database.dao.NotFoundException;
import com.excilys.computer_database.database.dtos.ComputerDTO;
import com.excilys.computer_database.database.dtos.ComputerDTOMapper;
import com.excilys.computer_database.entity.Computer;
import com.excilys.computer_database.ui.Page;

public class ComputerService {
    private ComputerDAO dao;

    /** Constructor. */
    public ComputerService() {
        dao = ComputerDAO.getInstance();
    }

    /** Return the computer of id id.
     * @param id The id
     * @return The computer with the id field to "id"
     * @throws DAOException In case of DAO problem
     */
    public Computer getComputerById(Long id) throws DAOException, NotFoundException {
        return dao.find(id);
    }

    /**
     * Return a page containing a number of "nbPerPage" Computer from "begining" in the DB.
     * @param begining Begining in the DB
     * @param nbPerPage Max number of element
     * @return A page containing a number of "nbPerPage" Computer from "begining" in the DB
     * @throws DAOException In case of DAO problem
     */
    public Page<Computer> listSomeComputers(int begining, int nbPerPage) throws DAOException {
        int pageNumber = begining / nbPerPage;
        List<Computer> list = dao.findSome(begining, nbPerPage);

        return new Page<Computer>(list, pageNumber, nbPerPage);
    }

    /** Return the list of all computers.
     * @return The list of all computers
     * @throws DAOException In case of DAO problem
     */
    public List<Computer> listAllComputers() throws DAOException {
        return dao.findAll();
    }

    /**
     * Update the computer in the DB.
     * @param comp The computer to update
     * @return The computer
     * @throws DAOException In case of DAO problem
     */
    public Computer update(Computer comp) throws DAOException {
        return dao.update(comp);
    }

    /**
     * Create the computer in the DB.
     * @param computer The computer to put in the DB
     * @return The computer
     * @throws DAOException In case of DAO problem
     */
    public Computer createComputer(Computer computer) throws DAOException {
        return dao.create(computer);
    }

    /**
     * Delete the computer from the DB.
     * @param comp The computer to delete from the DB
     * @throws DAOException In case of DAO problem
     */
    public void delete(Computer comp) throws DAOException {
        dao.delete(comp.getId());
    }

    /**
     * Delete the computer from the DB.
     * @param id The id of the computer to delete from the DB
     * @throws DAOException In case of DAO problem
     */
    public void delete(Long id) throws DAOException {
        dao.delete(id);
    }

    public int getComputerCount() throws DAOException {
        return dao.getCount();
    }

    public Page<ComputerDTO> listSomeComputersDTO(int begining, int nbPerPage) throws DAOException {
        // TODO : Implémenter ça proprement (pour le moment complexité 2*n)
        int pageNumber = begining / nbPerPage;
        List<Computer> list = dao.findSome(begining, nbPerPage);
        List<ComputerDTO> listDTO = new LinkedList<ComputerDTO>();
        ComputerDTOMapper mapper = new ComputerDTOMapper();
        for(Computer c : list){
            listDTO.add(mapper.unmap(c));
        }

        return new Page<ComputerDTO>(listDTO, pageNumber, nbPerPage);
    }
    
    public Page<ComputerDTO> searchByName(String name, int begining, int nbPerPage) throws DAOException{
    	return dao.searchByName(name, begining, nbPerPage);
    }
}