package model;

/**
 * 
 * @authors Rasmus Gudiksen, Jakob Kjeldsteen, Emil Tolstrup Petersen, Christian
 *          Funder og Mark Drongesen
 * 
 *          <p>
 *          Denne klasse er til at oprette en packLine, den tager et
 *          AbstractProduct og en quantity og laver en linje med en pakke med et
 *          antal på.
 *
 */
public class PackLine {
	private AbstractProduct p;
	private int quantity;

	/**
	 * Constructoren til PackLine
	 * 
	 * @param p        er pakken der tilføjes til PackLine
	 * @param quantity er antallet af pakker der skal på denne PackLine
	 */
	public PackLine(AbstractProduct p, int quantity) {
		this.p = p;
		this.quantity = quantity;
	}

}
