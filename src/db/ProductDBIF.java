package db;

import exceptions.DataAccessException;
import model.Pack;
/**
 * 
 * @authors Rasmus Gudiksen, Jakob Kjeldsteen, Emil Tolstrup Petersen, Christian
 *          Funder og Mark Drongesen
 * 
 *          <p>
 *          Dette interface har alle metoderne ProductDB SKAL have.
 *
 */
public interface ProductDBIF {
	/**
	 * Metoden finder en pakke ud fra en stregkode.
	 * @param barcode er stregkoden på pakken man gerne vil finde.
	 * @return den pakke der bliver fundet.
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud fra databasen.
	 */
	public Pack findByProductBarcode(String barcode) throws DataAccessException;
	
	/**
	 * Metoden finder en pakke ud fra et productID.
	 * @param id er det productID som pakken har.
	 * @return den pakke der bliver fundet.
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud fra databasen.
	 */
	public Pack findByProductId(int id) throws DataAccessException;
	
	/**
	 * Metoden finder ProductID ud fra en stregkoden.
	 * @param barcode er stregkoden på produktet man gerne vil finde ProductID ud fra.
	 * @return en int som er ProductID.
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud fra databasen.
	 */
	public int findProductIdByBarcode(String barcode) throws DataAccessException;
	
	/**
	 * Metoden tager en stregkode og updatere lagerbeholdning ud fra et valg taget i GUIen.
	 * @param barcode er stregkoden på produktet.
	 * @throws DataAccessException kastes hvis der er fejl med databasen.
	 */
	public void updateStockByBarcode(String barcode) throws DataAccessException ;
}
