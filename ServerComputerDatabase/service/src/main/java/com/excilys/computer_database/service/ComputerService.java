package com.excilys.computer_database.service;

import org.springframework.data.domain.Sort.Direction;

import com.excilys.computer_database.core.dto.ComputerDTO;
import com.excilys.computer_database.core.entity.Computer;
import com.excilys.computer_database.core.page.SimplePage;

public interface ComputerService {
	public static enum COLUMN {
		COMPUTER_NAME, INTRODUCED, DISCONTINUED, COMPANY_NAME
	};
	
	/**
	 * @param id
	 *            The id
	 * @return The corresponding id
	 */
	public Computer getComputerById(Long id);

	public SimplePage<Computer> listComputers(COLUMN column, Direction direction, String search, int page, int size);

	/**
	 * @param begining
	 * @param nbPerPage
	 * @param orderBy
	 *            "id", "name", "introduced", "discontinued" or "company"
	 * @return An array, the first case is the List containing the result, the
	 *         second is the Integer representing the total number of result
	 */
	public SimplePage<ComputerDTO> listComputersDTO(COLUMN column, Direction direction, String search, int page, int size);

	public String getColumn(COLUMN col);

	/**
	 * Return the list of all computers.
	 * 
	 * @return The list of all computers
	 */
	public Iterable<Computer> listAllComputers();

	/**
	 * Update the computer in the DB.
	 * 
	 * @param comp
	 *            The computer to update
	 * @return The computer
	 */
	public Computer update(Computer comp);

	public Computer update(ComputerDTO comp);

	/**
	 * Create the computer in the DB.
	 * 
	 * @param computer
	 *            The computer to put in the DB
	 * @return The computer
	 */
	public Computer createComputer(Computer computer);

	public Computer createComputer(ComputerDTO comp);

	/**
	 * Delete the computer from the DB.
	 * 
	 * @param comp
	 *            The computer to delete from the DB
	 */
	public void delete(Computer comp);

	/**
	 * Delete the computer from the DB.
	 * 
	 * @param id
	 *            The id of the computer to delete from the DB
	 */
	public void delete(Long id);

	public void deleteComputerList(Long[] idList);
}
