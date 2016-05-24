package com.excilys.computer_database.database.dao;

import java.util.List;

import com.excilys.computer_database.entity.Company;

public interface CompanyDAO {
    /**
     * Create the object T in the DB.
     * @param obj The object to create in the DB
     * @return The object
     * @throws DAOException If an error occurs
     */
    public abstract Company create(Company obj) throws DAOException;

    /**
     * Find and return the object of id "id" in the DB.
     * @param id The id
     * @return The object of id id
     * @throws DAOException If an SQLException is thrown
     */
    public Company find(long id) throws DAOException;

    /**
     * Return a list containing all the objects of T in the DB.
     * @return A list containing all the objects of T in the DB
     * @throws DAOException If an SQLException is thrown
     */
    public List<Company> findAll() throws DAOException;
    /**
     * Return a list of object T from begining in the DB.
     * @param begining Begining in the DB
     * @param nbPerPage Number of object per page
     * @return The list
     * @throws DAOException if an error occurs
     */
    public List<Company> findSome(int begining, int nbPerPage, String orderBy) throws DAOException;

    /**
     * Update the object in the database.
     * @param obj The object to update
     * @return The object
     * @throws DAOException If an exception is thrown
     */
    public abstract Company update(Company obj) throws DAOException;

    /**
     * Delete the object in the database.
     * @param id The id
     * @throws DAOException If an exception is thrown
     */
    public abstract void delete(Long id) throws DAOException;
}
