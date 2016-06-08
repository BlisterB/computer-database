package com.excilys.computer_database.webapp.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computer_database.core.DateHelper;
import com.excilys.computer_database.core.entity.Company;
import com.excilys.computer_database.core.entity.Computer;
import com.excilys.computer_database.core.entity.Computer.ComputerBuilder;
import com.excilys.computer_database.core.page.SimplePage;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;

@RestController
public class RESTController {
	private static List<Integer> authorized_size = Arrays.asList(10, 20, 50);

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;

	@RequestMapping("/rest/listcompany")
	/**
	 * Catch the companies listing requests and send the result in JSON format.
	 * 
	 * @param pageNumber
	 *            The page number
	 * @param size
	 *            The size of the requested page (10, 20 or 50)
	 * @return
	 */
	public SimplePage<Company> listCompany(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(value = "size", defaultValue = "20") int size) {
		// Check request parameters
		pageNumber = checkPageNumber(pageNumber);
		size = checkPageSize(size);

		// List and send companies
		return companyService.listSomeCompanies(pageNumber, size);
	}

	/**
	 * Catch the computer listing requests and send the result in JSON format.
	 * 
	 * @param pageNumber
	 *            The page number
	 * @param size
	 *            The size of the requested page (10, 20 or 50)
	 * @return
	 */
	@RequestMapping("/rest/listcomputer")
	public SimplePage<Computer> listComputer(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(value = "size", defaultValue = "20") int size) {
		// Check request parameters
		pageNumber = checkPageNumber(pageNumber);
		size = checkPageSize(size);

		// List and send companies without search or order parameters
		return computerService.listComputers(null, null, null, pageNumber, size);
	}

	/**
	 * Catch the computer finding requests and send the result in JSON format.
	 * 
	 * @param id
	 *            The computer id to fetch
	 * @return
	 */
	@RequestMapping("/rest/findcomputer")
	public Computer findComputer(@RequestParam(value = "id", required = true) Long id) {
		return computerService.getComputerById(id);
	}

	/**
	 * Catch the computer creating requests and send the result in JSON format.
	 * 
	 * @param name
	 *            The computer name
	 * @param introduced
	 *            The computer introduced date
	 * @param discontinued
	 *            The computer discontinued date
	 * @param companyId
	 *            The computer's company id
	 * @return
	 */
	@RequestMapping("/rest/createcomputer")
	public Computer createComputer(@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "introduced", required = false) String introduced,
			@RequestParam(value = "discontinued", required = false) String discontinued,
			@RequestParam(value = "companyId", required = false) Long companyId) {
		LocalDate finalIntroduced = null;
		if (introduced != null) {
			finalIntroduced = DateHelper.isoStringToLocalDate(introduced);
		}

		LocalDate finalDiscontinued = null;
		if (discontinued != null) {
			finalDiscontinued = DateHelper.isoStringToLocalDate(discontinued);
		}

		Company company = null;
		if (companyId != null) {
			company = companyService.find(companyId);
		}

		// TODO : Passer par un validator au préalable
		return new ComputerBuilder(name).introduced(finalIntroduced).discontinued(finalDiscontinued).company(company)
				.build();
	}

	@RequestMapping("/rest/updatecomputer")
	/**
	 * Catch the computer update requests and send the result in JSON format.
	 * 
	 * @param name
	 *            The computer name
	 * @param introduced
	 *            The computer introduced date
	 * @param discontinued
	 *            The computer discontinued date
	 * @param companyId
	 *            The computer's company id
	 * @return
	 */
	public Computer updateComputer(@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "introduced", required = false) String introduced,
			@RequestParam(value = "discontinued", required = false) String discontinued,
			@RequestParam(value = "companyId", required = false) Long companyId) {
		// Formating the parameters
		LocalDate finalIntroduced = null;
		if (introduced != null) {
			finalIntroduced = DateHelper.isoStringToLocalDate(introduced);
		}

		LocalDate finalDiscontinued = null;
		if (discontinued != null) {
			finalDiscontinued = DateHelper.isoStringToLocalDate(discontinued);
		}

		Company company = null;
		if (companyId != null) {
			company = companyService.find(companyId);
		}

		// TODO : Validate the computer
		return new ComputerBuilder(name).id(id).introduced(finalIntroduced).discontinued(finalDiscontinued)
				.company(company).build();
	}

	@RequestMapping("/rest/deleteComputer")
	/**
	 * Catch the computer update requests and send the result in JSON format.
	 * 
	 * @param id
	 *            The computer id to fetch
	 * @return
	 */
	public String deleteComputer(@RequestParam(value = "id", required = true) Long id) {
		computerService.delete(id);
		return "Ok";
	}

	/**
	 * Control the pageSize parameter and return a valid value.
	 * 
	 * @param pageSize
	 *            The pageSize parameter to control
	 * @return
	 */
	private int checkPageSize(int pageSize) {
		if (!authorized_size.contains(pageSize)) {
			return authorized_size.get(0);
		}
		return pageSize;
	}

	/**
	 * Control the pageNumber parameter and return a valid value.
	 * 
	 * @param pageNumber
	 *            The pageNumber parameter to control
	 * @return
	 */
	private int checkPageNumber(int pageNumber) {
		if (pageNumber < 0) {
			return pageNumber = 0;
		}
		return pageNumber;
	}
}
