package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.B2BLogin;

class TestGiftNoGenerator {
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@Test
	void testGeneratedNumbersIs20CharsLong() throws InterruptedException {
		List<String> streamNumbers = new ArrayList<String>();
		String generatedNo = "";
		final int N = 100000;
		System.out.println(generatedNo.length());
		long startList = System.currentTimeMillis();
		for(int i = 1; i <= N; i++) {
			generatedNo = B2BLogin.createGiftNo();
			streamNumbers.add(generatedNo);
			System.out.println(i);
		}


		
		long endList = System.currentTimeMillis();
		long listTimeElapsed = endList - startList;
		System.out.println("List running time: " + listTimeElapsed / 1000d);
		
		System.out.println("List created");
		long start = System.currentTimeMillis();
		int rightCodes = streamNumbers.parallelStream()
						.mapToInt((i) -> {
							if(i.length() == 20) {
								return 1;
							}else {
								return 0;
							}
						})
						.reduce(0, (a,b) -> a + b);
		long end = System.currentTimeMillis();
		
		long timeElapsed = end - start;
		System.out.println("Running time: " + timeElapsed / 1000d);
		System.out.println("There is " + rightCodes + " true codes");

		System.out.println("Done!");

		assertEquals(N, rightCodes);
	}

}
