package db;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.DataAccessException;
import model.Pack;
import model.Price;

/**
 * 
 * @authors Rasmus Gudiksen, Jakob Kjeldsteen, Emil Tolstrup Petersen, Christian
 *          Funder og Mark Drongesen
 * 
 *          <p>
 *          Denne klasse styrer alt kontakt med databasen omkring produkter.
 *
 */
public class ProductDB implements ProductDBIF {
	private static final String FIND_BY_BARCODE_Q = "SELECT * FROM kk_AbstractProduct WHERE barcode = ? AND type = 'Pack'";
	private static PreparedStatement findByBarcodePS;
	private static final String FIND_PRICEHISTORY_BY_PRODUCTID_Q = "SELECT TOP 1 * FROM kk_Pricehistory WHERE productID = ? ORDER BY convert (dateTime, historyDate, 103) desc";
	private static PreparedStatement findPriceHistoryPS;
	private static final String FIND_BY_PRODUCTID_Q = "SELECT * FROM kk_AbstractProduct WHERE id = ?";
	private static PreparedStatement findByProductIDPS;
	private static final String FIND_PRODUCTID_BY_BARCODE_Q = "select id from kk_AbstractProduct where barcode = ?";
	private static PreparedStatement findProductIdByBarcodePS;
	private static final String UPDATE_STOCK_BY_BARCODE_Q = "update kk_AbstractProduct set stock -= 1 where barcode = ?";
	private static PreparedStatement updateStockByBarcodePS;
	
	/**
	 * Constructoren til klassen instantiere alle prepared statements i klassen.
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud fra databasen.
	 */
	public ProductDB() throws DataAccessException {
		Connection con = DBConnection.getInstance().getConnection();
		try {
			findByBarcodePS = con.prepareStatement(FIND_BY_BARCODE_Q);
			findPriceHistoryPS = con.prepareStatement(FIND_PRICEHISTORY_BY_PRODUCTID_Q);
			findByProductIDPS = con.prepareStatement(FIND_BY_PRODUCTID_Q);
			findProductIdByBarcodePS = con.prepareStatement(FIND_PRODUCTID_BY_BARCODE_Q);
			updateStockByBarcodePS = con.prepareStatement(UPDATE_STOCK_BY_BARCODE_Q);
		} catch (SQLException e) {
//			e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}
	
	@Override
	public int findProductIdByBarcode(String barcode) throws DataAccessException {
		int productId = -1;
		try {
			findProductIdByBarcodePS.setString(1, barcode);
			ResultSet rs = findProductIdByBarcodePS.executeQuery();
			if (rs.next()) {
				productId = rs.getInt("id");
			}
		} catch (SQLException e) {
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
//			e.printStackTrace();
		}
		return productId;
	}

	@Override
	public Pack findByProductBarcode(String barcode) throws DataAccessException {
		Pack currPack = null;
		try {
			findByBarcodePS.setString(1, barcode);
			ResultSet rs = findByBarcodePS.executeQuery();
			if (rs.next()) {
				currPack = buildPackObject(rs);
			}
		} catch (SQLException e) {
//			e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
		return currPack;
	}

	/**
	 * Metoden bygger et objekt af en Pack med de informationer der bliver trukket ud af databasen.
	 * @param rs er Resultsettet som bliver trukket ud af databasen.
	 * @return den pakke der er blevet bygget.
	 * @throws DataAccessException kastes hvis der ikke kan trækkes data ud fra databasen.
	 */
	private Pack buildPackObject(ResultSet rs) throws DataAccessException {
		Pack currPack = new Pack();
		try {
			currPack.setName(rs.getString("productName"));
			currPack.setBarcode(rs.getString("barcode"));
			currPack.setProductDescription(rs.getString("productdescription"));
			currPack.setStock(rs.getInt("stock"));
			findPriceHistoryPS.setInt(1, rs.getInt("id"));
			ResultSet res = findPriceHistoryPS.executeQuery();
			if (res.next()) {
				String p = res.getString("price");
				Price price = new Price(Double.parseDouble(p));
				currPack.setPrice(price);
			}
		} catch (SQLException e) {
//			e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return currPack;
	}

	@Override
	public Pack findByProductId(int id) throws DataAccessException {
		Pack currPack = null;
		try {
			findByProductIDPS.setInt(1, id);
			ResultSet rs = findByProductIDPS.executeQuery();
			if (rs.next()) {
				currPack = buildPackObject(rs);
			}
		} catch (SQLException e) {
//			e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
		return currPack;
	}

	@Override
	public void updateStockByBarcode(String barcode) throws DataAccessException  {
		try {
			updateStockByBarcodePS.setString(1, barcode);
			updateStockByBarcodePS.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
//			e.printStackTrace();
		}
		
	}

}
