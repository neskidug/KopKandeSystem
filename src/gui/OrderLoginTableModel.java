package gui;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @authors Rasmus Gudiksen, Jakob Kjeldsteen, Emil Tolstrup Petersen, Christian
 *          Funder og Mark Drongesen
 * 
 *          <p>
 *          Denne klasse styrer opsætningen af modellen for logins i B2BOrderGUI
 *
 */

public class OrderLoginTableModel extends DefaultTableModel{
	private static final String[] COLUMN_NAMES = { "Email"};
	private ArrayList<String> elements;
	/**
	 * Constructoren instantierer en <code>ArrayList<code> og laver et OrderChoiceOrderTableModel objekt
	 */
	public OrderLoginTableModel() {
		elements = new ArrayList<>();
	}
	/**
	 * Metoden sætter modellens data.
	 * @param data er listen med de strenge, som skal vises i tabellen.
	 */
	public void setModelData(HashMap<String,String> currLogins) {
		elements.clear();
		elements.addAll(currLogins.keySet());
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
	public Object getValueAt(int row, int column) {
		String currList = elements.get(row);
		String res = "UNKNOWN";
		switch (column) {
		case 0:
			res = currList;
			break;
		}
		return res;
	}
	/**
	 * Metoden tager elementet på den valgte række og returnerer det, hvis der er et.
	 * @param selectedRow er den valgte række
	 * @return Et objekt af typen String fra den valgte række.
	 */
	public String getElementAtIndex(int selectedRow) {
		if(elements.size() < 1) {
			return null;
		}
		return elements.get(selectedRow);
	}
	@Override
    public boolean isCellEditable(int row, int column) {
       //all cells false
       return false;
    }
}
