package com.excilys.computer_database.ui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.excilys.computer_database.database.dao.CompanyDAO;
import com.excilys.computer_database.database.dao.DAOException;
import com.excilys.computer_database.database.services.CompaniesService;
import com.excilys.computer_database.database.services.ComputerService;
import com.excilys.computer_database.entity.Company;
import com.excilys.computer_database.entity.Computer;

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
            case 7:
                listCompaniesByPage();
                break;
            case 8:
                listComputerByPage();
                break;
            default:
                break;
            }
        }
    }

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

    private void listComputerByPage() {
        try {
            int begining = 0, nbPerPage = 20;
            boolean continu = true;

            while (continu) {
                Page<Computer> page = computerServ.listSomeComputers(begining, nbPerPage);

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

    private void listAllCompanies() {
        try {
            List<Company> l = companiesService.listAllCompanies();
            view.displayCompanies(l);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    private void listAllComputers() {
        try {
            List<Computer> l = computerServ.listAllComputers();
            view.displayComputers(l);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

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

    public void createAComputer() {
        try {
            Computer computer = askComputerInformation();

            Computer comp = computerServ.createComputer(computer);

            // Show the computer information (to show the new id)
            System.out.println("Computer : ");
            view.showComputerDetail(comp);
        } catch (DAOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateComputer() {
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
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public Computer askComputerInformation() throws DAOException {
        // Name
        System.out.println("Nom :");
        String name = askString();

        // Dates
        DateFormat formatter = new SimpleDateFormat("yyyy MM dd");

        // Introduced
        System.out.println("Date introduced (yyyy MM dd) :");
        String stringIntroduced = askString();

        LocalDateTime introduced = null;
        if (!stringIntroduced.isEmpty()) {
            try {
                Date dateIntroduced = formatter.parse(stringIntroduced);
                introduced = LocalDateTime.ofInstant(dateIntroduced.toInstant(), ZoneId.systemDefault());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Discontinued
        System.out.println("Date discontinued (yyyy MM dd) :");
        String stringDiscontinued = askString();

        LocalDateTime discontinued = null;
        if (!stringDiscontinued.isEmpty()) {
            try {
                Date dateDiscontinued = formatter.parse(stringDiscontinued);
                discontinued = LocalDateTime.ofInstant(dateDiscontinued.toInstant(), ZoneId.systemDefault());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Company id
        System.out.println("Company id : ");
        Long companyId = askLong();

        // Création de l'objet correspondant
        Company company = CompanyDAO.getInstance().find(companyId);
        return new Computer.ComputerBuilder(name).introduced(introduced).discontinued(discontinued).company(company)
                .build();
    }

    public void deleteComputer() {
        System.out.println("Quel computer effacer ?");
        Long id = askLong();

        try {
            computerServ.delete(id);
        } catch (DAOException e) {
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
