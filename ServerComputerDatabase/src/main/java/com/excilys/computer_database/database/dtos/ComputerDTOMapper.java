package com.excilys.computer_database.database.dtos;

import org.joda.time.LocalDateTime;

import com.excilys.computer_database.database.mappers.Mapper;
import com.excilys.computer_database.entity.Computer;

public class ComputerDTOMapper extends DTO implements Mapper<ComputerDTO, Computer> {
    private Long id, companyId;
    private String name, companyName;
    private LocalDateTime introduced, discontinued;

    @Override
    public ComputerDTO unmap(Computer c) {
        return new ComputerDTO(c);
    }
}
