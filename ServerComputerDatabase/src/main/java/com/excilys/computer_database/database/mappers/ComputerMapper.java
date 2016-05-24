package com.excilys.computer_database.database.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computer_database.database.dao.ComputerDAOImplem;
import com.excilys.computer_database.entity.Computer;

public class ComputerMapper implements RowMapper<Computer> {

    @Override
    public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Computer.ComputerBuilder(rs.getString(ComputerDAOImplem.NAME))
                .id(rs.getLong(ComputerDAOImplem.ID))
                .introduced(rs.getTimestamp(ComputerDAOImplem.INTRODUCED))
                .discontinued(rs.getTimestamp(ComputerDAOImplem.DISCONTINUED))
                .company((new CompanyMapper()).mapRow(rs, -1))
                .build();
    }

}
