package model;

/**
 * 
 * @authors Rasmus Gudiksen, Jakob Kjeldsteen, Emil Tolstrup Petersen, Christian
 *          Funder og Mark Drongesen
 * 
 *          <p>
 *          Denne klasse er til at oprette en pakke som nedarver AbstractProduct
 *
 */
public class Pack extends AbstractProduct {

	/**
	 * Constructoren til Pack Objektet.
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
	public Pack(String name, String barcode, String productDescription, int stock, double price) {
		super(name, barcode, productDescription, stock, price);
	}

	/**
	 * En tom constructor til at bygge et Pack objekt når vi trækker information ud
	 * fra databasen
	 */
	public Pack() {
		super();
	}

	/**
	 * En ikke færdiggjort metode.
	 * <p>
	 * Metoden skal kunne opdatere stock på alle produkter i en pack.
	 */
	@Override
	public void addStock(int quantity) {

	}

	/**
	 * En ikke færdiggjort metode.
	 * <p>
	 * Metoden skal kunne opdatere stock på alle produkter i en pack.
	 */
	@Override
	public void removeStock(int quantity) {

	}
}
