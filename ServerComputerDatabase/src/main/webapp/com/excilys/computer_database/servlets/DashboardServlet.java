package com.excilys.computer_database.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
    // Request's parameters
    private static final String NB_PER_PAGE = "nbPerPage", CURRENT_PAGE = "currentPage", ORDER_BY = "orderBy",
            SEARCH = "search", COMPUTER_LIST = "computerList", NB_RESULTS = "nbResults", LIST_TO_DELETE = "selection";
    // Legal values
    private static final List<String> ORDER_BY_AUTHORIZED = Arrays
            .asList(new String[] { "id", "name", "introduced", "discontinued", "company" });
    private static final List<String> NB_PER_PAGE_AUTHORIZED = Arrays.asList(new String[] { "10", "50", "100" });

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
        int nbPerPage = getNbPerPage(request);
        request.setAttribute(NB_PER_PAGE, nbPerPage);

        // Fetch the page number
        int currentPage = getCurrentPage(request);
        request.setAttribute(CURRENT_PAGE, currentPage);

        // Analyse "orderby" parameter
        String orderBy = getOrderBy(request);
        request.setAttribute(ORDER_BY, orderBy);

        // List to display : 2 cases
        Page<ComputerDTO> computerList = null;
        Integer nbResult = null;

        // A) Display a search result
        String search = request.getParameter(SEARCH);
        if (search != null) {
            // Fetch the computers list
            computerList = computerServ.searchByName(search, currentPage * nbPerPage, nbPerPage);

            // Fetch the number of results
            nbResult = computerServ.countSearchByNameNbResult(search);
        }
        // B) Display all computers
        else {
            // Fetch the list of computers
            computerList = computerServ.listSomeComputersDTO(currentPage * nbPerPage, nbPerPage, orderBy);

            // Fetch the total number of computers
            nbResult = computerServ.getComputerCount();
        }

        request.setAttribute(COMPUTER_LIST, computerList.getList());
        request.setAttribute(NB_RESULTS, nbResult);

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
        String selection = request.getParameter(LIST_TO_DELETE);
        if (selection != null) {
            // Fetch the list
            String[] tab = selection.split(",");

            // Verify it's long value
            Long[] idTab = new Long[tab.length];
            for (int i = 0; i < tab.length; i++) {
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
    private int getNbPerPage(HttpServletRequest request) {
        // Fetch the eventual "limit" parameter
        String limitParam = request.getParameter(NB_PER_PAGE);

        // Check the legality of the value
        if (!NB_PER_PAGE_AUTHORIZED.contains(limitParam)) {
            return Integer.valueOf(NB_PER_PAGE_AUTHORIZED.get(0));
        }

        return Integer.valueOf(limitParam);
    }

    private int getCurrentPage(HttpServletRequest request) {
        int defaultValue = 0;

        // Fetch the eventual "current" parameter
        String currentParam = request.getParameter(CURRENT_PAGE);
        if (currentParam == null) {
            return defaultValue;
        }

        // Convert it to int (catching risk of illegal value)
        try {
            return Integer.parseInt(currentParam);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private String getOrderBy(HttpServletRequest request) {
        String orderby = request.getParameter(ORDER_BY);

        if (!ORDER_BY_AUTHORIZED.contains(orderby)) {
            return ORDER_BY_AUTHORIZED.get(0);
        }
        return orderby;
    }
}
