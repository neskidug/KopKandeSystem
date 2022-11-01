package model;

/**
 * 
 * @authors Rasmus Gudiksen, Jakob Kjeldsteen, Emil Tolstrup Petersen, Christian
 *          Funder og Mark Drongesen
 *          <p>
 *          Denne klasse er en prisklasse som bruges til at holde styr på pris
 *          og prishistorik på et givent produkt.
 *
 */
public class Price {
	private double price;

	/**
	 * Constructor til at oprette en ny pris.
	 * 
	 * @param price er den pris produket skal have.
	 */
	public Price(double price) {
		this.price = price;
	}

	public double getPrice() {
		return this.price;
	}

}
