package com.excilys.computer_database.database.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.excilys.computer_database.database.mappers.CompanyMapper;
import com.excilys.computer_database.entity.Company;

@Repository
public class CompanyDAOImplem implements CompanyDAO {
    public static final String TABLE_NAME = "company";
    public static final String ID = "company.id", NAME = "company.name";

    private static final String FIND_REQUEST = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = ?",
            FIND_ALL_REQUEST = "SELECT " + ID + "," + NAME + " FROM company",
            UPDATE_REQUEST = "UPDATE " + TABLE_NAME + " SET " + NAME + " = ? WHERE " + ID + " = ?",
            DELETE_REQUEST = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " = ? ";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Company create(Company obj) throws DAOException {
        SimpleJdbcInsert jdbc = new SimpleJdbcInsert(jdbcTemplate).withTableName("company");
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("name", obj.getName());

        Long id = jdbc.executeAndReturnKey(parameters).longValue();
        if (id == 0) {
            throw new DAOException("L'insertion n'a pas aboutie");
        }
        obj.setId(id);

        return obj;
    }

    @Override
    public Company find(long id) throws DAOException {
        try {
            return jdbcTemplate.queryForObject(FIND_REQUEST, new Object[]{id}, new CompanyMapper());
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Company> findAll() throws DAOException {
        try {
            return jdbcTemplate.query(FIND_ALL_REQUEST, new CompanyMapper());
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Company> findSome(int begining, int nbPerPage, String orderBy) throws DAOException {
        String request = FIND_ALL_REQUEST + " ORDER BY case " + orderBy + " LIMIT " + nbPerPage + " OFFSET " + begining;

        try {
            return jdbcTemplate.query(request, new CompanyMapper());
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Company update(Company obj) throws DAOException {
        try {
            jdbcTemplate.update(UPDATE_REQUEST, new Object[] { obj.getName(), obj.getId() });
            return obj;
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(Long id) throws DAOException {
        try {
            jdbcTemplate.update(DELETE_REQUEST, new Object[] { id });
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    /**
     * @param datasource the datasource to set
     */
    public void setDatasource(DataSource datasource) {
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }
}
