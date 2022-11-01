package ctrl;

import db.ProductDB;
import db.ProductDBIF;
import exceptions.DataAccessException;
import model.Pack;

/**
 * 
 * @authors Rasmus Gudiksen, Jakob Kjeldsteen, Emil Tolstrup Petersen, Christian
 *          Funder og Mark Drongesen
 * 
 *          <p>
 *          Denne klasse er styre alt der har med ordre at gøre
 *
 */
public class ProductCtrl {

	private ProductDBIF productDB;

	/**
	 * Constructoren til klassen, som instansiere et ProductDB objekt.
	 * 
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud fra
	 *                             databasen.
	 */
	public ProductCtrl() throws DataAccessException {
		this.productDB = new ProductDB();
	}

	/**
	 * Metoden finder en pakke ud fra en stregkode.
	 * 
	 * @param barcode er stregkoden på pakken der skal findes.
	 * @return Pakken der findes ud fra stregkoden.
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud fra
	 *                             databasen.
	 */
	public Pack findPack(String barcode) throws DataAccessException {
		Pack currPack = productDB.findByProductBarcode(barcode);
		return currPack;
	}

	/**
	 * Metoden finder et produkt id ud fra en stregkode.
	 * 
	 * @param barcode er stregkoden på produktet man gerne vil finde product id'et
	 *                på.
	 * @return Produkt id'et som en int.
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud fra
	 *                             databasen.
	 */
	public int findProductIdByBarcode(String barcode) throws DataAccessException {
		return productDB.findProductIdByBarcode(barcode);
	}

	/**
	 * Metoden opdatere lagerbeholdningen på et proukt ud fra en stregkode.
	 * 
	 * @param barcode er stregkoden på produktet der skal have opdateret sin
	 *                lagerbeholdning.
	 * @throws DataAccessException 
	 */
	public void updateStock(String barcode) throws DataAccessException {
		productDB.updateStockByBarcode(barcode);
	}

}
