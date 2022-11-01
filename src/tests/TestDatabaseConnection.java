package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import db.DBConnection;
import exceptions.DataAccessException;

class TestDatabaseConnection {
	private static Connection dbCon;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	
	}

	@Test
	void testDatabaseConnection() throws DataAccessException, SQLException {
		dbCon = DBConnection.getInstance().getConnection();
		boolean reachable = dbCon.isValid(10);
		assertEquals(true, reachable);
		
	}
	
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		dbCon.close();
	}

	

}
