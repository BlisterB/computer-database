package com.excilys.computer_database.database.services;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.excilys.computer_database.database.dao.ComputerDAO;
import com.excilys.computer_database.entity.Computer;
import com.excilys.computer_database.ui.Page;

public class ComputerService {
	private ComputerDAO dao;

	public ComputerService() {
		dao = ComputerDAO.getInstance();
	}
	
	public Computer getComputerById(Long id) throws SQLException {
		return dao.find(id);
	}
	
	public Page<Computer> listSomeComputers(int begining, int nbPerPage) throws SQLException{
		boolean first = (begining == 0);
		List<Computer> list = dao.findSome(begining, nbPerPage);
		boolean last = list.size() <= nbPerPage;
		
		return new Page<Computer>(list, first, last);
	}
	
	public List<Computer> listAllComputers() throws SQLException {
		return dao.findAll();
	}

	public Computer update(Computer comp) throws SQLException {
		return dao.update(comp);
	}

	public Computer createComputer(Computer computer)
			throws SQLException {
		// Introduced date greater than discontinued date?
		if(computer.getIntroduced().after(computer.getDiscontinued())){
			throw new SQLException("The introduced date is greater than the discontinued date");
		}
		
		return dao.create(computer);
	}

	public Computer createComputer(String name, Date introduced, Date discontinued, Long company_id)
			throws SQLException {
		Computer computer = new Computer(name, new Timestamp(introduced.getTime()), new Timestamp(discontinued.getTime()), company_id);
		return createComputer(computer);
	}
	
	public void delete(Computer comp) throws SQLException{
		dao.delete(comp.getId());
	}
	
	public void delete(Long id) throws SQLException{
		dao.delete(id);
	}
}
