package com.excilys.computer_database.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.computer_database.database.dtos.CompanyDTO;
import com.excilys.computer_database.database.dtos.ComputerDTO;
import com.excilys.computer_database.database.mappers.ComputerDTOMapper;
import com.excilys.computer_database.database.services.CompanyService;
import com.excilys.computer_database.database.services.ComputerService;
import com.excilys.computer_database.database.validators.ComputerValidator;
import com.excilys.computer_database.entity.Computer;

@Controller
@RequestMapping("editComputer")
public class EditComputer {
    @Autowired
    private ComputerService computerService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private Dashboard dashboard;

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(HttpServletRequest request) {
        // Verify the request contains the computer's id to edit
        Long idComputer = Long.parseLong(request.getParameter("idComputer"));

        // Fetch the computer to edit
        ComputerDTOMapper mapper = new ComputerDTOMapper();
        ComputerDTO computer = mapper.unmap(computerService.getComputerById(idComputer));
        request.setAttribute("computer", computer);

        // Fetch the company list
        List<CompanyDTO> companyList = companyService.getDTOList();
        request.setAttribute("companyList", companyList);

        return "editComputer";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doPost(HttpServletRequest request) {
        // Fetch the parameters
        Long idComputer = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        String introducedString = request.getParameter("introduced");
        String discontinuedString = request.getParameter("discontinued");
        Long companyId = Long.parseLong(request.getParameter("companyId"));

        // Validate the computer informations
        Computer computer = ComputerValidator.validate(companyService, name, introducedString, discontinuedString,
                companyId);
        computer.setId(idComputer);

        // Create the computer in the DB
        computerService.update(computer);

        // On redirige vers le dashboard
        return dashboard.dashboardGet(request);
    }
}
