package com.excilys.computer_database.console;

import com.excilys.computer_database.core.page.SimplePage;

public class CommandLineInterfaceView {

	/** The default constructor. */
	public CommandLineInterfaceView() {
	}

	/** Display the main prompt. */
	public void displayPrompt() {
		String prompt = "Please select a choice:\n" + "\t1) List companies\n" + "\t2) List computers\n"
				+ "\t3) Find a computer\n" + "\t4) Create a computer\n" + "\t5) Modify a computer\n"
				+ "\t6) Delete a computer\n";
		System.out.println(prompt);
	}

	/**
	 * Display a SimplePage.
	 * 
	 * @param p
	 *            The page to display.
	 */
	public void displayPage(SimplePage<?> p) {
		// Construct the String to display
		StringBuilder sb = new StringBuilder();
		sb.append("(").append(p.getPageNumber()).append("/").append(p.getPageTotalCount() - 1).append(") :\n");
		for (Object o : p.getList()) {
			sb.append(o.toString()).append("\n");
		}

		// Display
		System.out.println(sb);
	}

	/**
	 * Display the computer's details.
	 * 
	 * @param comp
	 *            The computer to display
	 */
	public void showComputerDetail(Object comp) {
		System.out.println(comp);
	}
}
