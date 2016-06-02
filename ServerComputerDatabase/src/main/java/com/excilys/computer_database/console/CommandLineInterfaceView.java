package com.excilys.computer_database.console;

import java.util.List;

import com.excilys.computer_database.core.entity.Company;
import com.excilys.computer_database.core.entity.Computer;

public class CommandLineInterfaceView {

    /** Constructor by default.
     *  @param c The CLI controller
     */
    public CommandLineInterfaceView(CommandLineInterfaceController c) {
    }

    /** Display the main prompt. */
    public void displayPrompt() {
        String prompt = "Please select a choice:\n" + "\t1) List all companies\n" + "\t2) List all computers\n"
                + "\t3) Show computer details\n" + "\t4) Create a computer\n" + "\t5) Modify a computer\n"
                + "\t6) Delete a computer\n"
                + "\t7) List companies (page version)\n"
                + "\t8) List computers (page version)\n"
                + "\t9) Delete a company";
        System.out.println(prompt);
    }

    /** Display a list of company.
     * @param l The list to display
     */
    public void displayCompanies(Iterable<Company> l) {
        StringBuilder sb = new StringBuilder();
        for (Company company : l) {
            sb.append(company.toString()).append("\n");
        }
        System.out.println(sb);
    }

    /** Display a list of computers.
     * @param l The list to display
     */
    public void displayComputers(Iterable<Computer> l) {
        StringBuilder sb = new StringBuilder();
        for (Computer computer : l) {
            sb.append(computer.toString()).append("\n");
        }
        System.out.println(sb);
    }

    /** Display the computer details.
     * @param comp The computer to display
     */
    public void showComputerDetail(Computer comp) {
        System.out.println(comp);
    }

    /** Display a page.
     * @param page The computer to display
     */
    public void showPage(List<?> page) {
        for(Object o : page) {
            System.out.println(o);
        }
    }
}
