package model;

/**
 * 
 * @authors Rasmus Gudiksen, Jakob Kjeldsteen, Emil Tolstrup Petersen, Christian
 *          Funder og Mark Drongesen
 * 
 *          <p>
 *          Denne klasse er til at oprette et firma i systemet med en tilknyttet
 *          kontakt person.
 *
 */
public class B2BCustomer extends Person {
	private String companyName;
	private int cvr;

	/**
	 * Constructoren til oprettelse af firmaet
	 * 
	 * @param name    er navnet på firmaet.
	 * @param address er adressen på firmaet.
	 * @param zipCode er postnummeret adressen ligger i.
	 * @param phoneNo er telefonnummeret til kontaktpersonen i firmaet.
	 * @param email   er emailen til kontaktpersonen i firmaet.
	 * @param city    er byen firmaets adresse ligger i.
	 */
	public B2BCustomer(String name, String address, int zipCode, int phoneNo, String email, String city) {
		super(name, address, zipCode, phoneNo, email, city);
	}

	/**
	 * En tom constructor til byggelse af et objekt med data fra databasen.
	 */
	public B2BCustomer() {
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setCvr(int cvr) {
		this.cvr = cvr;
	}

	public int getCVR() {
		return cvr;
	}

	public String getCompanyName() {
		return companyName;
	}
}
