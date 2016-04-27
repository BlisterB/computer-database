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
import com.excilys.computer_database.database.dtos.CompanyDTO;
import com.excilys.computer_database.database.services.CompaniesService;
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

        String introducedString = request.getParameter("introduced");
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        LocalDateTime dateTime = LocalDateTime.parse(introducedString, formatter);
        System.out.println("date : " + dateTime);

        System.out.println("discontinued : " + request.getParameter("discontinued"));
        System.out.println("company_id : " + request.getParameter("companyId"));


    }
}
