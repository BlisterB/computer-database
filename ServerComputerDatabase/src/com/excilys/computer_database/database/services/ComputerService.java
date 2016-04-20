package com.excilys.computer_database.database.services;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
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

	public Computer createComputer(Computer computer)
			throws SQLException {
		// TODO : Vérifier que la date de fin est apres la date de début
		return dao.create(computer);
	}
	
	public Computer createComputer(String name, Date introduced, Date discontinued, Long company_id)
			throws SQLException {
		Computer computer = new Computer(name, new Timestamp(introduced.getTime()), new Timestamp(discontinued.getTime()), company_id);
		return createComputer(computer);
	}
}
