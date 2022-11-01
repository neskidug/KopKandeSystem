package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ctrl.OrderCtrl;
import exceptions.DataAccessException;
import model.B2BOrder;

class TC4 {
	private B2BOrder currOrder;


	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void testEmailAlreadyOnOrder() throws DataAccessException, SQLException {
		currOrder = null;
		OrderCtrl orderCtrl = new OrderCtrl();
		orderCtrl.registerB2BOrder("20-05-2022", 123456789);
		orderCtrl.addPackage("P1234");
		orderCtrl.addB2BLogin("Gudiksen@gmail.com");
		orderCtrl.addB2BLogin("Gudiksen@gmail.com");
		currOrder = orderCtrl.endOrder();
		assertEquals(1,currOrder.getEmailGiftNo().size());
	}
}
