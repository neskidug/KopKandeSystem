package db;

import exceptions.DataAccessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.B2BCustomer;

/**
 * 
 * @authors Rasmus Gudiksen, Jakob Kjeldsteen, Emil Tolstrup Petersen, Christian
 *          Funder og Mark Drongesen
 * 
 *          <p>
 *          Denne klasse styre alt kontakt med databasen omkring kunder.
 *
 */
public class CustomerDB implements CustomerDBIF {
	
	private static final String FIND_CUSTOMER_BY_CVR_Q = "SELECT * FROM kk_B2BCustomer WHERE cvr = ?";
	private static PreparedStatement findCustomer;
	private static final String FIND_CUSTOMER_BY_ID_Q = "SELECT * FROM kk_B2BCustomer WHERE id = ?";
	private static PreparedStatement findCustomerById;
	
	/**
	 * Constructoren til klassen instantiere alle prepared statements i klassen.
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud fra databasen.
	 */
	public CustomerDB() throws DataAccessException {
		try {
			findCustomer = DBConnection.getInstance().getConnection().prepareStatement(FIND_CUSTOMER_BY_CVR_Q);
			findCustomerById = DBConnection.getInstance().getConnection().prepareStatement(FIND_CUSTOMER_BY_ID_Q);
		} catch (SQLException e) {
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
//			e.printStackTrace();
		} 
	}
	
	
	@Override
	public B2BCustomer findB2BCustomer(int cvr) throws exceptions.DataAccessException {
		B2BCustomer currCustomer = null;
		try {
			findCustomer.setInt(1, cvr);
			ResultSet rs = findCustomer.executeQuery();
			if(rs.next()) {
			currCustomer = buildObject(rs);
			}
		} catch (SQLException e) {
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
//			e.printStackTrace();
		}
		return currCustomer;
	}
	
	@Override
	public B2BCustomer findB2BCustomerByID(int id) throws DataAccessException {
		B2BCustomer currCustomer = null;
		try {
			findCustomerById.setInt(1, id);
			ResultSet rs = findCustomerById.executeQuery();
			if(rs.next()) {
			currCustomer = buildObject(rs);
			}
		} catch (SQLException e) {
//			e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);		
		}
		return currCustomer;
	}
	
	
	/**
	 * Metoden bygger et objekt af en B2B kunde med de informationer der bliver trukket ud af databasen.
	 * @param rs er Resultsettet som bliver trukket ud af databasen.
	 * @return den B2B kunde der er blevet bygget.
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud fra databasen.
	 */
	private B2BCustomer buildObject(ResultSet rs) throws DataAccessException {
		B2BCustomer currCustomer = new B2BCustomer();
		try {
				currCustomer.setCvr(rs.getInt("cvr"));
				currCustomer.setCompanyName(rs.getString("companyName"));
		
//				currCustomer.setName(rs.getString("name"));
//				currCustomer.setAddress(rs.getString("address"));
//				currCustomer.setZipCode(rs.getInt("zipcode"));
//				currCustomer.setPhoneNo(rs.getInt("phoneno"));
		} catch (SQLException e) {
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
//			e.printStackTrace();
		}
		return currCustomer;
	}
}
