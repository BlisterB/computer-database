package com.excilys.computer_database.binding;

import org.springframework.stereotype.Component;

import com.excilys.computer_database.core.dto.ComputerDTO;
import com.excilys.computer_database.core.entity.Company;
import com.excilys.computer_database.core.entity.Computer;

@Component
public class ComputerDTOMapper{

    public ComputerDTO unmap(Computer c) {
        return new ComputerDTO(c);
    }
    public Computer map(ComputerDTO dto) {
        Computer c = new Computer.ComputerBuilder(dto.getName()).introduced(dto.getIntroduced())
                .id(dto.getId())
                .discontinued(dto.getDiscontinued())
                .company(new Company())
                .build();
        c.getCompany().setId(dto.getCompanyId());
        c.getCompany().setName(dto.getCompanyName());
        return c;
    }
}
