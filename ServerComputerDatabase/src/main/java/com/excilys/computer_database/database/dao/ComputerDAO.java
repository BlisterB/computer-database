package com.excilys.computer_database.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.excilys.computer_database.database.dtos.ComputerDTO;
import com.excilys.computer_database.database.mappers.ComputerDTOMapper;
import com.excilys.computer_database.database.mappers.ComputerMapper;
import com.excilys.computer_database.database.services.ComputerService.COLUMN;
import com.excilys.computer_database.database.services.ComputerService.ORDER;
import com.excilys.computer_database.entity.Computer;
import com.excilys.computer_database.helpers.DateHelper;
import com.excilys.computer_database.ui.Page;

public class ComputerDAO extends DAO<Computer> {
    public static final String TABLE_NAME = "computer";
    public static final String ID = TABLE_NAME + ".id", NAME = TABLE_NAME + ".name",
            COMPANY_ID = TABLE_NAME + ".company_id", INTRODUCED = TABLE_NAME + ".introduced",
            DISCONTINUED = TABLE_NAME + ".discontinued";

    private static final String FIND_REQUEST = "SELECT * FROM " + TABLE_NAME + " LEFT JOIN " + CompanyDAO.TABLE_NAME
            + " ON " + COMPANY_ID + " = " + CompanyDAO.ID + " WHERE " + ID + " = ?",
            FIND_ALL_REQUEST = "SELECT * FROM " + TABLE_NAME + " LEFT JOIN " + CompanyDAO.TABLE_NAME + " ON "
                    + COMPANY_ID + " = " + CompanyDAO.ID,
                    INSERT_FULL_REQUEST = "INSERT INTO " + TABLE_NAME + " ( " + NAME + "," + INTRODUCED + "," + DISCONTINUED
                    + "," + COMPANY_ID + " ) VALUES (?,?,?,?) ",
                    UPDATE_REQUEST = "UPDATE " + TABLE_NAME + " SET " + NAME + " = ? , " + INTRODUCED + " = ? , " + DISCONTINUED
                    + " = ? , " + COMPANY_ID + " = ? WHERE " + ID + " = ?",
                    DELETE_REQUEST = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " = ? ",
                    DELETE_LIST = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " IN ",
                    DELETE_BY_COMPANY_ID_REQUEST = "DELETE FROM " + TABLE_NAME + " WHERE " + COMPANY_ID + " = ?";

    @Override
    public String getFindRequest() {
        return FIND_REQUEST;
    }

    @Override
    public String getFindAllRequest() {
        return FIND_ALL_REQUEST;
    }

    @Override
    public Computer create(Computer comp) throws DAOException {
        // comp must not have an id
        if (comp.getId() != null) {
            throw new DAOException("The object must have no id at the creation.");
        }

        // Exécution de la requête
        try (Connection con = datasource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(INSERT_FULL_REQUEST, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, comp.getName());
            if (comp.getIntroduced() != null) {
                stmt.setTimestamp(2, DateHelper.localDateToTimestamp(comp.getIntroduced()));
            } else {
                stmt.setTimestamp(2, null);
            }
            if (comp.getDiscontinued() != null) {
                stmt.setTimestamp(3, DateHelper.localDateToTimestamp(comp.getDiscontinued()));
            } else {
                stmt.setTimestamp(3, null);
            }

            if (comp.getCompany() != null) {
                stmt.setLong(4, comp.getCompany().getId());
            } else {
                stmt.setNull(4, java.sql.Types.BIGINT);
            }

            stmt.executeUpdate();

            // Update the id with the inserted one
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.first()) {
                comp.setId(rs.getLong(1));
            } else {
                String errorMessage = "L'insertion n'a pas aboutie";
                throw new SQLException(errorMessage);
            }

            return comp;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Computer update(Computer comp) throws DAOException {
        try (Connection con = datasource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(UPDATE_REQUEST);

            stmt.setString(1, comp.getName());
            stmt.setTimestamp(2, DateHelper.localDateToTimestamp(comp.getIntroduced()));
            stmt.setTimestamp(3, DateHelper.localDateToTimestamp(comp.getDiscontinued()));
            stmt.setLong(4, comp.getCompany().getId());
            stmt.setLong(5, comp.getId());

            stmt.executeUpdate();

            return comp;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(Long id) throws DAOException {
        // TODO : Vérifier que l'id existe
        try (Connection con = datasource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(DELETE_REQUEST);
            stmt.setLong(1, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

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

        try (Connection con = datasource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(DELETE_LIST + sb.toString());
            // System.out.println(stmt.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }

    public void deleteByCompanyID(Long companyId) throws DAOException {
        try (Connection con = datasource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(DELETE_BY_COMPANY_ID_REQUEST);
            stmt.setLong(1, companyId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public Page<ComputerDTO> listComputersDTO(COLUMN column, ORDER order, String search, int begining, int pageSize)
            throws DAOException {
        // Request construction
        StringBuilder sb = new StringBuilder();
        sb.append(
                "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name ");
        sb.append("FROM computer LEFT JOIN company ON company.id = computer.id ");

        // Search
        if (search != null) {
            sb.append(" WHERE computer.name = ?  OR company.name = ? ");
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
                col = CompanyDAO.NAME;
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
        sb.append("LIMIT ? OFFSET ? ");

        // PreparedStatement
        try (Connection con = datasource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(sb.toString());
            int nextParam = 1;

            // Search
            if (search != null) {
                stmt.setString(nextParam++, search);
                stmt.setString(nextParam++, search);
            }

            // Limit
            stmt.setInt(nextParam++, pageSize);
            stmt.setInt(nextParam++, begining);

            // Execute request
            ResultSet rs = stmt.executeQuery();

            // Unmap
            List<ComputerDTO> list = new LinkedList<>();
            while (rs.next()) {
                // System.out.println(unmap(rs));
                list.add(ComputerDTOMapper.unmap(rs));
            }

            return new Page<>(list, begining / pageSize, pageSize);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public int countSearchResult(String search) {
        // Request construction
        String request;
        if (search != null) {
            request = "SELECT COUNT(*) FROM " + TABLE_NAME + " LEFT JOIN " + CompanyDAO.TABLE_NAME + " ON " + COMPANY_ID
                    + " = " + CompanyDAO.ID + " WHERE " + NAME + " = ?  OR " + CompanyDAO.NAME + " = ?";
        } else {
            request = "SELECT COUNT(*) FROM " + TABLE_NAME;
        }

        try (Connection con = datasource.getConnection()) {

            PreparedStatement stmt = con.prepareStatement(request);
            if (search != null) {
                stmt.setString(1, search);
                stmt.setString(2, search);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.first()) {
                return rs.getInt(1);
            }
            throw new DAOException("Imposible to calculate the DB size");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Computer unmap(ResultSet rs) throws DAOException {
        try {
            return (new ComputerMapper()).mapRow(rs, -1);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
