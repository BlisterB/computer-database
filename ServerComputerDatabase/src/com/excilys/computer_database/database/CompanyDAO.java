package com.excilys.computer_database.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import com.excilys.computer_database.model.Company;

public class CompanyDAO extends DAO<Company> {
	public static final String TABLE_NAME = "company";
	private static final String ID = "company.id", NAME = "company.name";

	public CompanyDAO() throws SQLException {
		super();
	}

	@Override
	public Company find(long id) throws SQLException {
		rowset.setCommand("SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = " + id);
		rowset.execute();

		if (rowset.first()) {
			return new Company(id, rowset.getString(NAME));
		}

		return null;
	}

	@Override
	public List<Company> findAll() throws SQLException {
		List<Company> list = new ArrayList<Company>();

		// Exécution de la requête
		rowset.setCommand("SELECT " + ID + "," + NAME + " FROM company");
		rowset.execute();

		// Création de la liste
		while (rowset.next()) {
			list.add(new Company(rowset.getLong(ID), rowset.getString(NAME)));
		}

		return list;
	}

	@Override
	public Company create(Company obj) throws SQLException {
		// Exécution de la requête
		// TODO : préciser quelle table au rowset
		rowset.moveToInsertRow();
		rowset.updateString(NAME, obj.getName());
		rowset.insertRow();
		
		// Récupération de l'id et ajout à obj
		obj.setId(new Long(rowset.getRow()));

		return obj;
	}

	@Override
	public Company update(Company obj) throws SQLException {
		rowset.setCommand("UPDATE langage SET " + NAME + " = " + obj.getName() +
                    	" WHERE ID = " + obj.getId());
		rowset.execute();
		
		return null;
	}

	@Override
	public void delete(Company obj) throws SQLException {
		// TODO Auto-generated method stub

	}

}
