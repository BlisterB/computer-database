package com.excilys.computer_database.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computer_database.database.dao.DAOException;
import com.excilys.computer_database.database.dtos.ComputerDTO;
import com.excilys.computer_database.database.services.ComputerService;
import com.excilys.computer_database.ui.Page;

/**
 * Servlet implementation class DashboardServlet
 */
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * The GET method is called to display computer list with simple parameters : limit (nb of element per page), current (page)
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ComputerService computerServ = new ComputerService();

        // Fetch the number of computers to display
        int limit = getLimit(request);

        // Fetch the page number
        int current = getCurrentPage(request);

        // Fetch the total number of computers
        Integer nbComputer = null;
        try {
            nbComputer = computerServ.getComputerCount();
        } catch (DAOException e) {
            // Stay at null
            e.printStackTrace();
        }
        request.setAttribute("nbComputer", nbComputer);

        // Fetch the list of computers
        Page<ComputerDTO> computerList = null;
        try {
            computerList = computerServ.listSomeComputersDTO(current*nbComputer, limit);
            request.setAttribute("computerList", computerList.getList());
        } catch (DAOException e) {
            // TODO : Afficher message d'erreur
            e.printStackTrace();
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
    }

    /**
     * The POST method is called to display result based on a name filter.
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

    /** Return the number of computers to display.
     * @param request The request
     * @return The number of computers to display
     */
    private int getLimit(HttpServletRequest request){
        int nbPerDefault = 10;
        int[] acceptedNumbers = {10, 50, 100};

        // Fetch the eventual "limit" parameter
        String limitParam = request.getParameter("limit");
        if (limitParam == null) {
            return nbPerDefault;
        }

        // Convert it to int
        int limit = -1;
        try {
            limit = Integer.parseInt(limitParam);
        } catch(NumberFormatException e) {
            return nbPerDefault;
        }

        // Verify if the value is legal
        for(int i : acceptedNumbers){
            if (limit == i) {
                return i;
            }
        }
        return nbPerDefault;
    }

    private int getCurrentPage(HttpServletRequest request){
        int nbPerDefault = 0;

        // Fetch the eventual "current" parameter
        String currentParam = request.getParameter("current");
        if (currentParam == null) {
            return nbPerDefault;
        }

        // Convert it to int
        try {
            return Integer.parseInt(currentParam);
        } catch(NumberFormatException e) {
            return nbPerDefault;
        }
    }
}
