package com.excilys.computer_database.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computer_database.database.dtos.CompanyDTO;
import com.excilys.computer_database.database.services.CompaniesService;
import com.excilys.computer_database.database.services.ComputerService;
import com.excilys.computer_database.database.validators.ComputerValidator;
import com.excilys.computer_database.entity.Computer;

public class AddComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CompaniesService companyService = new CompaniesService();
    private ComputerService computerService = new ComputerService();

    public AddComputerServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Fetch the company list
        List<CompanyDTO> companyList = companyService.getDTOList();
        request.setAttribute("companyList", companyList);

        // Redirect to the JSP
        this.getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Fetch the parameters
        String name = request.getParameter("name");
        String introducedString = request.getParameter("introduced");
        String discontinuedString = request.getParameter("discontinued");
        Long companyId = Long.parseLong(request.getParameter("companyId"));

        // Validate the computer informations
        Computer computer = ComputerValidator.validate(name, introducedString, discontinuedString, companyId);

        // Create the computer in the DB
        computerService.createComputer(computer);

        // On redirige vers le dashboard
        response.sendRedirect("/computer-database/dashboard");
    }
}
