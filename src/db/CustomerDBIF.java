package db;

import exceptions.DataAccessException;
import model.B2BCustomer;
/**
 * 
 * @authors Rasmus Gudiksen, Jakob Kjeldsteen, Emil Tolstrup Petersen, Christian
 *          Funder og Mark Drongesen
 * 
 *          <p>
 *          Dette interface har alle metoderne som CustomerDB SKAL have.
 *
 */
public interface CustomerDBIF {
	/**
	 * Metoden finder en kunde ud fra et CVR nummer.
	 * @param cvr er CVRnummeret på kunden man ønsker at finde.
	 * @return den kunde der bliver fundet.
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud fra databasen.
	 */
	public B2BCustomer findB2BCustomer(int cvr) throws DataAccessException;
	
	/**
	 * Metoden finder en kunde ud fra deres kunde id.
	 * @param int er id'et på kunden man ønsker at finde.
	 * @return den kunde der bliver fundet.
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud fra databasen.
	 */
	public B2BCustomer findB2BCustomerByID(int id) throws DataAccessException;
}
