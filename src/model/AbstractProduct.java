package model;

import java.util.ArrayList;

/**
 * 
 * @authors Rasmus Gudiksen, Jakob Kjeldsteen, Emil Tolstrup Petersen, Christian
 *          Funder og Mark Drongesen
 * 
 *          <p>
 *          Denne klasse er til at oprette et AbstractProduct.
 *
 */
public abstract class AbstractProduct {
	private String name;
	private String barcode;
	private String productDescription;
	private int stock;
	private ArrayList<Price> prices;

	/**
	 * Constructoren til at oprette et AbstractProduct.
	 * <p>
	 * Den opretter en ny <code>ArrayList</code> som bruges til at gemme priser på
	 * produktet for at have en prishistorik.
	 * 
	 * @param name               er navnet på produktet.
	 * @param barcode            er stregkoden på produktet.
	 * @param productDescription er en beskrivelse af produktet.
	 * @param stock              er antallet af produktet der er på lageret
	 * @param priceInsert        er den pris et produkt skal have. Denne bruges til
	 *                           at lave et nyt pris objekt.
	 */
	public AbstractProduct(String name, String barcode, String productDescription, int stock, double priceInsert) {
		this.name = name;
		this.barcode = barcode;
		this.productDescription = productDescription;
		this.stock = stock;
		Price price = new Price(priceInsert);
		this.prices = new ArrayList<Price>();
		this.prices.add(price);
	}

	/**
	 * En tom contructor til oprettelse af AbstractProduct objektet med data fra
	 * databasen. Opretter en ny <code>ArrayList</code> til pris historik.
	 */
	public AbstractProduct() {
		this.prices = new ArrayList<Price>();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBarcode() {
		return this.barcode;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getStock() {
		return stock;
	}

	public double getPrice() {
		return prices.get(prices.size() - 1).getPrice();
	}

	public void setPrice(Price price) {
		this.prices.add(price);
	}

	public abstract void addStock(int quantity);

	public abstract void removeStock(int quantity);

}
