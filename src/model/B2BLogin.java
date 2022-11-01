package model;

import java.nio.charset.Charset;
import java.util.Random;

/**
 * 
 * @authors Rasmus Gudiksen, Jakob Kjeldsteen, Emil Tolstrup Petersen, Christian
 *          Funder og Mark Drongesen
 * 
 *          <p>
 *          Denne klasse er til at oprette en gave kode til medarbejderene i B2B
 *          kundens firma. Koden skal bruges når medarbejderen vil vælge en
 *          pakke degodt kunne tænke sig.
 *
 */
public class B2BLogin {


	/**
	 * Denne metode opretter en tilfældig kode på 20 tegn. Med store og små
	 * bogstaver fra A til Z og fra 0 til 9.
	 * 
	 * @return En gift Code på 20 tegn.
	 */
	public static String createGiftNo() {
		byte[] array = new byte[256];
		new Random().nextBytes(array);
		int maxLength = 20;
		String randomString = new String(array, Charset.forName("UTF-8"));

		StringBuffer buffer = new StringBuffer();

		String alphaNumericString = randomString.replaceAll("[^A-Za-z0-9]", "");
		for (int i = 0; i < alphaNumericString.length(); i++) {

			if (Character.isLetter(alphaNumericString.charAt(i)) && (maxLength > 0)
					|| Character.isDigit(alphaNumericString.charAt(i)) && (maxLength > 0)) {

				buffer.append(alphaNumericString.charAt(i));
				maxLength--;
			}
		}

		return buffer.toString();
	}

}
