package com.excilys.computer_database.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computer_database.database.dtos.CompanyDTO;
import com.excilys.computer_database.database.dtos.ComputerDTO;
import com.excilys.computer_database.database.dtos.ComputerDTOMapper;
import com.excilys.computer_database.database.services.CompaniesService;
import com.excilys.computer_database.database.services.ComputerService;

/**
 * Servlet implementation class EditComputerServlet
 */
@WebServlet("/EditComputerServlet")
public class EditComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
        ComputerService serv = new ComputerService();
        ComputerDTOMapper mapper = new ComputerDTOMapper();
        ComputerDTO computer = mapper.unmap(serv.getComputerById(idComputer));
        request.setAttribute("computer", computer);

        // Fetch the company list
        CompaniesService service = new CompaniesService();
        List<CompanyDTO> companyList = service.getDTOList();
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
        doGet(request, response);
    }

}
