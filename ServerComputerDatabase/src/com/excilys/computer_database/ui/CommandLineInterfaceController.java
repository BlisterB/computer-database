package com.excilys.computer_database.ui;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.excilys.computer_database.database.services.CompaniesService;
import com.excilys.computer_database.database.services.ComputerService;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;

/**
 * The Command Line Interface's controller, initialize an instance et use
 * start() to launch the Command Line Interface
 */
public class CommandLineInterfaceController {
	private CommandLineInterfaceView view;
	private Scanner sc = new Scanner(System.in);
	private ComputerService computerServ = new ComputerService();
	private CompaniesService companiesService = new CompaniesService();

	public CommandLineInterfaceController() {
		this.view = new CommandLineInterfaceView(this);
	}

	/** Start the Command Line Interface */
	public void start() {
		while (true) {
			// Display the prompt
			view.displayPrompt();

			// Ask the command to execute
			switch (askInt()) {
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
				break;
			case 5:
				updateComputer();
				break;
			case 6:
				deleteComputer();
				break;
			default:
				break;
			}
		}
	}

	private void listAllCompanies() {
		try {
			List<Company> l = companiesService.listAllCompanies();
			view.displayCompanies(l);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void listAllComputers() {
		try {
			List<Computer> l = computerServ.listAllComputers();
			view.displayComputers(l);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void showComputerDetail() {
		System.out.println("Entrez un id : ");
		long id = askLong();

		try {
			Computer computer = computerServ.getComputerById(id);
			view.showComputerDetail(computer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createAComputer() {
		Computer computer = askComputerInformation();
		
		try {
			Computer comp = computerServ.createComputer(computer);
			
			// Show the computer information (to show the new id)
			System.out.println("Computer : ");
			view.showComputerDetail(comp);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void updateComputer() {
		System.out.println("Quel computer updater ?");
		Long id = askLong();

		try {
			Computer comp = computerServ.getComputerById(id);
			System.out.println("Computer à mettre à jour : " + comp);

			Computer newComp = askComputerInformation();
			newComp.setId(comp.getId());
			computerServ.update(newComp);
			
			System.out.println("Computer mis à jour : ");
			view.showComputerDetail(comp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Computer askComputerInformation() {
		// Name
		System.out.println("Nom :");
		String name = askString();

		// Dates
		DateFormat formatter = new SimpleDateFormat("yyyy MM dd");

		// Introduced
		System.out.println("Date introduced (yyyy MM dd) :");
		String stringIntroduced = askString();

		Timestamp introduced = null;
		if (!stringIntroduced.isEmpty()) {
			try {
				Date dateIntroduced = formatter.parse(stringIntroduced);
				introduced = new Timestamp(dateIntroduced.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		// Discontinued
		System.out.println("Date discontinued (yyyy MM dd) :");
		String stringDiscontinued = askString();

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
		Long company_id = askLong();

		// Création de l'objet correspondant
		return new Computer(name, introduced, discontinued, company_id);
	}

	public void deleteComputer() {
		System.out.println("Quel computer effacer ?");
		Long id = askLong();

		try {
			computerServ.delete(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int askInt() {
		int l = sc.nextInt();
		sc.nextLine();

		return l;
	}

	public Long askLong() {
		Long l = sc.nextLong();
		sc.nextLine();

		return l;
	}

	public String askString() {
		return sc.nextLine();
	}

	public static void main(String[] arg) {
		CommandLineInterfaceController controller = new CommandLineInterfaceController();
		controller.start();
	}
}
