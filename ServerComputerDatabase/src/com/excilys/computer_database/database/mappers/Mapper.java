package com.excilys.computer_database.database.mappers;

import com.excilys.computer_database.database.dao.DAOException;

public interface Mapper<T, R> {
	public T unmap(R rs) throws DAOException;
}
