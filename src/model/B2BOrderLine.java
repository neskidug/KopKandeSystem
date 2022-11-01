package model;

/**
 * 
 * @authors Rasmus Gudiksen, Jakob Kjeldsteen, Emil Tolstrup Petersen, Christian
 *          Funder og Mark Drongesen
 * 
 *          <p>
 *          Denne klasse er til at oprette en OrderLine for B2B kunder.
 *
 */
public class B2BOrderLine {
	private Pack product;
	private int quantity;

	/**
	 * Constructoren til en B2BOrderLine som tager en Pack og sætter antallet til 0,
	 * da dette er til valg af produkter for B2B Kunder som deres medarbejdere skal
	 * kunne vælge udfra.
	 * 
	 * @param product er den pack der som skal ligge på ordre linjen.
	 */
	public B2BOrderLine(Pack product) {
		this.product = product;
		this.quantity = 0;
	}

	/**
	 * En tom constructor til at bygge et objekt når vi trækker data ud fra
	 * databasen.
	 */
	public B2BOrderLine() {

	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Tilføjer et antal til quantity
	 * 
	 * @param amount er antallet der skal tilføjes
	 */
	public void addQuantity(int amount) {
		this.quantity += amount;
	}

	public Pack getProduct() {
		return product;
	}

	public void setProduct(Pack product) {
		this.product = product;
	}
}
