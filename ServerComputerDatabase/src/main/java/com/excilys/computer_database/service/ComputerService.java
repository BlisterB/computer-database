package com.excilys.computer_database.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computer_database.database.dtos.ComputerDTO;
import com.excilys.computer_database.entity.Company;
import com.excilys.computer_database.entity.Computer;
import com.excilys.computer_database.mapper.ComputerDTOMapper;
import com.excilys.computer_database.repository.ComputerDAO;
import com.excilys.computer_database.repository.DAOException;


@Service
@Transactional
public class ComputerService {
    @Autowired
    private ComputerDAO computerDAO;
    @Autowired
    private ComputerDTOMapper computerDTOMapper;

    public static enum COLUMN {
        COMPUTER_NAME, INTRODUCED, DISCONTINUED, COMPANY_NAME
    };

    /**
     * Return the computer of id id.
     * @param id The id
     * @return The computer with the id field to "id"
     * @throws DAOException In case of DAO problem
     */
    public Computer getComputerById(Long id) throws DAOException {
        return computerDAO.findOne(id);
    }

    /**
     * @param begining
     * @param nbPerPage
     * @param orderBy "id", "name", "introduced", "discontinued" or "company"
     * @return
     * @throws DAOException
     */
    public Page<ComputerDTO> listComputersDTO(COLUMN column, Direction direction, String search, int page, int size)
            throws DAOException {
        // TODO : adapter la recherche
        Page<Computer> computers;
        PageRequest r;
        if(column == null) {
            r = new PageRequest(page, size);
        } else {
            r = new PageRequest(page, size, direction, getColumn(column));
        }
        computers = computerDAO.findAll(r);


        List<ComputerDTO> list = new LinkedList<ComputerDTO>();

        for(Computer c : computers.getContent()) {
            list.add(computerDTOMapper.unmap(c));
        }
        return new PageImpl<>(list);
    }

    public String getColumn(COLUMN col){
        switch (col) {
        case COMPUTER_NAME :
            return "name";
        case INTRODUCED :
            return "introduced";
        case DISCONTINUED :
            return "discontinued";
        case COMPANY_NAME :
            return "company.name";
        default:
            return "name";
        }
    }

    public int countListResult(){
        return (int) computerDAO.count();
    }

    public int countListResult(String search) {
        // TODO : adapter la recherche
        if(search == null) {
            return countListResult();
        }
        return -1;
    }

    /**
     * Return the list of all computers.
     * @return The list of all computers
     * @throws DAOException In case of DAO problem
     */
    public Iterable<Computer> listAllComputers() throws DAOException {
        return computerDAO.findAll();
    }

    /**
     * Update the computer in the DB.
     * @param comp The computer to update
     * @return The computer
     * @throws DAOException In case of DAO problem
     */
    public Computer update(Computer comp) throws DAOException {
        // Find the computer
        Computer c = this.getComputerById(comp.getId());

        if (c == null) {
            throw new DAOException("No computer for id " + comp.getId());
        }

        // Update its attributs
        c.setName(comp.getName());
        c.setIntroduced(comp.getIntroduced());
        c.setDiscontinued(comp.getDiscontinued());
        if(c.getCompany() == null) {
            c.setCompany(new Company());
        }
        c.getCompany().setId(comp.getCompany().getId());
        c.getCompany().setName(comp.getCompany().getName());

        // Save the changes
        computerDAO.save(c);
        return c;
    }

    @Transactional(rollbackFor=DAOException.class)
    public Computer update(ComputerDTO comp) throws DAOException {
        return update(computerDTOMapper.map(comp));
    }

    /**
     * Create the computer in the DB.
     * @param computer The computer to put in the DB
     * @return The computer
     * @throws DAOException In case of DAO problem
     */
    public Computer createComputer(Computer computer) throws DAOException {
        return computerDAO.save(computer);
    }

    public Computer createComputer(ComputerDTO comp) throws DAOException {
        Computer c = new Computer.ComputerBuilder(comp.getName())
                .introduced(comp.getIntroduced())
                .discontinued(comp.getDiscontinued())
                .company(new Company(comp.getCompanyId(), comp.getCompanyName()))
                .build();
        return createComputer(c);
    }

    /**
     * Delete the computer from the DB.
     * @param comp The computer to delete from the DB
     * @throws DAOException In case of DAO problem
     */
    public void delete(Computer comp) throws DAOException {
        computerDAO.delete(comp);
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
        // TODO : Improve the complexity (too many separated request)
        for(Long l : idList) {
            computerDAO.delete(l);
        }
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