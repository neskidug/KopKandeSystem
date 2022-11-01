package model;

/**
 * 
 * @authors Rasmus Gudiksen, Jakob Kjeldsteen, Emil Tolstrup Petersen, Christian
 *          Funder og Mark Drongesen
 *          <p>
 *          Denne klasse bygger produkter til B2C Kunder. Klassen er lavet til
 *          en del af composite patteren. Klassen nedarver fra AbstractProduct
 *
 */
public class Product extends AbstractProduct {
	private String brand;

	/**
	 * Constructoren til produkt klassen.
	 * 
	 * @param name               er navnet på produktet
	 * @param barcode            er stregkoden produktet skal have som man senere
	 *                           kan finde produktet på.
	 * @param productDescription er produktbeskrivelsen som man kan bruge til at
	 *                           læse en lille beskrivelse af produktet
	 * @param stock              er hvor mange af produktet der er på lager. Denne
	 *                           opdateres senere når produktet bliver købt.
	 * @param price              er hvad produktet skal koste.
	 * @param brand              er hvilket mærke produktet er fra.
	 */
	public Product(String name, String barcode, String productDescription, int stock, double price, String brand) {
		super(name, barcode, productDescription, stock, price);
		this.brand = brand;
	}

	/**
	 * Tilføjer stock til produktet
	 */
	@Override
	public void addStock(int quantity) {
		setStock(getStock() + quantity);

	}

	/**
	 * Fjerner stock fra produktet
	 */
	@Override
	public void removeStock(int quantity) {
		setStock(getStock() - quantity);
	}

}
