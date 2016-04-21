package com.excilys.computer_database.ui;

import java.util.List;

import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;

public class CommandLineInterfaceView {
	private CommandLineInterfaceController controller;

	public CommandLineInterfaceView(CommandLineInterfaceController c){
		this.controller = c;
	}
	
	public void displayPrompt() {
		String prompt = "Please select a choice:\n" + "\t1) List all companies\n" + "\t2) List all computers\n"
				+ "\t3) Show computer details\n" + "\t4) Create a computer\n" + "\t5) Modify a computer\n"
				+ "\t6) Delete a computer\n";
		System.out.println(prompt);
	}
	
	public void displayCompanies(List<Company> l){
		StringBuilder sb = new StringBuilder();
		for (Company company : l) {
			sb.append(company.toString()).append("\n");
		}
		System.out.println(sb);
	}
	
	public void displayComputers(List<Computer> l){
		StringBuilder sb = new StringBuilder();
		for (Computer computer : l) {
			sb.append(computer.toString()).append("\n");
		}
		System.out.println(sb);
	}
	
	public void showComputerDetail(Computer comp){
		System.out.println(comp);
	}
}
