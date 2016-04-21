package com.excilys.computer_database.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computer_database.database.ConnectionDB;
import com.excilys.computer_database.model.Company;

public class CompanyDAO extends DAO<Company> {
	private static volatile CompanyDAO instance = null;

	private static final String TABLE_NAME = "company";
	private static final String ID = "company.id", NAME = "company.name";

	private static final String FIND_REQUEST = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = ?",
			FIND_ALL_REQUEST = "SELECT " + ID + "," + NAME + " FROM company",
			INSERT_REQUEST = "INSERT INTO " + TABLE_NAME + " ( " + NAME + " ) VALUES (?) ",
			UPDATE_REQUEST = "UPDATE " + TABLE_NAME + " SET " + NAME + " = ? WHERE " + ID + " = ?",
			DELETE_REQUEST = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " = ? ";

	private CompanyDAO() {
		super();
	}

	public final static CompanyDAO getInstance() {
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
	public Company unmap(ResultSet rs) throws SQLException {
		return new Company(rs.getLong(ID), rs.getString(NAME));
	}

	@Override
	public ResultSet map(Company obj) throws SQLException {
		// TODO
		return null;
	}

	@Override
	public Company find(long id) throws SQLException {
		try (Connection con = ConnectionDB.getConnection()) {
			try (PreparedStatement stmt = con.prepareStatement(FIND_REQUEST)) {
				stmt.setLong(1, id);

				ResultSet rs = stmt.executeQuery();

				if (rs.first()) {
					return unmap(rs);
				}

				return null;
			}
		}
	}

	@Override
	public List<Company> findAll() throws SQLException {
		List<Company> list = new ArrayList<Company>();

		try (Connection con = ConnectionDB.getConnection()) {
			try (PreparedStatement stmt = con.prepareStatement(FIND_ALL_REQUEST)) {
				ResultSet rs = stmt.executeQuery();

				// Création de la liste
				while (rs.next()) {
					list.add(unmap(rs));
				}
			}
		}

		return list;
	}

	@Override
	public Company create(Company obj) throws SQLException {
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
		}
	}

	@Override
	public Company update(Company obj) throws SQLException {
		try (Connection con = ConnectionDB.getConnection()) {
			try (PreparedStatement stmt = con.prepareStatement(UPDATE_REQUEST)) {
				stmt.setString(1, obj.getName());
				stmt.setLong(2, obj.getId());

				stmt.executeUpdate();
				return obj;
			}
		}
	}

	@Override
	public void delete(Long id) throws SQLException {
		try (Connection con = ConnectionDB.getConnection()) {
			try (PreparedStatement stmt = con.prepareStatement(DELETE_REQUEST)) {
				stmt.setLong(1, id);
				stmt.executeUpdate();
			}
		}
	}
}
