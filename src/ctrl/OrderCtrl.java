package ctrl;

import java.sql.SQLException;

import db.OrderDB;
import db.OrderDBIF;
import exceptions.DataAccessException;
import model.B2BCustomer;
import model.B2BOrder;
import model.B2BOrderLine;
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
public class OrderCtrl {

	private ProductCtrl productCtrl;
	private CustomerCtrl customerCtrl;
	private B2BOrder o;
	private OrderDBIF orderDB;

	/**
	 * Constructoren til klassen, som instantiere nye objekter af, ProductCtrl
	 * klassen, CustomerCtrl klassen og OrderDB klassen
	 * 
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud fra
	 *                             databasen.
	 */
	public OrderCtrl() throws DataAccessException {
		productCtrl = new ProductCtrl();
		customerCtrl = new CustomerCtrl();
		orderDB = new OrderDB();
	}

	/**
	 * Metoden går ned i databasen og finder en medarbejders login ud fra deres
	 * giftNo. <br>
	 * Så kigger den på om der er tilknyttet en ordreline til den kode eller der
	 * ikke er.<br>
	 * Hvis der er et orderLineId på koden så er koden brugt og kan ikke bruges
	 * igen.<br>
	 * Hvis der ikke er en orderLineID på så kan koden bruges til at vælge en gave.
	 * <p>
	 * Hvis ikke koden er blevet brugt før finder den ordren koden er
	 * tilknyttet.<br>
	 * 
	 * Hvis koden er brugt før findes der ikke noget.
	 * 
	 * @param giftNo er gavekoden medarbejderen har fået sendt på email.
	 * @return Et ordre som den indtastet gavekode et tilknyttet eller hvis koden er
	 *         brugt før returneres <code>NULL</code>
	 * @throws DataAccessException kastes hvis den ikke kan trække data ud fra
	 *                             databasen
	 * @throws SQLException        smides hvis der er fejl med databasen.
	 */
	public B2BOrder registerB2BOrderChoice(String giftNo) throws DataAccessException {
		int orderLineId = orderDB.findLoginByGiftNo(giftNo);
		if (orderLineId == 0) {
			o = orderDB.findOrderBylogin(giftNo);
			return o;
		}
		return null;
	}

	/**
	 * Metoden opretter en ny ordre til en B2B kunde.
	 * 
	 * @param endDate er hvilken dato ordren er gyldig til.
	 * @param cvr     er cvr nummeret på B2B kundens firma.
	 * @return En tom ordre, med en slutdato og en B2B Kunde.
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud af
	 *                             databasen.
	 */
	public B2BOrder registerB2BOrder(String endDate, int cvr) throws DataAccessException {
		B2BCustomer c = customerCtrl.findB2BCustomer(cvr);
		o = new B2BOrder(endDate, c);
		return o;
	}

	/**
	 * Tilføjer en pakke til en ordre. <br>
	 * Metoden checker for om pakken man gerne vil tilføje allerede ligger på
	 * ordren. <br>
	 * Hvis den gør det skal der ikke ske noget. Hvis den ikke gør tilføjes pakken
	 * til ordren.
	 * 
	 * @param barcode er stregkoden på pakken man gerne vil tilføje til ordren.
	 * @return returnere den ordre linje man lige har lagt på hvis den ikke allerede
	 *         lå på ordren.
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud af
	 *                             databasen.
	 */
	public B2BOrderLine addPackage(String barcode) throws DataAccessException {
		boolean productAlreadyExists = false;
		Pack p = productCtrl.findPack(barcode);
		for (B2BOrderLine ol : o.getOrderLines()) {
			if (ol.getProduct().getBarcode().equals(p.getBarcode())) {
				productAlreadyExists = true;
			}
		}
		if (!productAlreadyExists) {
			B2BOrderLine ol = new B2BOrderLine(p);
			o.addOrderLine(ol);
			return ol;
		} else {
			return null;
		}
	}

	/**
	 * Metoden tilføjer en B2B Kundes medarbejder til ordren ud fra medarbejderens
	 * email.
	 * 
	 * @param email er emailen på B2B Kundens medarbejder.
	 * @return en boolean for at se om den er tilføjet eller ej.
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud af
	 *                             databasen.
	 */
	public boolean addB2BLogin(String email) throws DataAccessException {
		return o.addB2BLogin(email);
	}

	/**
	 * Metoden gemmer ordren til databasen hvis der er mindst 1 ordre linje og
	 * mindst 1 email på ordren.
	 * 
	 * @return ordren hvis den kunne gemmes til database, ellers bliver der
	 *         retuneret <code>NULL</code>
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud af
	 *                             databasen.
	 */
	public B2BOrder endOrder() throws DataAccessException {
		if (o.getOrderLines().size() > 0 && o.getEmailGiftNo().size() > 0) {
			orderDB.saveOrderToDB(o);
		} else {
			return null;
		}
		return o;
	}

	public B2BOrder getOrder() {
		return this.o;
	}

	/**
	 * Metoden bruges til at vælge en pakke som ligger på ordren ud fra dens
	 * stregkode.
	 * 
	 * @param barcode er stregkoden på pakken man vælger.
	 */
	public void choosePack(String barcode) {
		o.choosePack(barcode);
	}

	/**
	 * Metoden gemmer valget af pakke B2B kundens medarbejder har lavet.<br>
	 * Metoden henter først OrdreId fra ordren vi er på. <br>
	 * Så går den igennem alle ordre linjerne på ordren og kigger efter en stregkode
	 * der er det samme som det B2B kundens medarbejder har valgt. Hvis der er to
	 * der passer sammen gemmer den stregkoden og finder et produkt id ud fra
	 * stregkoden.<br>
	 * Til sidst gemmer den valgtet og opdaterer lager beholdning og antal på
	 * ordren.
	 * 
	 * @param giftNo       er B2B kundens medarbejders gavekode
	 * @param barcodeCheck er stregkoden på pakken B2B Kundens medarbejder har
	 *                     valgt.
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud fra
	 *                             databasen.
	 * @throws SQLException        kastes hvis der er fejl med databasen.
	 */
	public void saveChoice(String giftNo, String barcodeCheck) throws DataAccessException {
		String barcode = "";
		int productId = -1;
		int orderId = o.getOrderId();
		for (int i = 0; i < this.o.getOrderLines().size(); i++) {
			if (this.o.getOrderLines().get(i).getProduct().getBarcode().equals(barcodeCheck)) {
				barcode = barcodeCheck;
				productId = productCtrl.findProductIdByBarcode(barcode);
			}
		}
		orderDB.saveChoice(orderId, productId, giftNo, o.getOrderLines());
//		productCtrl.updateStock(productId);
	}
	/**
	 * Metoden trækker orderlines ud på en given ordre for kunne opdatere ordren i real tid.
	 * @param currOrder er den ordre der kigges på, på det givne tidspunkt.
	 * @return En ordre for at den kan vises i GUI
	 * @throws SQLException kastes hvis der er fejl med databasen.
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud fra databasen.
	 */
	public void pullOrderLines(B2BOrder currOrder) throws  DataAccessException {
		orderDB.pullOrderLines(currOrder);
	}

}
