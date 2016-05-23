package com.excilys.computer_database.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import com.excilys.computer_database.database.DBManager;
import com.excilys.computer_database.database.mappers.Mapper;

public abstract class DAO<T> implements Mapper<T, ResultSet> {
    protected DataSource datasource;

    /**
     * Return the string of the request used to find an object in the DB.
     * @return Return the string used to find an object in the DB
     */
    public abstract String getFindRequest();

    /**
     * Return the string of the request used to list all the object of the DB.
     * @return The string of the request used to list all the object of the DB
     */
    public abstract String getFindAllRequest();

    /**
     * Create the object T in the DB.
     * @param obj The object to create in the DB
     * @return The object
     * @throws DAOException If an error occurs
     */
    public abstract T create(T obj) throws DAOException;

    /**
     * Update the object in the database.
     * @param obj The object to update
     * @return The object
     * @throws DAOException If an exception is thrown
     */
    public abstract T update(T obj) throws DAOException;

    /**
     * Delete the object in the database.
     * @param id The id
     * @throws DAOException If an exception is thrown
     */
    public abstract void delete(Long id) throws DAOException;

    /**
     * Return the list of <T> associated with the ResultSet rs.
     * @param rs The resultset to use.
     * @return The list.
     * @throws DAOException If an SQLException is thrown
     */
    public List<T> fillList(ResultSet rs) throws DAOException {
        try {
            // We use LinkedList because of the multiple insertions used
            List<T> list = new LinkedList<>();

            // Création de la liste
            while (rs.next()) {
                list.add(unmap(rs));
            }

            return list;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Find and return the object of id "id" in the DB.
     * @param id The id
     * @return The object of id id
     * @throws DAOException If an SQLException is thrown
     */
    public T find(long id) throws DAOException {
        try (PreparedStatement stmt = DBManager.getConnection().prepareStatement(this.getFindRequest())) {
            // Fill the id field
            stmt.setLong(1, id);

            // Execute the query and return the result
            ResultSet rs = stmt.executeQuery();

            if (rs.first()) {
                return unmap(rs);
            }
            throw new DAOException(id + " not found.");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Return a list containing all the objects of T in the DB.
     * @return A list containing all the objects of T in the DB
     * @throws DAOException If an SQLException is thrown
     */
    public List<T> findAll() throws DAOException {
        List<T> list = new ArrayList<>();

        try (PreparedStatement stmt = DBManager.getConnection().prepareStatement(this.getFindAllRequest())) {
            ResultSet rs = stmt.executeQuery();

            // Création de la liste
            while (rs.next()) {
                list.add(unmap(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return list;
    }

    /**
     * Return a list of object T from begining in the DB.
     * @param begining Begining in the DB
     * @param nbPerPage Number of object per page
     * @return The list
     * @throws DAOException if an error occurs
     */
    public List<T> findSome(int begining, int nbPerPage, String orderBy) throws DAOException {
        String request = getFindAllRequest() + " ORDER BY case when " + orderBy + " is null then 1 else 0 end ,"
                + orderBy + " LIMIT " + nbPerPage + " OFFSET " + begining;

        try (PreparedStatement stmt = DBManager.getConnection().prepareStatement(request)) {
            ResultSet rs = stmt.executeQuery();

            // Create, fill, and return a list
            return fillList(rs);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /** Return the datasource
     * @return the datasource
     */
    public DataSource getDatasource() {
        return datasource;
    }

    /**
     * @param datasource the datasource to set
     */
    public void setDatasource(DataSource datasource) {
        this.datasource = datasource;
    }
}