package com.excilys.computer_database.database.mappers;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computer_database.database.dao.CompanyDAOImplem;
import com.excilys.computer_database.entity.Company;

public class CompanyMapper implements RowMapper<Company> {

    @Override
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Company(rs.getLong(CompanyDAOImplem.ID), rs.getString(CompanyDAOImplem.NAME));
    }
}
