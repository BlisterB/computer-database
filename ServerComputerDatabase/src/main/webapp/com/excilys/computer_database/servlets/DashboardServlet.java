package com.excilys.computer_database.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
     * The GET method is called to display computer list with simple parameters
     * : limit (nb of element per page), current (page)
     *
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ComputerService computerServ = new ComputerService();

        // Fetch the number of computers to display
        int limit = getLimit(request);
        request.setAttribute("limit", limit);

        // Fetch the page number
        int current = getCurrentPage(request);
        request.setAttribute("current", current);

        // List to display : 2 cases
        Page<ComputerDTO> computerList = null;
        Integer nbResult = null;

        // A) Display a search result
        if (request.getParameter("search") != null) {
            String search = request.getParameter("search");
            // Fetch the computers list
            computerList = computerServ.searchByName(search, current, limit);

            // Fetch the number of results
            nbResult = computerServ.countSearchByNameNbResult(search);
        }
        // B) Display all computer
        else {
            // Fetch the list of computers
            computerList = computerServ.listSomeComputersDTO(current * limit, limit);

            // Fetch the total number of computers
            nbResult = computerServ.getComputerCount();
        }

        request.setAttribute("computerList", computerList.getList());
        request.setAttribute("nbResults", nbResult);

        this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
    }

    /**
     * The POST method is called to display result based on a name filter.
     *
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // The parameters "selection" indicate a list of computer to delete
        String selection = request.getParameter("selection");
        if(selection != null){
            System.out.println(selection);

            // Fetch the list
            String[] tab = selection.split(",");

            // Verify it's long value
            Long[] idTab = new Long[tab.length];
            for(int i = 0; i < tab.length; i++) {
                idTab[i] = Long.parseLong(tab[i]);
            }

            ComputerService computerServ = new ComputerService();
            computerServ.deleteComputerList(idTab);
        }

        // Redirection to the GET Page
        doGet(request, response);
    }

    /**
     * Return the number of computers to display.
     *
     * @param request The request
     * @return The number of computers to display
     */
    private int getLimit(HttpServletRequest request) {
        int nbPerDefault = 10;
        int[] acceptedNumbers = { 10, 50, 100 };

        // Fetch the eventual "limit" parameter
        String limitParam = request.getParameter("limit");
        if (limitParam == null) {
            return nbPerDefault;
        }

        // Convert it to int
        int limit = -1;
        try {
            limit = Integer.parseInt(limitParam);
        } catch (NumberFormatException e) {
            return nbPerDefault;
        }

        // Verify if the value is legal
        for (int i : acceptedNumbers) {
            if (limit == i) {
                return i;
            }
        }
        return nbPerDefault;
    }

    private int getCurrentPage(HttpServletRequest request) {
        int nbPerDefault = 0;

        // Fetch the eventual "current" parameter
        String currentParam = request.getParameter("current");
        if (currentParam == null) {
            return nbPerDefault;
        }

        // Convert it to int
        try {
            return Integer.parseInt(currentParam);
        } catch (NumberFormatException e) {
            return nbPerDefault;
        }
    }
}
