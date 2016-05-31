package com.excilys.computer_database.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.computer_database.dto.ComputerDTO;
import com.excilys.computer_database.service.CompanyService;

@Component
public class ComputerDTOValidator implements Validator {
    CompanyService companyService;

    @Override
    public boolean supports(Class<?> paramClass) {
        return ComputerDTO.class.equals(paramClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        // NB : We consider that the annotation validation has been done
        ComputerDTO computerDTO = (ComputerDTO) obj;
        if(computerDTO.getIntroduced() != null && computerDTO.getDiscontinued() != null && computerDTO.getDiscontinued().isBefore(computerDTO.getIntroduced())) {
            errors.rejectValue("discontinued", "discontinued after introduced", "Discontinued date must be after Introduced date");
        }
    }
}
