package com.excilys.computer_database.database.mappers;

import com.excilys.computer_database.database.dao.DAOException;

public interface Mapper<T, R> {
    /** Obtain the object T from a ResultSet.
     * @param rs The resultset
     * @return The object from the resultset
     * @throws DAOException If an error occurs
     */
    T unmap(R rs) throws DAOException;
}