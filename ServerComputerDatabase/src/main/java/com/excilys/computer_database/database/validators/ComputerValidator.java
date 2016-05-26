package com.excilys.computer_database.database.validators;


import java.time.LocalDate;

import com.excilys.computer_database.database.dao.DAOException;
import com.excilys.computer_database.database.services.CompanyService;
import com.excilys.computer_database.entity.Company;
import com.excilys.computer_database.entity.Computer;
import com.excilys.computer_database.entity.Computer.ComputerBuilder;
import com.excilys.computer_database.helpers.DateHelper;

public class ComputerValidator {
    CompanyService companyService;

    public static Computer validate(CompanyService companyService, String name, String introducedString, String discontinuedString, Long companyId)
            throws ValidationException, DAOException {
        // Name
        if (name == null || name.length() < 3) {
            throw new ValidationException("No given name, or too short");
        }

        // Date
        LocalDate introduced = null;
        if (introducedString != null && !introducedString.isEmpty()) {
            introduced = DateHelper.isoStringToLocalDate(introducedString);
        }
        LocalDate discontinued = null;
        if (discontinuedString != null && !discontinuedString.isEmpty()) {
            discontinued = DateHelper.isoStringToLocalDate(discontinuedString);
        }
        if (introduced != null && discontinued != null) {
            if(introduced.isAfter(discontinued)) {
                throw new ValidationException("Introduced date must be lesser than Discontinued date.");
            }
        }

        // Construct the Computer
        ComputerBuilder builder = new ComputerBuilder(name).introduced(introduced).discontinued(discontinued);

        Company company = companyService.find(companyId);
        builder.company(company);

        return builder.build();
    }
}
