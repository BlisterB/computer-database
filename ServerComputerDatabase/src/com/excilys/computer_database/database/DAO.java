package com.excilys.computer_database.database;

import java.sql.SQLException;
import java.util.List;

import javax.sql.RowSet;

import com.sun.rowset.JdbcRowSetImpl;

public abstract class DAO<T> implements AutoCloseable {
	protected RowSet rowset;
	
	public DAO() throws SQLException{
		rowset = new JdbcRowSetImpl(ConnectionDB.getConnection());
	}
	
	@Override
	public void close() throws SQLException {
		rowset.close();
	}
	
	public abstract T find(long id)		throws SQLException;
	public abstract List<T> findAll()	throws SQLException;
	public abstract T create(T obj)		throws SQLException;
	public abstract T update(T obj)		throws SQLException;
	public abstract void delete(T obj)	throws SQLException;
}
