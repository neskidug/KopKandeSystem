package db;

import java.util.List;

import exceptions.DataAccessException;
import model.B2BOrder;
import model.B2BOrderLine;
/**
 * 
 * @authors Rasmus Gudiksen, Jakob Kjeldsteen, Emil Tolstrup Petersen, Christian
 *          Funder og Mark Drongesen
 * 
 *          <p>
 *       	Dette interface har alle metoderne OrderDB SKAL have.
 *
 */
public interface OrderDBIF {
	/**
	 * Metoden gemmer den nuværende ordre til databasen.
	 * @param order den nuværende ordre der laves og eller rettes på.
	 * @return Den ordre der lige er blevet gemt.
	 * @throws DataAccessException kastes hvis der ikke kan puttes ting ind i databasen.
	 */
	public B2BOrder saveOrderToDB(B2BOrder order) throws DataAccessException;

	/**
	 * Metoden finder en ordre ud fra en gavekode.
	 * @param giftNo er gavekoden der er blevet givet til den enkelte medarbejder.
	 * @return Den ordre hvor gavekoden er tilknyttet.
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud af databasen.
	 */
	public B2BOrder findOrderBylogin(String giftNo) throws DataAccessException;

	/**
	 * Metoden gemmer det valg af pakke medarbejderen har taget.
	 * @param orderId er det ID ordren har.
	 * @param productId er det productID på pakken som medarbejderen har valgt.
	 * @param giftNo er gavekoden medarbejderen har opgivet. 
	 * @param orderLines er ordrelinjen der skal opdateres med nyt quantity.
	 * @throws DataAccessException kastes hvis der ikke kan gemmes ting i databasen.
	 */
	public void saveChoice(int orderId, int productId, String giftNo, List<B2BOrderLine> orderLines) throws DataAccessException;
	
	/**
	 * Metoden finder et login ud en gavekode.
	 * @param giftNo er gavekoden som er opgivet til medarbjderen.
	 * @return en int som er det OrderLineID som ligger på loginnet.
	 * @throws DataAccessException kastes hvis der er fejl med databasen.
	 */

	public int findLoginByGiftNo(String giftNo) throws DataAccessException;
	
	/**
	 * Metoden trækker orderlines ud på en given ordre for kunne opdatere ordren i real tid.
	 * @param currOrder er den ordre der kigges på, på det givne tidspunkt.
	 * @return En ordre for at den kan vises i GUI
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud fra databasen.
	 */
	public B2BOrder pullOrderLines(B2BOrder currOrder) throws DataAccessException;
}
