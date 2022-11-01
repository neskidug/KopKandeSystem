package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 
 * @authors Rasmus Gudiksen, Jakob Kjeldsteen, Emil Tolstrup Petersen, Christian
 *          Funder og Mark Drongesen
 * 
 *          <p>
 *          Denne klasse er til at oprette en Ordre for en B2B kunde.
 *
 */
public class B2BOrder {
	private LocalDate endDate;
	private String dateText;
	private List<B2BOrderLine> orderLines;
	private B2BCustomer c;
	private HashMap<String, String> emailGiftNo;
	private int orderNo;
	private int orderId;

	/**
	 * Constructoren til B2BOrder som skal bruge en slutdato, for hvornår ordren er
	 * gyldig til og en kunde som er firmaet.
	 * 
	 * @param endDateString er en dato hvor ordren er gyldig til.
	 * @param c             er en B2BCustomer som skal være tilknyttet ordren.
	 */
	public B2BOrder(String endDateString, B2BCustomer c) {
		this.c = c;
		orderLines = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		this.endDate = LocalDate.parse(endDateString, formatter);
		dateText = endDate.format(formatter);
		emailGiftNo = new HashMap<String, String>();
	}

	/**
	 * En constructor som opretter en <code>ArrayList</code> og et
	 * <code>HashMap</code>. Constructoren skal bruges til byggelse af et order
	 * objekt som bliver trukket ud fra databasen.
	 */
	public B2BOrder() {
		orderLines = new ArrayList<>();
		emailGiftNo = new HashMap<String, String>();
	}

	/**
	 * En metode der bruges til at ligge ordre linjer ned i en
	 * <code>ArrayList</code>.
	 * 
	 * @param ol er den ordrelinje, som skal tilføjes til ordren.
	 * @return den ordrelinje, som er tilføjet til ordren.
	 */
	public B2BOrderLine addOrderLine(B2BOrderLine ol) {
		orderLines.add(ol);
		return ol;
	}

	public String getEndDate() {
		return dateText;
	}

	public List<B2BOrderLine> getOrderLines() {
		return orderLines;
	}

	public B2BCustomer getB2BCustomer() {
		return c;
	}

	public int getOrderNo() {
		return this.orderNo;
	}

	public int getOrderId() {
		return this.orderId;
	}
	/**
	 * Metoden tilføjer et B2BLogin til et hashMap. <p>
	 * Der bliver taget en email ind som parameter og så tjekker metoden emailen igennem for at se om emailen allerede ligger i hashmappet.
	 * Hvis ikke den gør det oprettes der en gavekode til som kommer til at ligge som value til emailen i hashmappet.
	 * @param email er emailen på B2B kundens medarbejder.
	 * @return En boolean for at se om den er blevet tilføjet eller ej.
	 */
	public boolean addB2BLogin(String email) {
		boolean res = false;
		if (email != "") {
			if (getEmailGiftNo().size() > 0) {
				for (Map.Entry<String, String> entry : emailGiftNo.entrySet()) {
					if (!entry.getKey().equals(email)) {
						res = true;
					} else {
						return false;
					}
				}
			} else {
				emailGiftNo.put(email, B2BLogin.createGiftNo());
				return true;
			}
			//Står her fordi vi ellers ændrer i HashMap mens vi itererer gennem den.
			if (res) {
				emailGiftNo.put(email, B2BLogin.createGiftNo());
			}
		}
		return res;
	}

	public void setEndDate(String endDateString) {
		endDate = LocalDate.parse(endDateString, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}

	public void setOrderLines(B2BOrderLine orderLine) {
		orderLines.add(orderLine);
	}

	public void setCustomer(B2BCustomer c) {
		this.c = c;
	}

	public void setEmailGiftNo(HashMap<String,String> emailGiftNo) {
		this.emailGiftNo = emailGiftNo;
	}
	/**
	 * Metoden laver et random tal og dette er ordrenummeret ordren får.
	 * @return Et random tal mellem 0 og 1.000.000
	 */
	public int newOrderNo() {
		orderNo = new Random().nextInt(1000000);
		return orderNo;
	}

	public HashMap<String, String> getEmailGiftNo() {
		return this.emailGiftNo;
	}
	/**
	 * Metoden tager en stregkode som parameter og bruger stregkoden til at se om den stregkode ligger på ordren.<p>
	 * Hvis den gør det stopper while loopet og der bliver tilføjet én til antallet af pakken på ordren.
	 * @param barcode er stregkoden på den pakke B2B kundens medarbejder har valgt.
	 */
	public void choosePack(String barcode) {
		boolean goon = true;
		int i = 0;
		while (goon && i < orderLines.size()) {
			if (orderLines.get(i).getProduct().getBarcode().equals(barcode)) {
				goon = false;
				orderLines.get(i).addQuantity(1);
			}
			i++;
		}
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
}
