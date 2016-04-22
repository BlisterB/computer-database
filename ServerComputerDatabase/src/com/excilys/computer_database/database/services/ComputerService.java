package com.excilys.computer_database.database.services;

import java.util.List;

import com.excilys.computer_database.database.dao.ComputerDAO;
import com.excilys.computer_database.database.dao.DAOException;
import com.excilys.computer_database.entity.Computer;
import com.excilys.computer_database.ui.Page;

public class ComputerService {
	private ComputerDAO dao;

	public ComputerService() {
		dao = ComputerDAO.getInstance();
	}

	public Computer getComputerById(Long id) throws DAOException {
		return dao.find(id);
	}

	public Page<Computer> listSomeComputers(int begining, int nbPerPage) throws DAOException {
		boolean first = (begining == 0);
		List<Computer> list = dao.findSome(begining, nbPerPage);
		boolean last = list.size() <= nbPerPage;

		return new Page<Computer>(list, first, last);
	}

	public List<Computer> listAllComputers() throws DAOException {
		return dao.findAll();
	}

	public Computer update(Computer comp) throws DAOException {
		return dao.update(comp);
	}

	public Computer createComputer(Computer computer) throws DAOException {
		return dao.create(computer);
	}

	public void delete(Computer comp) throws DAOException {
		dao.delete(comp.getId());
	}

	public void delete(Long id) throws DAOException {
		dao.delete(id);
	}
}
