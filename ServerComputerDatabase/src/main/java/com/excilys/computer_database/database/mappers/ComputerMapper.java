package com.excilys.computer_database.database.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computer_database.database.dao.ComputerDAO;
import com.excilys.computer_database.entity.Computer;

public class ComputerMapper implements RowMapper<Computer> {

    @Override
    public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Computer.ComputerBuilder(rs.getString(ComputerDAO.NAME))
                .id(rs.getLong(ComputerDAO.ID))
                .introduced(rs.getTimestamp(ComputerDAO.INTRODUCED))
                .discontinued(rs.getTimestamp(ComputerDAO.DISCONTINUED))
                .company((new CompanyMapper()).mapRow(rs, -1))
                .build();
    }

}
