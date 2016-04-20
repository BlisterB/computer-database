package com.excilys.computer_database.database.services;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computer_database.database.dao.ComputerDAO;
import com.excilys.computer_database.model.Computer;

public class ComputerService {
	private ComputerDAO dao;

	public ComputerService() {
		dao = ComputerDAO.getInstance();
	}

	public List<Computer> listAllComputers() throws SQLException {
		return dao.findAll();
	}
	
	public Computer getComputerById(Long id) throws SQLException {
		return dao.find(id);
	}
}
