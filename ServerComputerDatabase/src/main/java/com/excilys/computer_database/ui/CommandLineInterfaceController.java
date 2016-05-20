package com.excilys.computer_database.ui;

import java.util.List;
import java.util.Scanner;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.excilys.computer_database.database.dao.DAOException;
import com.excilys.computer_database.database.dao.NotFoundException;
import com.excilys.computer_database.database.dtos.ComputerDTO;
import com.excilys.computer_database.database.services.CompanyService;
import com.excilys.computer_database.database.services.ComputerService;
import com.excilys.computer_database.entity.Company;
import com.excilys.computer_database.entity.Computer;

/**
 * The Command Line Interface's controller, initialize an instance et use
 * start() to launch the Command Line Interface.
 */
public class CommandLineInterfaceController {
    private CommandLineInterfaceView view;
    private Scanner sc = new Scanner(System.in);
    private ComputerService computerServ = new ComputerService();
    private CompanyService companiesService = new CompanyService();

    /** The constructor. */
    public CommandLineInterfaceController() {
        this.view = new CommandLineInterfaceView(this);
    }

    /** Start the Command Line Interface. */
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
            case 7:
                listCompaniesByPage();
                break;
            case 8:
                listComputerByPage();
                break;
            case 9:
                deleteCompany();
                break;
            default:
                break;
            }
        }
    }

    /** Launch the listing companies by page process. */
    private void listCompaniesByPage() {
        try {
            int begining = 0, nbPerPage = 20;
            boolean continu = true;

            while (continu) {
                Page<Company> page = companiesService.listSomeCompanies(begining, nbPerPage);

                view.showPage(page);

                String choice = askString().trim();
                if (choice.equals("n")) {
                    begining += nbPerPage;
                } else if (choice.equals("p")) {
                    begining -= nbPerPage;
                } else {
                    continu = false;
                }
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    /** Launch the listing computers by page process. */
    private void listComputerByPage() {
        try {
            int begining = 0, pageSize = 20;
            boolean continu = true;

            while (continu) {
                Page<ComputerDTO> page = computerServ.listComputersDTO(null, null, null, begining, pageSize);

                view.showPage(page);

                String choice = askString().trim();
                if (choice.equals("n")) {
                    begining += pageSize;
                } else if (choice.equals("p")) {
                    begining -= pageSize;
                } else {
                    continu = false;
                }
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    /** Fetch the companies list and ask to the view to display them. */
    private void listAllCompanies() {
        try {
            List<Company> l = companiesService.listAllCompanies();
            view.displayCompanies(l);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    /** Fetch the computers list and ask to the view to display them. */
    private void listAllComputers() {
        try {
            List<Computer> l = computerServ.listAllComputers();
            view.displayComputers(l);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    /** Fetch a computer's information and display them. */
    public void showComputerDetail() {
        System.out.println("Entrez un id : ");
        long id = askLong();

        try {
            Computer computer = computerServ.getComputerById(id);
            view.showComputerDetail(computer);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    /** Launch the "create computer" process. */
    private void createAComputer() {
        try {
            Computer computer = askComputerInformation();

            Computer comp = computerServ.createComputer(computer);

            // Show the computer information (to show the new id)
            System.out.println("Computer : ");
            view.showComputerDetail(comp);
        } catch (DAOException | NotFoundException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /** Launch the "update the computer" process. */
    private void updateComputer() {
        System.out.println("Quel computer updater ?");
        Long id = askLong();

        try {
            Computer comp = computerServ.getComputerById(id);
            System.out.println("Computer à mettre à jour :\n" + comp);

            Computer newComp = askComputerInformation();
            newComp.setId(comp.getId());
            computerServ.update(newComp);

            System.out.println("Computer mis à jour : ");
            view.showComputerDetail(newComp);
        } catch (DAOException | NotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Launch the "display computer's informations" process.
     * @return Return the computer
     * @throws DAOException If an error occurs with the DAO
     */
    private Computer askComputerInformation() throws DAOException, NotFoundException {
        // Name
        System.out.println("Nom :");
        String name = askString();

        // Dates
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy MM dd");

        // Introduced
        System.out.println("Date introduced (yyyy MM dd) :");
        String stringIntroduced = askString();

        LocalDate introduced = null;
        if (!stringIntroduced.isEmpty()) {
            try {
                introduced = LocalDate.parse(stringIntroduced, formatter);
            } catch (Exception e) {
                System.out.println("Bad entry, introduced date set to null");
            }
        }

        // Discontinued
        System.out.println("Date discontinued (yyyy MM dd) :");
        String stringDiscontinued = askString();

        LocalDate discontinued = null;
        if (!stringDiscontinued.isEmpty()) {
            try {
                discontinued = LocalDate.parse(stringDiscontinued, formatter);
            } catch (Exception e) {
                System.out.println("Bad entry, discontinued date set to null");
            }
        }

        // Company id
        System.out.println("Company id : ");
        Long companyId = askLong();

        // Création de l'objet correspondant
        Company company = companiesService.find(companyId);
        return new Computer.ComputerBuilder(name).introduced(introduced).discontinued(discontinued).company(company)
                .build();
    }

    /** Launch a "delete a computer" process. */
    private void deleteComputer() {
        System.out.println("Quel computer effacer ?");
        Long id = askLong();

        try {
            computerServ.delete(id);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    /** Launch the "delete a company" process. */
    private void deleteCompany() {
        System.out.println("Quelle company effacer ?");
        Long id = askLong();

        try {
            companiesService.delete(id);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetch an int in System.in and delete the rest of the line.
     * @return The entered Int
     */
    private int askInt() {
        int l = sc.nextInt();
        sc.nextLine();

        return l;
    }

    /**
     * Fetch a long in System.in and delete the rest of the line.
     * @return The entered Long
     */
    private Long askLong() {
        Long l = sc.nextLong();
        sc.nextLine();

        return l;
    }

    /**
     * Fetch an int in System.in.
     * @return The entered String
     */
    private String askString() {
        return sc.nextLine();
    }

    /**
     * The main launching the CLI.
     * @param arg The arguments
     */
    public static void main(String[] arg) {
        CommandLineInterfaceController controller = new CommandLineInterfaceController();
        controller.start();
    }
}
