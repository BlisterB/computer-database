package com.excilys.computer_database.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.computer_database.dto.ComputerDTO;
import com.excilys.computer_database.service.ComputerService;
import com.excilys.computer_database.service.ComputerService.COLUMN;

@Controller
@RequestMapping("/dashboard")
public class Dashboard {
    @Autowired
    ComputerService computerService ;

    // Request's parameters
    private static final String PAGE_SIZE = "pageSize", CURRENT_PAGE = "page", ORDER_BY = "column", SEARCH = "search",
            COMPUTER_LIST = "computerList", NB_RESULTS = "nbResults", LIST_TO_DELETE = "selection", ORDER_TAG = "order";
    private static final String VIEW_NAME = "dashboard";
    // Legal param values
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
    private static final Map<String, Direction> ORDER_AUTHORIZED;
    static {
        ORDER_AUTHORIZED = new HashMap<>();
        ORDER_AUTHORIZED.put("ASC", Direction.ASC);
        ORDER_AUTHORIZED.put("DESC", Direction.DESC);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String dashboardGet(HttpServletRequest request) {
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
        Direction order = ORDER_AUTHORIZED.get(request.getParameter(ORDER_TAG));
        request.setAttribute(ORDER_TAG, order);

        // Search : can be null
        String search = request.getParameter(SEARCH);
        request.setAttribute(SEARCH, search);

        // Ask the DB
        Object[] searchResult = computerService.listComputersDTO(column, order, search, currentPage,
                pageSize);

        @SuppressWarnings("unchecked")
        List<ComputerDTO> computers = (List<ComputerDTO>) searchResult[0];
        Long nbResult = (Long) searchResult[1];

        request.setAttribute(COMPUTER_LIST, computers);
        request.setAttribute(NB_RESULTS, nbResult);

        return VIEW_NAME;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String dashboardPost(HttpServletRequest request) {
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

            computerService.deleteComputerList(idTab);
        }

        // Redirection to the GET Page
        return dashboardGet(request);
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
