package tests;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import ctrl.OrderCtrl;
import db.DBConnection;
import exceptions.DataAccessException;
import model.B2BOrderLine;

class TC3Test {

	
	

	@Test
	void testProductIsNull() throws DataAccessException {
		// Arrange
		DBConnection.getInstance().getConnection();
		OrderCtrl orderCtrl = new OrderCtrl();
		orderCtrl.registerB2BOrder("20-05-2022", 123456789);
		
		// Act
		B2BOrderLine ol = orderCtrl.addPackage("1");
		
		//Assert
		
		DataAccessException thrown = assertThrows(DataAccessException.class, () -> {
			if(ol.getProduct() == null) {
				throw new DataAccessException("Product is null!", new Throwable());
			}
		});
			
		assertEquals("Product is null!", thrown.getMessage());


	}
	
}
