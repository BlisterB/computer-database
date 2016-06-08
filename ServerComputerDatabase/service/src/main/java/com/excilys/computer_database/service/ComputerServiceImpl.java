package com.excilys.computer_database.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computer_database.binding.ComputerDTOMapper;
import com.excilys.computer_database.core.dto.ComputerDTO;
import com.excilys.computer_database.core.entity.Company;
import com.excilys.computer_database.core.entity.Computer;
import com.excilys.computer_database.core.page.SimplePage;
import com.excilys.computer_database.persistence.DAOException;
import com.excilys.computer_database.persistence.dao.ComputerDAO;

@Service
@Transactional
public class ComputerServiceImpl implements ComputerService {

	@Autowired
	private ComputerDAO computerDAO;
	@Autowired
	private ComputerDTOMapper computerDTOMapper;

	public Computer getComputerById(Long id) {
		return computerDAO.findOne(id);
	}

	public SimplePage<Computer> listComputers(COLUMN column, Direction direction, String search, int page, int size) {
		PageRequest r;
		if (column == null) {
			r = new PageRequest(page, size);
		} else {
			r = new PageRequest(page, size, direction, getColumn(column));
		}

		Page<Computer> p;
		if (search == null || search.isEmpty()) {
			p = computerDAO.findAll(r);
		} else {
			p = computerDAO.findByNameOrCompany_Name(search, search, r);
		}
		return new SimplePage<>(p.getContent(), page, size, p.getNumberOfElements(), p.getTotalPages());
	}

	public SimplePage<ComputerDTO> listComputersDTO(COLUMN column, Direction direction, String search, int page,
			int size) {
		SimplePage<Computer> computers = listComputers(column, direction, search, page, size);
		List<ComputerDTO> list = new LinkedList<ComputerDTO>();

		for (Computer c : computers.getList()) {
			list.add(computerDTOMapper.unmap(c));
		}

		return new SimplePage<>(list, computers.getPageNumber(), computers.getSize(), computers.getElementTotalCount(),
				computers.getPageNumber());
	}

	public String getColumn(COLUMN col) {
		switch (col) {
		case COMPUTER_NAME:
			return "name";
		case INTRODUCED:
			return "introduced";
		case DISCONTINUED:
			return "discontinued";
		case COMPANY_NAME:
			return "company.name";
		default:
			return "name";
		}
	}

	public Iterable<Computer> listAllComputers() {
		return computerDAO.findAll();
	}

	public Computer update(Computer comp) {
		// Find the computer
		Computer c = this.getComputerById(comp.getId());

		if (c == null) {
			throw new DAOException("No computer for id " + comp.getId());
		}

		// Update its attributs
		c.setName(comp.getName());
		c.setIntroduced(comp.getIntroduced());
		c.setDiscontinued(comp.getDiscontinued());
		c.setCompany(comp.getCompany());

		return comp;
	}

	@Transactional(rollbackFor = DAOException.class)
	public Computer update(ComputerDTO comp) {
		return update(computerDTOMapper.map(comp));
	}

	public Computer createComputer(Computer computer) {
		return computerDAO.save(computer);
	}

	public Computer createComputer(ComputerDTO comp) {
		Computer c = new Computer.ComputerBuilder(comp.getName()).introduced(comp.getIntroduced())
				.discontinued(comp.getDiscontinued())
				.company((comp.getCompanyId() != null) ? new Company(comp.getCompanyId(), comp.getCompanyName()) : null)
				.build();
		return createComputer(c);
	}

	public void delete(Computer comp) {
		computerDAO.delete(comp);
	}

	public void delete(Long id) {
		computerDAO.delete(id);
	}

	public void deleteComputerList(Long[] idList) {
		// TODO : Improve the complexity (too many separated request)
		for (Long l : idList) {
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
	 * @param computerDAO
	 *            the computerDAO to set
	 */
	public void setComputerDAO(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}
}