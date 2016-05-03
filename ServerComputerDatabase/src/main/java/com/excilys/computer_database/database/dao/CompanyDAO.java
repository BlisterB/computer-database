package com.excilys.computer_database.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.database.ConnectionDB;
import com.excilys.computer_database.entity.Company;

public class CompanyDAO extends DAO<Company> {
    private static volatile CompanyDAO instance = null;

    public static final String TABLE_NAME = "company";
    public static final String ID = "company.id", NAME = "company.name";

    private static final String FIND_REQUEST = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = ?",
            FIND_ALL_REQUEST = "SELECT " + ID + "," + NAME + " FROM company",
            INSERT_REQUEST = "INSERT INTO " + TABLE_NAME + " ( " + NAME + " ) VALUES (?) ",
            UPDATE_REQUEST = "UPDATE " + TABLE_NAME + " SET " + NAME + " = ? WHERE " + ID + " = ?";

    private Logger logger;

    /** Constructor. */
    private CompanyDAO() {
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
    public Company unmap(ResultSet rs) throws DAOException {
        try {
            return new Company(rs.getLong(ID), rs.getString(NAME));
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    /** Return the unique instance of CompanyDAO (singleton pattern).
     *  @return The unique instance of CompanyDAO */
    public static final CompanyDAO getInstance() {
        // TODO : implements a Backoff-lock ? (for the moment : Test&Test&Set)
        if (CompanyDAO.instance == null) {
            synchronized (CompanyDAO.class) {
                if (CompanyDAO.instance == null) {
                    CompanyDAO.instance = new CompanyDAO();
                }
            }
        }
        return CompanyDAO.instance;
    }

    @Override
    public Company create(Company obj) throws DAOException {
        // Exécution de la requête
        try (Connection con = ConnectionDB.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(INSERT_REQUEST, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, obj.getName());

                stmt.executeUpdate();

                // Mise à jour de l'id de l'objet inséré
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.first()) {
                    obj.setId(rs.getLong(1));
                } else {
                    throw new SQLException("L'insertion n'a pas aboutie");
                }

                return obj;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public Company update(Company obj) throws DAOException {
        try (Connection con = ConnectionDB.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(UPDATE_REQUEST)) {
                stmt.setString(1, obj.getName());
                stmt.setLong(2, obj.getId());

                stmt.executeUpdate();
                return obj;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(Long id) throws DAOException {
        // Dete a company implies to delete all the associated computer
        try (Connection con = ConnectionDB.getConnection()) {
            // Begin the transaction
            con.setAutoCommit(false);

            String deleteCompanyRequest = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " = ? ";
            PreparedStatement deleteCompanyStmt = con.prepareStatement(deleteCompanyRequest);
            deleteCompanyStmt.setLong(1, id);

            String deleteComputersRequest = "DELETE FROM " + ComputerDAO.TABLE_NAME + " WHERE " + ComputerDAO.COMPANY_ID + " = ? ";
            PreparedStatement deleteComputersStmt = con.prepareStatement(deleteComputersRequest);
            deleteComputersStmt.setLong(1, id);

            try {
                deleteComputersStmt.executeUpdate();
                deleteCompanyStmt.executeUpdate();

                con.commit();
            } catch (SQLException e){
                e.printStackTrace();
                con.rollback();
            } finally {
                deleteCompanyStmt.close();
                deleteComputersStmt.close();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DAOException(e);
        }
    }
}
