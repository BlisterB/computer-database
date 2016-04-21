package com.excilys.computer_database.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computer_database.database.ConnectionDB;

public abstract class DAO<T> {
	public abstract String getFindRequest();
	public abstract String getFindAllRequest();
	public abstract T create(T obj)		throws SQLException;
	public abstract T update(T obj)		throws SQLException;
	public abstract void delete(Long id)	throws SQLException;
	
	public abstract T unmap(ResultSet rs)	throws SQLException;
	public abstract ResultSet map(T obj)	throws SQLException;
	
	public List<T> fillList(ResultSet rs) throws SQLException {
		List<T> list = new ArrayList<>();
		
		// Création de la liste
		while (rs.next()) {
			list.add(unmap(rs));
		}
		
		return list;
	}
	
	public T find(long id) throws SQLException {
		try (Connection con = ConnectionDB.getConnection()) {
			try (PreparedStatement stmt = con.prepareStatement(this.getFindRequest())) {
				// Fill the id field
				stmt.setLong(1, id);

				// Execute the query and return the result
				ResultSet rs = stmt.executeQuery();

				if (rs.first()) {
					return unmap(rs);
				}
				throw new SQLException("No such element");
			}
		}
	}
	
	public List<T> findAll()	throws SQLException{
		List<T> list = new ArrayList<>();

		try (Connection con = ConnectionDB.getConnection()) {
			try (PreparedStatement stmt = con.prepareStatement(this.getFindRequest())) {
				ResultSet rs = stmt.executeQuery();

				// Création de la liste
				while (rs.next()) {
					list.add(unmap(rs));
				}
			}
		}

		return list;
	}
	
	public List<T> findSome(int begining, int nbPerPage) throws SQLException {
		try (Connection con = ConnectionDB.getConnection()) {
			try (PreparedStatement stmt = con.prepareStatement(getFindAllRequest() + " LIMIT " + nbPerPage + " OFFSET " + begining)) {
				ResultSet rs = stmt.executeQuery();

				// Create, fill, and return a list
				return fillList(rs);
			}
		}
	}
}
