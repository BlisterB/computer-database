package com.excilys.computer_database.ui;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.excilys.computer_database.database.dao.CompanyDAO;
import com.excilys.computer_database.database.dao.ComputerDAO;
import com.excilys.computer_database.database.services.CompaniesService;
import com.excilys.computer_database.database.services.ComputerService;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;

public class CLI_UI {
	private Scanner sc = new Scanner(System.in);
	private ComputerService computerServ = new ComputerService();
	private CompaniesService companiesService = new CompaniesService();

	public void displayPrompt() {
		String prompt = "Please select a choice:\n" + "\t1) List all companies\n" + "\t2) List all computers\n"
				+ "\t3) Show computer details\n" + "\t4) Create a computer\n" + "\t5) Modify a computer\n"
				+ "\t6) Delete a computer\n";
		System.out.println(prompt);
	}

	public void listAllCompanies() {
		try {
			List<Company> l = companiesService.listAllCompanies();

			StringBuilder sb = new StringBuilder();
			for (Company company : l) {
				sb.append(company.toString()).append("\n");
			}
			System.out.println(sb);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void listAllComputers() {
		try {
			List<Computer> l = computerServ.listAllComputers();

			StringBuilder sb = new StringBuilder();
			for (Computer computer : l) {
				sb.append(computer.toString()).append("\n");
			}
			System.out.println(sb);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void showComputerDetail() {
		System.out.println("Entrez un id : ");
		long id = sc.nextLong();

		try {
			Computer computer = computerServ.getComputerById(id);
			System.out.println(computer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createAComputer() {
		Computer computer = askComputerInformation();
		try {
			computerServ.createComputer(computer);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public Computer askComputerInformation() {
		if (sc.hasNextLine())
			sc.nextLine();

		// Name
		System.out.println("Nom :");
		String name = sc.nextLine();

		// Dates
		DateFormat formatter = new SimpleDateFormat("yyyy MM dd");
		System.out.println("Date introduced (yyyy MM dd) :");
		String stringIntroduced = sc.nextLine().trim();

		Timestamp introduced = null;
		if (!stringIntroduced.isEmpty()) {
			try {
				Date dateIntroduced = formatter.parse(stringIntroduced);
				introduced = new Timestamp(dateIntroduced.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Date discontinued (yyyy MM dd) :");
		String stringDiscontinued = sc.nextLine().trim();

		Timestamp discontinued = null;
		if (!stringDiscontinued.isEmpty()) {
			try {
				Date dateDiscontinued = formatter.parse(stringDiscontinued);
				discontinued = new Timestamp(dateDiscontinued.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		// Company id
		System.out.println("Company id : ");
		Long company_id = sc.nextLong();

		// Création de l'objet correspondant
		return new Computer(name, introduced, discontinued, company_id);
	}

	public void deleteComputer(){
		System.out.println("Quel computer effacer ?");
		long id = sc.nextLong();

		try {
			computerServ.delete(id);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void runCLI() {
		while (true) {
			displayPrompt();

			switch (sc.nextInt()) {
			case 1:
				listAllCompanies();
				break;
			case 2:
				listAllComputers();
				break;
			case 3:
				showComputerDetail();
				break;
			case 4:
				createAComputer();
			case 5:

			case 6:
				createAComputer();
			default:
				break;
			}
		}
	}

	public static void main(String[] arg) {
		CLI_UI cli = new CLI_UI();
		cli.runCLI();
	}
}
