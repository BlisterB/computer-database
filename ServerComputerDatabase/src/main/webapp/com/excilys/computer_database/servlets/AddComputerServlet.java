package com.excilys.computer_database.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.excilys.computer_database.database.dao.DAOException;
import com.excilys.computer_database.database.dao.NotFoundException;
import com.excilys.computer_database.database.dtos.CompanyDTO;
import com.excilys.computer_database.database.services.CompaniesService;
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
        // TODO : Effectuer les verification d'input
        String name = request.getParameter("computerName");
        ComputerBuilder builder = new ComputerBuilder(name);

        // Date
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

        String introducedString = request.getParameter("introduced");
        LocalDateTime introduced = null;
        try {
            introduced = LocalDateTime.parse(introducedString, formatter);
            builder.introduced(introduced);
        } catch (Exception e) {
            // Stay with null value
        }

        String discontinuedString = request.getParameter("introduced");
        LocalDateTime discontinued = null;
        try {
            discontinued = LocalDateTime.parse(discontinuedString, formatter);
            builder.discontinued(discontinued);
        } catch (Exception e) {
            // Stay with null value
        }

        // Company id
        try {
            Long companyId = Long.parseLong("company_id : " + request.getParameter("companyId"));
            CompaniesService companyService = new CompaniesService();
            try {
                Company company = companyService.find(companyId);
            } catch (DAOException | NotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (NumberFormatException  e) {
            // Stay with null value
        }
    }
}
