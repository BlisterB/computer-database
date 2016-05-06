package com.excilys.computer_database.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.database.DBManager;
import com.excilys.computer_database.database.dtos.ComputerDTO;
import com.excilys.computer_database.database.dtos.ComputerDTOMapper;
import com.excilys.computer_database.database.mappers.Mapper;
import com.excilys.computer_database.database.services.ComputerService.COLUMN;
import com.excilys.computer_database.database.services.ComputerService.ORDER;
import com.excilys.computer_database.entity.Company;
import com.excilys.computer_database.entity.Computer;
import com.excilys.computer_database.helpers.DateHelper;
import com.excilys.computer_database.ui.Page;

public class ComputerDAO extends DAO<Computer> implements Mapper<Computer, ResultSet> {
    // TODO : study the utility of the "volatile"
    private static volatile ComputerDAO instance = null;

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

    private Logger logger;

    /** Constructor. */
    private ComputerDAO() {
        super();
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public String getFindRequest() {
        return FIND_REQUEST;
    }

    @Override
    public String getFindAllRequest() {
        return FIND_ALL_REQUEST;
    }

    @Override
    public Computer unmap(ResultSet rs) throws DAOException {
        // Extract the company
        Company company = (CompanyDAO.getInstance()).unmap(rs);

        // Build the Computer
        try {
            return new Computer.ComputerBuilder(rs.getString(NAME)).id(rs.getLong(ID))
                    .introduced(rs.getTimestamp(INTRODUCED)).discontinued(rs.getTimestamp(DISCONTINUED))
                    .company(company).build();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    /**
     * Factory pattern.
     *
     * @return The unique instance of ComputerDAO
     */
    public static final ComputerDAO getInstance() {
        if (ComputerDAO.instance == null) {
            synchronized (ComputerDAO.class) {
                if (ComputerDAO.instance == null) {
                    ComputerDAO.instance = new ComputerDAO();
                }
            }
        }

        return ComputerDAO.instance;
    }

    @Override
    public Computer create(Computer comp) throws DAOException {
        // comp must not have an id
        if (comp.getId() != null) {
            throw new DAOException("The object must have no id at the creation.");
        }

        // Exécution de la requête
        try (PreparedStatement stmt = DBManager.getConnection().prepareStatement(INSERT_FULL_REQUEST,
                Statement.RETURN_GENERATED_KEYS)) {
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

            // Mise à jour de l'id de l'objet inséré
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.first()) {
                comp.setId(rs.getLong(1));
            } else {
                String errorMessage = "L'insertion n'a pas aboutie";
                logger.error(errorMessage);
                throw new SQLException(errorMessage);
            }

            return comp;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public Computer update(Computer comp) throws DAOException {
        try (PreparedStatement stmt = DBManager.getConnection().prepareStatement(UPDATE_REQUEST)) {
            stmt.setString(1, comp.getName());
            stmt.setTimestamp(2, DateHelper.localDateToTimestamp(comp.getIntroduced()));
            stmt.setTimestamp(3, DateHelper.localDateToTimestamp(comp.getDiscontinued()));
            stmt.setLong(4, comp.getCompany().getId());
            stmt.setLong(5, comp.getId());

            stmt.executeUpdate();

            return comp;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(Long id) throws DAOException {
        // TODO : Vérifier que l'id existe
        try (PreparedStatement stmt = DBManager.getConnection().prepareStatement(DELETE_REQUEST)) {
            stmt.setLong(1, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
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

        try (PreparedStatement stmt = DBManager.getConnection().prepareStatement(DELETE_LIST + sb.toString())) {
            System.out.println(stmt.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }

    public void deleteByCompanyID(Long companyId) throws DAOException {
        try (PreparedStatement stmt = DBManager.getConnection().prepareStatement(DELETE_BY_COMPANY_ID_REQUEST)) {
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
        sb.append("SELECT * FROM " + TABLE_NAME + " LEFT JOIN " + CompanyDAO.TABLE_NAME + " ON " + COMPANY_ID + " = "
                + CompanyDAO.ID);

        // Search
        if (search != null) {
            sb.append(" WHERE " + NAME + " LIKE ?  OR " + CompanyDAO.NAME + " LIKE ? ");
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
        try (PreparedStatement stmt = DBManager.getConnection().prepareStatement(sb.toString())) {
            int nextParam = 1;

            // Search
            if (search != null) {
                String s = '%' + search + '%';
                stmt.setString(nextParam++, s);
                stmt.setString(nextParam++, s);
            }

            // Limit
            stmt.setInt(nextParam++, pageSize);
            stmt.setInt(nextParam++, begining);

            // Execute request
            ResultSet rs = stmt.executeQuery();

            // Unmap
            ComputerDTOMapper mapper = new ComputerDTOMapper();
            List<ComputerDTO> list = new LinkedList<>();
            while (rs.next()) {
                //System.out.println(unmap(rs));
                list.add(mapper.unmap(unmap(rs)));
            }

            return new Page<>(list, begining / pageSize, pageSize);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public int countSearchResult(String search) {
        String request;
        if (search != null) {
            request = "SELECT COUNT(" + ID + ") FROM " + TABLE_NAME + " LEFT JOIN " + CompanyDAO.TABLE_NAME + " ON "
                    + COMPANY_ID + " = " + CompanyDAO.ID + " WHERE " + NAME + " LIKE ?  OR " + CompanyDAO.NAME
                    + " LIKE ?";
        } else {
            request = "SELECT COUNT(" + ID + ") FROM " + TABLE_NAME;
        }

        try (PreparedStatement stmt = DBManager.getConnection().prepareStatement(request)) {
            if (search != null) {
                String s = '%' + search + '%';
                stmt.setString(1, s);
                stmt.setString(2, s);
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
}
