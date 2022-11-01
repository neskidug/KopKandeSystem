package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import exceptions.DataAccessException;
import model.B2BOrder;
import model.B2BOrderLine;
/**
 * 
 * @authors Rasmus Gudiksen, Jakob Kjeldsteen, Emil Tolstrup Petersen, Christian
 *          Funder og Mark Drongesen
 * 
 *          <p>
 *       	Dette klasse styre alt kontakt med databasen omkring ordre.
 *
 */
public class OrderDB implements OrderDBIF {
	private CustomerDBIF customerDB;
	private ProductDBIF productDB;
	private HashMap<String, String> emailGiftNo;
	private static final String INSERT_INTO_ORDERLINE_Q = "insert into kk_OrderLines (orderID, productID, quantity, type) values (?, ?, ?, ?)";
	private PreparedStatement insertOrderLinePS;
	private static final String INSERT_INTO_ORDER_Q = "insert into kk_Orders (type, orderNo, customerID, employeeID, enddate) values (?, ?, ?, ?, ?)";
	private PreparedStatement insertOrderPS;
	private static final String INSERT_INTO_B2BLOGIN_Q = "insert into kk_B2BLogin (giftNO, email, orderID) values (?, ?, ?)";
	private PreparedStatement insertB2bLoginPS;
	private static final String FIND_CUSTOMERID_Q = "select * from kk_B2BCustomer WHERE cvr = ?";
	private PreparedStatement findCustomerIDPS;
	private static final String FIND_PRODUCTID_Q = "select id from kk_AbstractProduct WHERE barcode = ?";
	private PreparedStatement findProductIDPS;
	private static final String FIND_ORDER_BY_LOGIN_Q = "SELECT * FROM kk_Orders INNER JOIN kk_B2BLogin ON kk_orders.id = kk_B2BLogin.orderid and kk_B2BLogin.giftNo = ?";
	private static PreparedStatement findOrderByLoginPS;
	private static final String FIND_ORDERLINES_BY_ORDERID_Q = "Select * FROM kk_OrderLines WHERE orderID = ?";
	private static PreparedStatement findOrderLinesByOrderIdPS;
	private static final String FIND_ORDERLINES_BY_ORDERID_PRODUCTID_Q = "Select * FROM kk_OrderLines WHERE orderID = ? and productID = ?";
	private static PreparedStatement findOrderLinesByOrderIdProductIdPS;
	private static final String UPDATE_B2BLOGIN_Q = "update kk_B2BLogin set orderLineID = ? where orderID = ? and giftNo = ?";
	private static PreparedStatement updateB2BLoginPS;
	private static final String UPDATE_ORDERLINE_Q = "update kk_OrderLines set quantity += 1 where id = ?";
	private static PreparedStatement updateOrderLinePS;
	private static final String FIND_B2BLOGIN_Q = "select orderlineId from kk_B2BLogin where giftNo = ?";
	private static PreparedStatement findB2BLoginPS;
	
	/**
	 * Constructoren til klassen instantiere alle prepared statements og andre klasser i klassen.
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud fra databasen.
	 */
	public OrderDB() throws DataAccessException {
		customerDB = new CustomerDB();
		productDB = new ProductDB();
		Connection con = DBConnection.getInstance().getConnection();
		try {
			insertOrderLinePS = con.prepareStatement(INSERT_INTO_ORDERLINE_Q);
			insertOrderPS = con.prepareStatement(INSERT_INTO_ORDER_Q, PreparedStatement.RETURN_GENERATED_KEYS);
			findCustomerIDPS = con.prepareStatement(FIND_CUSTOMERID_Q);
			findProductIDPS = con.prepareStatement(FIND_PRODUCTID_Q);
			insertB2bLoginPS = con.prepareStatement(INSERT_INTO_B2BLOGIN_Q);
			findOrderByLoginPS = con.prepareStatement(FIND_ORDER_BY_LOGIN_Q);
			findOrderLinesByOrderIdPS = con.prepareStatement(FIND_ORDERLINES_BY_ORDERID_Q);
			findOrderLinesByOrderIdProductIdPS = con.prepareStatement(FIND_ORDERLINES_BY_ORDERID_PRODUCTID_Q);
			updateB2BLoginPS = con.prepareStatement(UPDATE_B2BLOGIN_Q);
			updateOrderLinePS = con.prepareStatement(UPDATE_ORDERLINE_Q);
			findB2BLoginPS = con.prepareStatement(FIND_B2BLOGIN_Q);
			} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}

	@Override
	public B2BOrder saveOrderToDB(B2BOrder order) throws DataAccessException {

		int customerID = -1;
		int orderNo = 0;
		int employeeID = 1;
		int productID = -1;
		try {
			DBConnection.getInstance().startTransaction();
			findCustomerIDPS.setInt(1, order.getB2BCustomer().getCVR());
			ResultSet rsCustomer = findCustomerIDPS.executeQuery();
			if (rsCustomer.next()) {
				customerID = rsCustomer.getInt(1);
			}
			// insert into Order
			insertOrderPS.setString(1, "B2B");
			orderNo = order.newOrderNo();
			insertOrderPS.setInt(2, orderNo);
			insertOrderPS.setInt(3, customerID);
			insertOrderPS.setInt(4, employeeID);
			insertOrderPS.setString(5, order.getEndDate());

			// Insert into orderlines
			int orderID = DBConnection.getInstance().executeInsertWithIdentity(insertOrderPS);
			for (int i = 0; i < order.getOrderLines().size(); i++) {
				findProductIDPS.setString(1, order.getOrderLines().get(i).getProduct().getBarcode());
				ResultSet rsProduct = findProductIDPS.executeQuery();
				if (rsProduct.next()) {
					productID = rsProduct.getInt(1);
				}
				insertOrderLinePS.setInt(1, orderID);
				insertOrderLinePS.setInt(2, productID);
				insertOrderLinePS.setInt(3, order.getOrderLines().get(i).getQuantity());
				insertOrderLinePS.setString(4, "pack");
				insertOrderLinePS.executeUpdate();
			}
			// Save b2b login
			for (String login : order.getEmailGiftNo().keySet()) {
				insertB2bLoginPS.setString(1, order.getEmailGiftNo().get(login));
				insertB2bLoginPS.setString(2, login);
				insertB2bLoginPS.setInt(3, orderID);
				insertB2bLoginPS.execute();
			}
			DBConnection.getInstance().commitTransaction();
		} catch (SQLException e) {
//			e.printStackTrace();
			DBConnection.getInstance().rollbackTransaction();
//			throw new DataAccessException(DBMessages.COULD_NOT_BIND_PS_VARS_INSERT, e);
		}
		return order;
	}

	@Override
	public B2BOrder findOrderBylogin(String giftNo) throws DataAccessException {
		B2BOrder currOrder = null;
		try {
			findOrderByLoginPS.setString(1, giftNo);
			ResultSet rs = findOrderByLoginPS.executeQuery();
			if (rs.next()) {
				currOrder = buildOrderObject(rs);
			}
		} catch (SQLException e) {
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
		return currOrder;
	}

	/**
	 * Metoden bygger et ordre objekt ud fra data der kommer fra databasen.
	 * @param rs er det Resultset som bliver trukket ud af databasen.
	 * @return Den ordre der lige er blevet bygget
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud fra databasen.
	 */
	private B2BOrder buildOrderObject(ResultSet rs) throws DataAccessException {
		B2BOrder currOrder = new B2BOrder();
		try {
			currOrder.setOrderId(rs.getInt("id"));
			currOrder.setOrderNo(rs.getInt("orderNo"));
			currOrder.setEndDate(rs.getString("endDate"));
			currOrder.setCustomer(customerDB.findB2BCustomerByID(rs.getInt("customerID")));
			currOrder.setEmailGiftNo(buildEmailGiftObject(rs));
			currOrder = buildOrderLineObject(currOrder, currOrder.getOrderId());

		} catch (SQLException e) {
//			e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return currOrder;
	}

	/**
	 * Metoden bygger et hashMap med email og deres tilknyttet gavekoder som er på en given ordre.
	 * @param rs er resultsettet der kommer fra databasen.	
	 * @return et hashMap med email og gavekoder.
	 * @throws DataAccessException kastes hvis ikke der kan trækkes data ud fra databasen.
	 */
	private HashMap<String, String> buildEmailGiftObject(ResultSet rs) throws DataAccessException {
		emailGiftNo = new HashMap<String, String>();

		try {
				emailGiftNo.put(rs.getString("Email"), rs.getString("GiftNo"));
		} catch (SQLException e) {
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}

		return emailGiftNo;
	}

	/**
	 * Metoden bygger objekter med ordreLinjer.
	 * @param currOrder den ordre som der kigges på.
	 * @param orderID er det orderId på ordren hvis ordrelinjer der skal trækkes ud fra databasen.
	 * @return en ordre med ordrelinjerne.
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud fra databasen.
	 */
	private B2BOrder buildOrderLineObject(B2BOrder currOrder, int orderID) throws DataAccessException {
		ResultSet rs = null;
		try {
			findOrderLinesByOrderIdPS.setInt(1, orderID);
			rs = findOrderLinesByOrderIdPS.executeQuery();
		} catch (SQLException e1) {
//			e1.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e1);
			
		}
		try {
			while (rs.next()) {
				B2BOrderLine currOrderLine = new B2BOrderLine();
				currOrderLine.setProduct(productDB.findByProductId(rs.getInt("productID")));
				currOrderLine.setQuantity(rs.getInt("quantity"));
				currOrder.setOrderLines(currOrderLine);
			}
		} catch (SQLException e) {
//				e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return currOrder;
	}

	@Override
	public void saveChoice(int orderId, int productId, String giftNo, List<B2BOrderLine> orderLines) throws DataAccessException {
		int orderLineId = -1;
		DBConnection.getInstance().startTransaction();
		try {
			findOrderLinesByOrderIdProductIdPS.setInt(1, orderId);
			findOrderLinesByOrderIdProductIdPS.setInt(2, productId);
			ResultSet rs = findOrderLinesByOrderIdProductIdPS.executeQuery();

			
			try {
				if(rs.next()) {
					orderLineId = rs.getInt("ID");
				}
			} catch (SQLException e) {
//				e.printStackTrace();
				throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
			}
			updateB2BLoginPS.setInt(1, orderLineId);
			updateB2BLoginPS.setInt(2, orderId);
			updateB2BLoginPS.setString(3, giftNo);
			updateB2BLoginPS.executeUpdate();
			updateOrderLinePS.setInt(1, orderLineId);
			updateOrderLinePS.executeUpdate();
			DBConnection.getInstance().commitTransaction();
		} catch (SQLException e1) {
			DBConnection.getInstance().rollbackTransaction();
			throw new DataAccessException(DBMessages.COULD_NOT_INSERT, e1);
//			e1.printStackTrace();
		}
		
		
	}

	@Override
	public int findLoginByGiftNo(String giftNo) throws DataAccessException {
		int orderLineId = -1;
		try {
			findB2BLoginPS.setString(1, giftNo);
			ResultSet rs = findB2BLoginPS.executeQuery();
			
			if(rs.next()) {
				orderLineId = rs.getInt("orderlineId");
			}
		} catch (SQLException e) {
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		
		
		return orderLineId;
	}

	@Override
	public B2BOrder pullOrderLines(B2BOrder currOrder) throws DataAccessException {
		ResultSet rs = null;
		try {
			findOrderLinesByOrderIdPS.setInt(1, currOrder.getOrderId());
			rs = findOrderLinesByOrderIdPS.executeQuery();
			currOrder.getOrderLines().clear();
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e1);
		}
		try {
			while (rs.next()) {
				B2BOrderLine currOrderLine = new B2BOrderLine();
				currOrderLine.setProduct(productDB.findByProductId(rs.getInt("productID")));
				currOrderLine.setQuantity(rs.getInt("quantity"));
				currOrder.setOrderLines(currOrderLine);
			}
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return currOrder;
	}
}
