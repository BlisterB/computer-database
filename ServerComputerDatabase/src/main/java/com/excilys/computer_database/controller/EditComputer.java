package com.excilys.computer_database.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.computer_database.dto.CompanyDTO;
import com.excilys.computer_database.dto.ComputerDTO;
import com.excilys.computer_database.mapper.ComputerDTOMapper;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;

@Controller
@RequestMapping("editComputer")
public class EditComputer {
    @Autowired
    private ComputerService computerService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private Dashboard dashboard;

    @Autowired
    Validator computerDTOValidator;

    // NB : Permet d'assocer le validator à la validation du bean ComputerDTO, en plus des annotations
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(computerDTOValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getRequest(HttpServletRequest request) {
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
    public String postRequest(HttpServletRequest request, @Valid ComputerDTO computerDTO, BindingResult result ) {
        // TODO : Afficher en front les erreurs rencontrées en cas de validation échouant
        // Annotation validation
        if (result.hasErrors()) {
            System.out.println("Erreur lors de la création d'un bean :");
            for(ObjectError e : result.getAllErrors()){
                System.out.println(e);
            }
            return this.getRequest(request);
        }

        // Validation by constraint (date coherences)
        computerService.update(computerDTO);

        return dashboard.dashboardGet(request);
    }
}
