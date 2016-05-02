package com.excilys.computer_database.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.excilys.computer_database.database.dao.DAOException;
import com.excilys.computer_database.database.dao.NotFoundException;
import com.excilys.computer_database.database.dtos.CompanyDTO;
import com.excilys.computer_database.database.services.CompaniesService;
import com.excilys.computer_database.database.services.ComputerService;
import com.excilys.computer_database.entity.Company;
import com.excilys.computer_database.entity.Computer.ComputerBuilder;

public class AddComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    public AddComputerServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch the company list
        CompaniesService service = new CompaniesService();
        try {
            List<CompanyDTO> companyList = service.getDTOList();
            request.setAttribute("companyList", companyList);
            this.getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(request, response);
        } catch (DAOException e) {
            // TODO : Que faire en cas d'erreur ?
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CompaniesService companyService = new CompaniesService();
        ComputerService computerService= new ComputerService();

        // TODO : Effectuer les verification d'input
        // TODO : Verifier name vide
        String name = request.getParameter("computerName");
        ComputerBuilder builder = new ComputerBuilder(name);


        // Date
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

        String introducedString = request.getParameter("introduced");
        LocalDate introduced = null;
        try {
            introduced = LocalDate.parse(introducedString, formatter);
            builder.introduced(introduced);
        } catch (Exception e) {
            // Stay with null value
        }

        String discontinuedString = request.getParameter("discontinued");
        LocalDate discontinued = null;
        try {
            discontinued = LocalDate.parse(discontinuedString, formatter);
            builder.discontinued(discontinued);
        } catch (Exception e) {
            // Stay with null value
        }

        // Company id
        try {
            try {
                Long companyId = Long.parseLong(request.getParameter("companyId"));
                Company company = companyService.find(companyId);
                System.out.println("company found : " + company);
                builder.company(company);
            } catch (DAOException | NotFoundException e) {
                e.printStackTrace();
            }
        } catch (NumberFormatException  e) {
            e.printStackTrace();
            // Stay with null value
        }

        // Create the computer in the DB
        try {
            computerService.createComputer(builder.build());
        } catch (DAOException e) {
            e.printStackTrace();
        }

        // On redirige vers le dashboard
        response.sendRedirect("/computer-database/dashboard");
    }
}
