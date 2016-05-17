package com.excilys.computer_database.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computer_database.database.dtos.ComputerDTO;
import com.excilys.computer_database.database.services.ComputerService;
import com.excilys.computer_database.database.services.ComputerService.COLUMN;
import com.excilys.computer_database.database.services.ComputerService.ORDER;
import com.excilys.computer_database.ui.Page;

/**
 * Servlet implementation class DashboardServlet
 */
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // Request's parameters
    private static final String PAGE_SIZE = "pageSize", CURRENT_PAGE = "page", ORDER_BY = "column", SEARCH = "search",
            COMPUTER_LIST = "computerList", NB_RESULTS = "nbResults", LIST_TO_DELETE = "selection", ORDER_TAG = "order";

    // Legal values
    private static final Map<String, COLUMN> COLUMN_AUTHORISED;
    static {
        COLUMN_AUTHORISED = new HashMap<>();
        COLUMN_AUTHORISED.put("computerName", COLUMN.COMPUTER_NAME);
        COLUMN_AUTHORISED.put("introduced", COLUMN.INTRODUCED);
        COLUMN_AUTHORISED.put("discontinued", COLUMN.DISCONTINUED);
        COLUMN_AUTHORISED.put("companyName", COLUMN.COMPANY_NAME);
    }
    private static final Map<String, Integer> PAGE_SIZE_AUTHORIZED;
    static {
        PAGE_SIZE_AUTHORIZED = new HashMap<>();
        PAGE_SIZE_AUTHORIZED.put("10", 10);
        PAGE_SIZE_AUTHORIZED.put("50", 50);
        PAGE_SIZE_AUTHORIZED.put("100", 100);
    }
    private static final Map<String, ORDER> ORDER_AUTHORIZED;
    static {
        ORDER_AUTHORIZED = new HashMap<>();
        ORDER_AUTHORIZED.put("ASC", ORDER.ASC);
        ORDER_AUTHORIZED.put("DESC", ORDER.DESC);
    }

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
        super();
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

        try {
            // Fetch the page's size
            Integer pageSize = PAGE_SIZE_AUTHORIZED.get(request.getParameter(PAGE_SIZE));
            if (pageSize == null) {
                pageSize = 10;
            }
            request.setAttribute(PAGE_SIZE, pageSize);

            // Fetch the page number
            int currentPage = getCurrentPage(request);
            request.setAttribute(CURRENT_PAGE, currentPage);

            // Fetch the orderby column : can be null
            String columnParam = request.getParameter(ORDER_BY);
            COLUMN column = COLUMN_AUTHORISED.get(columnParam);

            // Fetch the order : can be null
            ORDER order = ORDER_AUTHORIZED.get(request.getParameter(ORDER_TAG));
            request.setAttribute(ORDER_TAG, order);

            // Search : can be null
            String search = request.getParameter(SEARCH);
            request.setAttribute(SEARCH, search);

            // Ask the DB
            ComputerService computerServ = new ComputerService();
            Page<ComputerDTO> computerList = computerServ.listComputersDTO(column, order, search, currentPage * pageSize, pageSize);
            int nbResult = computerServ.countListResult(search);

            request.setAttribute(COMPUTER_LIST, computerList.getList());
            request.setAttribute(NB_RESULTS, nbResult);

            this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
