package com.excilys.computer_database.ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computer_database.database.CompanyDAO;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;

public class TestBDD {
	private static final String COMPUTER_ID = "computer.id", COMPUTER_NAME = "computer.name",
			COMPUTER_INTRODUCED = "computer.introduced", COMPUTER_DISCONTINUED = "computer.discontinued",
			COMPUTER_COMPANY_ID = "computer.company_id";
	//listComputerStatement = con.prepareStatement("SELECT " + COMPUTER_ID + "," + COMPUTER_NAME + "," + COMPUTER_INTRODUCED + "," + COMPUTER_DISCONTINUED + "," + COMPUTER_COMPANY_ID + " FROM computer");

	// public static List<Computer> listCompany
	public static void main(String[] args) {
		try (CompanyDAO c = new CompanyDAO()){
			Company company = c.create(new Company("Blister Corporation"));
			System.out.println("id : " + company.getId());
			company.setName("The New Blister Corporation");
			c.update(company);
		
			
			List<Company> l = c.findAll();
			for (Company company2 : l) {
				System.out.println(company2);
			}
			/*
			List<Computer> l2 = listComputers();
			for (Computer c : l2) {
				System.out.println(c);
			}
			*/
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
