package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import model.B2BOrderLine;

/**
 * 
 * @authors Rasmus Gudiksen, Jakob Kjeldsteen, Emil Tolstrup Petersen, Christian
 *          Funder og Mark Drongesen
 * 
 *          <p>
 *          Denne klasse styrer opsætningen af modellen til venstre side GiftChoiceGUI
 *
 */

public class OrderChoiceTableModel extends DefaultTableModel {

	private static final String[] COLUMN_NAMES = { "Produkt", "Produkt Beskrivelse" };
	private ArrayList<B2BOrderLine> elements;
	/**
	 * Constructoren instantierer en <code>ArrayList<code> og laver et OrderChoiceOrderTableModel objekt
	 */
	public OrderChoiceTableModel() {
		elements = new ArrayList<>();
	}
	/**
	 * Metoden sætter modellens data.
	 * @param data er listen med de orderLines, som skal vises i tabellen.
	 */
	public void setModelData(List<B2BOrderLine> data) {
		elements.clear();
		elements.addAll(data);
		super.fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		int res = 0;
		if (elements != null) {
			res = elements.size();
		}
		return res;
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}

	@Override
	public String getValueAt(int row, int column) {
		B2BOrderLine currList = elements.get(row);
		String res = "UNKNOWN";
		switch (column) {
		case 0:
			res = currList.getProduct().getName();
			break;
		case 1:
			res = currList.getProduct().getProductDescription();
		}
		
		return res;
	}
	/**
	 * Metoden tager elementet på den valgte række og returnerer det, hvis der er et.
	 * @param selectedRow er den valgte række
	 * @return Et objekt af typen B2BOrderLine fra den valgte række.
	 */
	public B2BOrderLine getElementAtIndex(int selectedRow) {
		if (elements.size() < 1) {
			return null;
		}
		return elements.get(selectedRow);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		// all cells false
		return false;
	}
}
