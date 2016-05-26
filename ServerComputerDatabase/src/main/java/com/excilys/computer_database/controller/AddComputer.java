package com.excilys.computer_database.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.computer_database.database.dtos.CompanyDTO;
import com.excilys.computer_database.database.dtos.ComputerDTO;
import com.excilys.computer_database.database.services.CompanyService;
import com.excilys.computer_database.database.services.ComputerService;

@Controller
@RequestMapping("addComputer")
public class AddComputer {
    @Autowired
    CompanyService companyService;
    @Autowired
    ComputerService computerService;
    @Autowired
    Dashboard dashboard;

    @RequestMapping(method = RequestMethod.GET)
    public String getRequest(HttpServletRequest request) {
        // Fetch the company list (for the company field)
        List<CompanyDTO> companyList = companyService.getDTOList();
        request.setAttribute("companyList", companyList);

        // Redirect to the JSP
        return "addComputer";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String postRequest(HttpServletRequest request, @Valid ComputerDTO computerDTO, BindingResult result ) {
        // TODO : Afficher en front les erreurs rencontrées en cas de validation échouant
        // Annotation validation
        if (result.hasErrors()) {
            System.out.println("Erreur lors de la création d'un bean :");
            for(ObjectError e : result.getAllErrors()){
                System.out.println(e);
            }
            return "addComputer";
        }
        // Validation by constraint (date coherences)

        return "addComputer";
        /*
        // Fetch request's parameters
        String name = request.getParameter("name");
        String introducedString = request.getParameter("introduced");
        String discontinuedString = request.getParameter("discontinued");
        Long companyId = Long.parseLong(request.getParameter("companyId"));

        // Validate the informations
        Computer computer = ComputerValidator.validate(companyService, name, introducedString, discontinuedString, companyId);

        // Create the computer in the DB
        computerService.createComputer(computer);

        // On redirige vers le dashboard
        return dashboard.dashboardGet(request);
         */
    }
}
