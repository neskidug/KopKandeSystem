package model;

/**
 * 
 * @authors Rasmus Gudiksen, Jakob Kjeldsteen, Emil Tolstrup Petersen, Christian
 *          Funder og Mark Drongesen
 * 
 *          <p>
 *          Denne klasse er til at oprette en superklasse til alle de personer
 *          vi har i programmet.
 *
 */
public abstract class Person {
	private String name;
	private String address;
	private int zipCode;
	private int phoneNo;
	private String email;
	private String city;

	/**
	 * Constructoren som laver person objektet.
	 * 
	 * @param name    er navnet på personen der skal oprettes.
	 * @param address er adressen på personen der skal oprettes.
	 * @param zipCode er post nummeret på byen, personens adresse ligger i.
	 * @param phoneNo er telefonnummeret på personen der skal oprettes.
	 * @param email   er emailen på personen der skal oprettes.
	 * @param city    er byen personens adresse ligger i.
	 */
	public Person(String name, String address, int zipCode, int phoneNo, String email, String city) {
		this.name = name;
		this.address = address;
		this.zipCode = zipCode;
		this.phoneNo = phoneNo;
		this.email = email;
		this.city = city;

	}

	/**
	 * En tom constructor til når et person objekt skal trækkes ud fra databasen og
	 * bygges.
	 */
	public Person() {

	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public void setPhoneNo(int phoneNo) {
		this.phoneNo = phoneNo;
	}

}
