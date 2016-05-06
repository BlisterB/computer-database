package com.excilys.computer_database.database.dtos;

import com.excilys.computer_database.database.mappers.Mapper;
import com.excilys.computer_database.entity.Computer;

public class ComputerDTOMapper extends DTO implements Mapper<ComputerDTO, Computer> {
    @Override
    public ComputerDTO unmap(Computer c) {
        return new ComputerDTO(c);
    }
}
