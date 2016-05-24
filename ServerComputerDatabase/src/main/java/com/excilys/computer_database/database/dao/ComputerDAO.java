package com.excilys.computer_database.database.dao;

import java.util.List;

import com.excilys.computer_database.database.dtos.ComputerDTO;
import com.excilys.computer_database.database.services.ComputerService.COLUMN;
import com.excilys.computer_database.database.services.ComputerService.ORDER;
import com.excilys.computer_database.entity.Computer;
import com.excilys.computer_database.ui.Page;

public interface ComputerDAO {
    public Computer create(Computer comp) throws DAOException;

    public int countSearchResult(String search);

    public Page<ComputerDTO> listComputersDTO(COLUMN column, ORDER order, String search, int begining, int pageSize)
            throws DAOException;
    public Computer find(long id) throws DAOException;
    public List<Computer> findAll() throws DAOException;

    public Computer update(Computer comp) throws DAOException;

    public void delete(Long id) throws DAOException;
    public void deleteComputerList(Long[] t) throws DAOException;
    public void deleteByCompanyID(Long companyId) throws DAOException;
}
