package com.excilys.computer_database.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class DAO<T> implements AutoCloseable {
	protected Connection connect;
	
	public DAO() throws SQLException{
		connect = ConnectionDB.getConnection();
	}
	
	@Override
	public void close() throws SQLException {
		connect.close();
	}
	
	public abstract T find(long id)		throws SQLException;
	public abstract List<T> findAll()	throws SQLException;
	public abstract T create(T obj)		throws SQLException;
	public abstract T update(T obj)		throws SQLException;
	public abstract void delete(T obj)	throws SQLException;
	
	public abstract T unmap(ResultSet rs)		throws SQLException;
	public abstract ResultSet map(T obj)	throws SQLException;
}
