package com.excilys.computer_database.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import com.excilys.computer_database.database.ConnectionDB;
import com.excilys.computer_database.database.mappers.Mapper;
import com.excilys.computer_database.entity.Company;
import com.excilys.computer_database.entity.Computer;

public class ComputerDAO extends DAO<Computer> implements Mapper<Computer, ResultSet> {
	// TODO : study the utility of the "volatile"
	private static volatile ComputerDAO instance = null;

	public static final String TABLE_NAME = "computer";
	private static final String ID = TABLE_NAME + ".id", NAME = TABLE_NAME + ".name",
			COMPANY_ID = TABLE_NAME + ".company_id", INTRODUCED = TABLE_NAME + ".introduced",
			DISCONTINUED = TABLE_NAME + ".discontinued";

	private static final String FIND_REQUEST = "SELECT * FROM " + TABLE_NAME + " LEFT JOIN " + CompanyDAO.TABLE_NAME
			+ " ON " + COMPANY_ID + " = " + CompanyDAO.ID + " WHERE " + ID + " = ?",
			FIND_ALL_REQUEST = "SELECT * FROM " + TABLE_NAME + " LEFT JOIN " + CompanyDAO.TABLE_NAME + " ON "
					+ COMPANY_ID + " = " + CompanyDAO.ID,
			INSERT_FULL_REQUEST = "INSERT INTO " + TABLE_NAME + " ( " + NAME + "," + INTRODUCED + "," + DISCONTINUED
					+ "," + COMPANY_ID + " ) VALUES (?,?,?,?) ",
			UPDATE_REQUEST = "UPDATE " + TABLE_NAME + " SET " + NAME + " = ? , " + INTRODUCED + " = ? , " + DISCONTINUED
					+ " = ? , " + COMPANY_ID + " = ? WHERE " + ID + " = ?",
			DELETE_REQUEST = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " = ? ";

	private ComputerDAO() {
		super();
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
	public Computer unmap(ResultSet rs) throws DAOException {
		// Extract the company
		Company company = (CompanyDAO.getInstance()).unmap(rs);

		// Build the Computer
		try {
			return new Computer.ComputerBuilder(rs.getString(NAME)).id(rs.getLong(ID))
					.introduced(rs.getTimestamp(INTRODUCED)).discontinued(rs.getTimestamp(DISCONTINUED))
					.company(company).build();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public final static ComputerDAO getInstance() {
		if (ComputerDAO.instance == null) {
			synchronized (ComputerDAO.class) {
				if (ComputerDAO.instance == null) {
					ComputerDAO.instance = new ComputerDAO();
				}
			}
		}

		return ComputerDAO.instance;
	}

	@Override
	public Computer create(Computer comp) throws DAOException {
		// Exécution de la requête
		try (Connection con = ConnectionDB.getConnection()) {
			try (PreparedStatement stmt = con.prepareStatement(INSERT_FULL_REQUEST, Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, comp.getName());
				stmt.setTimestamp(2, Timestamp.valueOf(comp.getIntroduced()));
				stmt.setTimestamp(3, Timestamp.valueOf(comp.getDiscontinued()));
				stmt.setLong(4, comp.getCompany().getId());

				stmt.executeUpdate();

				// Mise à jour de l'id de l'objet inséré
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.first()) {
					comp.setId(rs.getLong(1));
				} else {
					throw new SQLException("L'insertion n'a pas aboutie");
				}

				return comp;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public Computer update(Computer comp) throws DAOException {
		try (Connection con = ConnectionDB.getConnection()) {
			try (PreparedStatement stmt = con.prepareStatement(UPDATE_REQUEST)) {
				stmt.setString(1, comp.getName());
				stmt.setTimestamp(2, Timestamp.valueOf(comp.getIntroduced()));
				stmt.setTimestamp(3, Timestamp.valueOf(comp.getDiscontinued()));
				stmt.setLong(4, comp.getCompany().getId());
				stmt.setLong(5, comp.getId());

				stmt.executeUpdate();
				
				return comp;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void delete(Long id) throws DAOException {
		// TODO : Vérifier que l'id existe
		try (Connection con = ConnectionDB.getConnection()) {
			try (PreparedStatement stmt = con.prepareStatement(DELETE_REQUEST)) {
				stmt.setLong(1, id);

				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
}
