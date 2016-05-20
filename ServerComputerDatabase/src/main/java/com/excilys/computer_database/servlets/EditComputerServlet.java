package com.excilys.computer_database.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.computer_database.database.dtos.CompanyDTO;
import com.excilys.computer_database.database.dtos.ComputerDTO;
import com.excilys.computer_database.database.mappers.ComputerDTOMapper;
import com.excilys.computer_database.database.services.CompanyService;
import com.excilys.computer_database.database.services.ComputerService;
import com.excilys.computer_database.database.validators.ComputerValidator;
import com.excilys.computer_database.entity.Computer;

/**
 * Servlet implementation class EditComputerServlet
 */
@WebServlet("/EditComputerServlet")
public class EditComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
    private static ComputerService computerService = (ComputerService) context.getBean("computerService");
    private static CompanyService companyService = (CompanyService) context.getBean("companyService");

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verify the request contains the computer's id to edit
        Long idComputer = Long.parseLong(request.getParameter("idComputer"));

        // Fetch the computer to edit
        ComputerDTOMapper mapper = new ComputerDTOMapper();
        ComputerDTO computer = mapper.unmap(computerService.getComputerById(idComputer));
        request.setAttribute("computer", computer);

        // Fetch the company list
        List<CompanyDTO> companyList = companyService.getDTOList();
        request.setAttribute("companyList", companyList);

        this.getServletContext().getRequestDispatcher("/WEB-INF/editComputer.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Fetch the parameters
        Long idComputer = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        String introducedString = request.getParameter("introduced");
        String discontinuedString = request.getParameter("discontinued");
        Long companyId = Long.parseLong(request.getParameter("companyId"));

        // Validate the computer informations
        Computer computer = ComputerValidator.validate(name, introducedString, discontinuedString, companyId);
        computer.setId(idComputer);

        // Create the computer in the DB
        computerService.update(computer);

        // On redirige vers le dashboard
        response.sendRedirect("/computer-database/dashboard");
    }

}
