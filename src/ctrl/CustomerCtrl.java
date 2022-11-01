package ctrl;

import db.CustomerDB;
import db.CustomerDBIF;
import exceptions.DataAccessException;
import model.B2BCustomer;
/**
 * 
 * @authors Rasmus Gudiksen, Jakob Kjeldsteen, Emil Tolstrup Petersen, Christian
 *          Funder og Mark Drongesen
 * 
 *          <p>
 *          Denne klasse er styre alt der har med Customers at gøre
 *
 */
public class CustomerCtrl {
	private CustomerDBIF customerDB;
	
	/**
	 * Constructoren til klassen, som instantiere et nyt customerDB objekt.
	 * 
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud fra
	 *                             databasen.
	 */
	public CustomerCtrl() throws DataAccessException {
		customerDB = new CustomerDB();
	}
	/**
	 * Metoden finder en B2B kunde i systemet ud fra deres cvr nummer.
	 * 
	 * @param cvr er det nummer på firmaet man gerne vil finde.
	 * @return Det kunde objekt der er blevet fundet.
	 * @throws DataAccessException kastes hvis ikke der kunne findes nogen kunde ud fra cvr nummret.
	 */
	public B2BCustomer findB2BCustomer(int cvr) throws DataAccessException {
		return customerDB.findB2BCustomer(cvr);
	}
	
	
}
