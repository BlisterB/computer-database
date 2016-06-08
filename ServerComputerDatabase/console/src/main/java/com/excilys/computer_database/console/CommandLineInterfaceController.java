package com.excilys.computer_database.console;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.core.DateHelper;
import com.excilys.computer_database.core.dto.ComputerDTO;
import com.excilys.computer_database.core.entity.Company;
import com.excilys.computer_database.core.entity.Computer;
import com.excilys.computer_database.core.page.SimplePage;
import com.excilys.computer_database.persistence.DAOException;

/**
 * The Command Line Interface's controller, initialize an instance et use
 * start() to launch the Command Line Interface.
 */
@Component
public class CommandLineInterfaceController {
	private static enum ComputerOrCompany {
		COMPUTER, COMPANY
	};

	private static final int DEFAULT_PAGE_NUMBER = 0, DEFAULT_PAGE_SIZE = 10;
	private static final String BASE_URL = "http://localhost:8080/webapp/rest";
	private static final String LIST_COMPANY_URL = BASE_URL + "/listcompany?pageNumber=%d&size=%d",
			LIST_COMPUTER_URL = BASE_URL + "/listcomputer?pageNumber=%d&size=%d",
			FIND_COMPUTER_URL = BASE_URL + "/findcomputer?id=%d",
			CREATE_COMPUTER_URL = BASE_URL + "/createcomputer?name=%s",
			UPDATE_COMPUTER_URL = BASE_URL + "/updatecomputer?id=%d&name=%s",
			DELETE_COMPUTER_URL = BASE_URL + "/deletecomputer?id=%d";

	private CommandLineInterfaceView view;
	private Scanner sc = new Scanner(System.in);
	Client client = ClientBuilder.newClient();
	private ObjectMapper mapper = new ObjectMapper();

	/** The constructor. */
	public CommandLineInterfaceController() {
		this.view = new CommandLineInterfaceView();
	}

	/** Start the Command Line Interface. */
	public void start() {
		while (true) {
			// Display the prompt
			view.displayPrompt();

			// Ask the command to execute
			try {
				switch (askInt()) {
				case 1:
					listCompanyOrComputer(ComputerOrCompany.COMPANY);
					break;
				case 2:
					listCompanyOrComputer(ComputerOrCompany.COMPUTER);
					break;
				case 3:
					findComputer();
					break;
				case 4:
					createComputer();
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Launch the listing companies by page process.
	 * 
	 * @param entityToList
	 *            The entity to list.
	 * @throws IOException
	 */
	public void listCompanyOrComputer(ComputerOrCompany entityToList) throws IOException {
		int pageNumber = DEFAULT_PAGE_NUMBER;
		int size = DEFAULT_PAGE_SIZE;

		// Page exploring process
		PAGE_EXPLORING: while (true) {
			SimplePage<?> page = sendListRequest(entityToList, pageNumber, size);
			view.displayPage(page);

			String choice = askString().trim();
			if (choice.equals("n")) {
				if (pageNumber < page.getPageTotalCount() - 1) {
					pageNumber++;
				}
			} else if (choice.equals("p")) {
				if (pageNumber > 0) {
					pageNumber--;
				}
			} else {
				break PAGE_EXPLORING;
			}
		}
	}

	/** Fetch a computer's informations and display them. */
	public void findComputer() throws IOException {
		// Ask an id to the user
		System.out.println("Entrez un id :");
		long id = askLong();

		// Fetch and display the user
		Computer computer = sendFindComputerRequest(id);
		view.showComputerDetail(computer);
	}

	/**
	 * Send the listing request to the server, and return the resulting page.
	 * 
	 * @param choice
	 * @param pageNumber
	 * @param size
	 * @return
	 * @throws IOException
	 */
	private SimplePage<Object> sendListRequest(ComputerOrCompany choice, int pageNumber, int size) throws IOException {
		String requestUrl, serverResponse;

		switch (choice) {
		default:
		case COMPANY:
			requestUrl = String.format(LIST_COMPANY_URL, pageNumber, size);
			serverResponse = client.target(requestUrl).request(MediaType.APPLICATION_JSON_VALUE).get(String.class);
			return mapper.readValue(serverResponse, new TypeReference<SimplePage<Company>>() {
			});
		case COMPUTER:
			requestUrl = String.format(LIST_COMPUTER_URL, pageNumber, size);
			serverResponse = client.target(requestUrl).request(MediaType.APPLICATION_JSON_VALUE).get(String.class);
			return mapper.readValue(serverResponse, new TypeReference<SimplePage<Computer>>() {
			});
		}

	}

	/** Send a find computer request. */
	private Computer sendFindComputerRequest(Long id) throws IOException {
		String requestUrl = String.format(FIND_COMPUTER_URL, id);
		String serverResponse = client.target(requestUrl).request(MediaType.APPLICATION_JSON_VALUE).get(String.class);
		return mapper.readValue(serverResponse, Computer.class);
	}

	/** Launch the "create computer" process. */
	private void createComputer() throws IOException {
		ComputerDTO c = askComputerInformation();

		String requestUrl = String.format(CREATE_COMPUTER_URL, c.getName());

		if (c.getIntroduced() != null) {
			requestUrl += "&introduced=" + c.getIntroduced();
		}
		if (c.getDiscontinued() != null) {
			requestUrl += "&discontinued=" + c.getDiscontinued();
		}
		if (c.getCompanyId() != null && c.getCompanyId() > 0) {
			requestUrl += "&companyId=" + c.getCompanyId();
		}
		String serverResponse = client.target(requestUrl).request(MediaType.APPLICATION_JSON_VALUE).get(String.class);

		// Show the computer information (to show the new id)
		System.out.println("Computer : ");
		Computer computer = mapper.readValue(serverResponse, Computer.class);
		view.showComputerDetail(computer);
	}

	/** Launch the "update the computer" process. */
	private void updateComputer() throws IOException {
		// Fetch the computer to update
		System.out.println("Quel computer updater ? Saisir l'id :");
		Long id = askLong();

		Computer c = sendFindComputerRequest(id);
		if (c == null) {
			System.out.println("Pas d'ordinateur pour cet id.");
			return;
		}
		System.out.println("Computer à mettre à jour :\n" + c);

		// Ask the new information to the user
		ComputerDTO newComp = askComputerInformation();
		newComp.setId(c.getId());

		// Build the update request
		String requestUrl = String.format(UPDATE_COMPUTER_URL, newComp.getId(), newComp.getName());
		if (newComp.getIntroduced() != null) {
			requestUrl += "&introduced=" + newComp.getIntroduced();
		}
		if (newComp.getDiscontinued() != null) {
			requestUrl += "&discontinued=" + newComp.getDiscontinued();
		}
		if (newComp.getCompanyId() != null && newComp.getCompanyId() > 0) {
			requestUrl += "&companyId=" + newComp.getCompanyId();
		}

		// Send the request and display the responses
		String serverResponse = client.target(requestUrl).request(MediaType.APPLICATION_JSON_VALUE).get(String.class);
		System.out.println("Computer mis à jour : ");
		Computer computer = mapper.readValue(serverResponse, Computer.class);
		view.showComputerDetail(computer);
	}

	private ComputerDTO askComputerInformation() {
		// Name
		System.out.println("Nom :");
		String name = askString();

		// Dates
		// Introduced
		System.out.println("Date introduced (yyyy-MM-dd) :");
		String stringIntroduced = askString();

		LocalDate introduced = null;
		if (!stringIntroduced.isEmpty()) {
			try {
				introduced = DateHelper.isoStringToLocalDate(stringIntroduced);
			} catch (DateTimeParseException e) {
				System.out.println("Bad entry, introduced date set to null");
			}
		}

		// Discontinued
		System.out.println("Date discontinued (yyyy-MM-dd) :");
		String stringDiscontinued = askString();

		LocalDate discontinued = null;
		if (!stringDiscontinued.isEmpty()) {
			try {
				discontinued = DateHelper.isoStringToLocalDate(stringDiscontinued);
			} catch (DateTimeParseException e) {
				System.out.println("Bad entry, discontinued date set to null");
			}
		}

		// Company id
		System.out.println("Company id : ");
		Long companyId = askLong();

		// Création de l'objet correspondant
		return new ComputerDTO(-1L, name, introduced, discontinued, companyId, null);
	}

	/** Launch a "delete a computer" process. */
	private void deleteComputer() {
		System.out.println("Quel computer effacer ?");
		Long id = askLong();

		String requestUrl = String.format(DELETE_COMPUTER_URL, id);
		String s = client.target(requestUrl).request(MediaType.TEXT_PLAIN_VALUE).get(String.class);
		System.out.println("Reponse serveur : " + s);
	}

	/**
	 * Fetch an int in System.in and delete the rest of the line.
	 * 
	 * @return The entered Int
	 */
	private int askInt() {
		int l = sc.nextInt();
		sc.nextLine();

		return l;
	}

	/**
	 * Fetch a long in System.in and delete the rest of the line.
	 * 
	 * @return The entered Long
	 */
	private Long askLong() {
		Long l = sc.nextLong();
		sc.nextLine();

		return l;
	}

	/**
	 * Fetch an int in System.in.
	 * 
	 * @return The entered String
	 */
	private String askString() {
		return sc.nextLine();
	}

	/**
	 * The main launching the CLI.
	 * 
	 * @param arg
	 *            The arguments
	 */
	public static void main(String[] arg) {
		CommandLineInterfaceController controller = new CommandLineInterfaceController();
		controller.start();
	}
}
