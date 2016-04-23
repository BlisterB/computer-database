package com.excilys.computer_database.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.excilys.computer_database.database.ConnectionDB;
import com.excilys.computer_database.database.mappers.Mapper;

public abstract class DAO<T> implements Mapper<T, ResultSet> {
	public abstract String getFindRequest();

	public abstract String getFindAllRequest();

	public abstract T create(T obj) throws DAOException;

	public abstract T update(T obj) throws DAOException;

	public abstract void delete(Long id) throws DAOException;

	/** Return the list of <T> associated with the ResultSet rs */
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

	public T find(long id) throws DAOException {
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
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<T> findAll() throws DAOException {
		try (Connection con = ConnectionDB.getConnection()) {
			List<T> list = new ArrayList<>();
			
			try (PreparedStatement stmt = con.prepareStatement(this.getFindAllRequest())) {
				ResultSet rs = stmt.executeQuery();

				// Création de la liste
				while (rs.next()) {
					list.add(unmap(rs));
				}
			}

			return list;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<T> findSome(int begining, int nbPerPage) throws DAOException {
		try (Connection con = ConnectionDB.getConnection()) {
			try (PreparedStatement stmt = con
					.prepareStatement(getFindAllRequest() + " LIMIT " + nbPerPage + " OFFSET " + begining)) {
				System.out.println(stmt.toString());
				ResultSet rs = stmt.executeQuery();

				// Create, fill, and return a list
				return fillList(rs);
			}
		}catch(SQLException e){
			throw new DAOException(e);
		}
	}
}
