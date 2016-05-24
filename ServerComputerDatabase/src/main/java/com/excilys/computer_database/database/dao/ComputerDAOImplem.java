package com.excilys.computer_database.database.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.excilys.computer_database.database.dtos.ComputerDTO;
import com.excilys.computer_database.database.mappers.ComputerDTOMapper;
import com.excilys.computer_database.database.mappers.ComputerMapper;
import com.excilys.computer_database.database.services.ComputerService.COLUMN;
import com.excilys.computer_database.database.services.ComputerService.ORDER;
import com.excilys.computer_database.entity.Computer;
import com.excilys.computer_database.helpers.DateHelper;
import com.excilys.computer_database.ui.Page;

public class ComputerDAOImplem implements ComputerDAO {
    public static final String TABLE_NAME = "computer";
    public static final String ID = TABLE_NAME + ".id", NAME = TABLE_NAME + ".name",
            COMPANY_ID = TABLE_NAME + ".company_id", INTRODUCED = TABLE_NAME + ".introduced",
            DISCONTINUED = TABLE_NAME + ".discontinued";

    private static final String FIND_REQUEST = "SELECT * FROM " + TABLE_NAME + " LEFT JOIN "
            + CompanyDAOImplem.TABLE_NAME + " ON " + COMPANY_ID + " = " + CompanyDAOImplem.ID + " WHERE " + ID + " = ?",
            FIND_ALL_REQUEST = "SELECT * FROM " + TABLE_NAME + " LEFT JOIN " + CompanyDAOImplem.TABLE_NAME + " ON "
                    + COMPANY_ID + " = " + CompanyDAOImplem.ID,
                    INSERT_FULL_REQUEST = "INSERT INTO " + TABLE_NAME + " ( " + NAME + "," + INTRODUCED + "," + DISCONTINUED
                    + "," + COMPANY_ID + " ) VALUES (?,?,?,?) ",
                    UPDATE_REQUEST = "UPDATE " + TABLE_NAME + " SET " + NAME + " = ? , " + INTRODUCED + " = ? , " + DISCONTINUED
                    + " = ? , " + COMPANY_ID + " = ? WHERE " + ID + " = ?",
                    DELETE_REQUEST = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " = ? ",
                    DELETE_LIST = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " IN ",
                    DELETE_BY_COMPANY_ID_REQUEST = "DELETE FROM " + TABLE_NAME + " WHERE " + COMPANY_ID + " = ?";

    JdbcTemplate jdbcTemplate;

    @Override
    public Computer create(Computer comp) throws DAOException {
        // comp mustn't have an id
        if (comp.getId() != null) {
            throw new DAOException("The object must have no id at the creation.");
        }

        // Exécution de la requête
        SimpleJdbcInsert jdbc = new SimpleJdbcInsert(jdbcTemplate).withTableName("company").usingGeneratedKeyColumns(ID);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("name", comp.getName());
        parameters.put("introduced",
                (comp.getIntroduced() == null) ? null : DateHelper.localDateToTimestamp(comp.getIntroduced()));
        parameters.put("discontinued",
                (comp.getDiscontinued() == null) ? null : DateHelper.localDateToTimestamp(comp.getDiscontinued()));
        parameters.put("company_id", (comp.getCompany() == null) ? null : comp.getCompany());

        Long id = jdbc.executeAndReturnKey(parameters).longValue();
        if (id == 0) {
            throw new DAOException("L'insertion n'a pas aboutie");
        }
        comp.setId(id);

        return comp;
    }

    @Override
    public Computer find(long id) throws DAOException {
        try {
            return jdbcTemplate.queryForObject(FIND_REQUEST, new Object[] { id }, new ComputerMapper());
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Computer> findAll() throws DAOException {
        try {
            return jdbcTemplate.query(FIND_ALL_REQUEST, new ComputerMapper());
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Computer update(Computer comp) throws DAOException {
        try {
            Timestamp timestamp = (comp.getIntroduced() == null) ? null
                    : DateHelper.localDateToTimestamp(comp.getIntroduced());
            Timestamp discontinued = (comp.getDiscontinued() == null) ? null
                    : DateHelper.localDateToTimestamp(comp.getDiscontinued());
            jdbcTemplate.update(UPDATE_REQUEST,
                    new Object[] { comp.getName(), timestamp, discontinued, comp.getCompany().getId(), comp.getId() });
            return comp;
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(Long id) throws DAOException {
        // TODO : Vérifier que l'id existe
        try {
            jdbcTemplate.update(DELETE_REQUEST, new Object[] { id });
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteComputerList(Long[] t) throws DAOException {
        // setArray(..) is not supported with mysql, so we have to implement
        // it...
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        for (int i = 0; i < t.length; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(t[i]);
        }
        sb.append(")");

        try {
            jdbcTemplate.update(DELETE_LIST + sb.toString());
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteByCompanyID(Long companyId) throws DAOException {
        try {
            jdbcTemplate.update(DELETE_REQUEST, new Object[] { companyId });
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Page<ComputerDTO> listComputersDTO(COLUMN column, ORDER order, String search, int begining, int pageSize)
            throws DAOException {
        // Request construction
        StringBuilder sb = new StringBuilder();
        sb.append(
                "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name ");
        sb.append("FROM computer LEFT JOIN company ON company.id = computer.id ");

        // Search
        if (search != null) {
            sb.append(" WHERE computer.name = '" + search + "'  + OR company.name = '" + search + " ");
        }

        // Column
        if (column != null) {
            String col;

            switch (column) {
            case COMPUTER_NAME:
                col = NAME;
                break;
            case INTRODUCED:
                col = INTRODUCED;
                break;
            case DISCONTINUED:
                col = DISCONTINUED;
                break;
            case COMPANY_NAME:
                col = CompanyDAOImplem.NAME;
                break;
            default:
                col = NAME;
                break;
            }
            sb.append(" ORDER BY ").append(col);

            // ASC / DESC
            if (order != null && order.equals(ORDER.ASC)) {
                sb.append(" ASC ");
            } else {
                sb.append(" DESC ");
            }
        }

        // Page size
        sb.append("LIMIT " + pageSize + " OFFSET " + begining + " ");

        // PreparedStatement
        try {
            List<ComputerDTO> list = jdbcTemplate.query(sb.toString(), new ComputerDTOMapper());

            return new Page<>(list, begining / pageSize, pageSize);
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public int countSearchResult(String search) {
        // Request construction
        String request;
        if (search != null) {
            request = "SELECT COUNT(*) FROM " + TABLE_NAME + " LEFT JOIN " + CompanyDAOImplem.TABLE_NAME + " ON "
                    + COMPANY_ID + " = " + CompanyDAOImplem.ID + " WHERE " + NAME + " = ?  OR " + CompanyDAOImplem.NAME
                    + " = ?";
        } else {
            request = "SELECT COUNT(*) FROM " + TABLE_NAME;
        }

        try {
            return jdbcTemplate.queryForObject(request, Integer.class);
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
