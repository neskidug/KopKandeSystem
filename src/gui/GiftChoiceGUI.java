package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ctrl.OrderCtrl;
import ctrl.ProductCtrl;
import exceptions.DataAccessException;
import model.B2BOrderLine;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JDesktopPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Component;

/**
 * 
 * @authors Rasmus Gudiksen, Jakob Kjeldsteen, Emil Tolstrup Petersen, Christian
 *          Funder og Mark Drongesen
 * 
 *          <p>
 *          Denne klasse styrer interaktionen mellem bruger og valget af gavepakke til ordren.
 *
 */

public class GiftChoiceGUI extends JFrame {
	private static GiftChoiceGUI gui;
	private OrderChoiceOrderTableModel orderChoiceOrderTableModel;
	private OrderChoiceTableModel orderChoiceTableModel;
	private JPanel contentPane;
	private OrderCtrl orderCtrl;
	private JTextField txtOrderNumber;
	private JTextField txtB2BCustomer;
	private JTextField txtGiftCode;
	private JTextField txtEmail;
	private JTable tblChoices;
	private JTable tblB2BOrder;
	private boolean choice;
	private String giftNo;
	private String barcode;
	private ProductCtrl productCtrl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui = new GiftChoiceGUI();
					gui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Construtoren kalder klassens anden constructor og initmetode.
	 * @param orderCtrl					Den orderCtrl som styrer den nuværende ordre
	 * @param giftNo					Det giftNo der er indtastet af brugeren
	 * @throws DataAccessException		kastes hvis ikke ProductDB kan få kontakt til databasen
	 */
	public GiftChoiceGUI(OrderCtrl orderCtrl, String giftNo) throws DataAccessException {
		this();
		init(orderCtrl, giftNo);
	}
	
	/**
	 * Create the frame. 
	 */
	public GiftChoiceGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JDesktopPane desktopPane = new JDesktopPane();
		panel.add(desktopPane, BorderLayout.CENTER);
		desktopPane.setLayout(new BoxLayout(desktopPane, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		desktopPane.add(scrollPane);
		
		tblChoices = new JTable();
		scrollPane.setViewportView(tblChoices);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		desktopPane.add(scrollPane_1);
		
		tblB2BOrder = new JTable();
		scrollPane_1.setViewportView(tblB2BOrder);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{325, 329, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 0, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 0;
		panel_2.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{120, 89, 0};
		gbl_panel_3.rowHeights = new int[]{23, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JButton btnChoice = new JButton("Tilføj");
		btnChoice.addActionListener((e ->  { 
			choiceClicked();
		}));
		GridBagConstraints gbc_btnChoice = new GridBagConstraints();
		gbc_btnChoice.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnChoice.gridx = 1;
		gbc_btnChoice.gridy = 0;
		panel_3.add(btnChoice, gbc_btnChoice);
		
		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 1;
		gbc_panel_4.gridy = 0;
		panel_2.add(panel_4, gbc_panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[]{89, 0};
		gbl_panel_4.rowHeights = new int[]{23, 0};
		gbl_panel_4.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_4.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_4.setLayout(gbl_panel_4);
		JButton btnUpdateOrder = new JButton("Gem");
		btnUpdateOrder.addActionListener((e -> {
				saveChoiceClicked();
		}));
		btnUpdateOrder.setAlignmentX(Component.RIGHT_ALIGNMENT);
		GridBagConstraints gbc_btnUpdateOrder = new GridBagConstraints();
		gbc_btnUpdateOrder.anchor = GridBagConstraints.EAST;
		gbc_btnUpdateOrder.gridx = 0;
		gbc_btnUpdateOrder.gridy = 0;
		panel_4.add(btnUpdateOrder, gbc_btnUpdateOrder);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{19, 91, 258, 0, 0, 46, 0};
		gbl_panel_1.rowHeights = new int[]{0, 14, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblOrderNumber = new JLabel("Ordrenummer");
		GridBagConstraints gbc_lblOrderNumber = new GridBagConstraints();
		gbc_lblOrderNumber.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblOrderNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblOrderNumber.gridx = 1;
		gbc_lblOrderNumber.gridy = 0;
		panel_1.add(lblOrderNumber, gbc_lblOrderNumber);
		
		txtOrderNumber = new JTextField();
		txtOrderNumber.setEditable(false);
		GridBagConstraints gbc_txtOrderNumber = new GridBagConstraints();
		gbc_txtOrderNumber.insets = new Insets(0, 0, 5, 5);
		gbc_txtOrderNumber.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtOrderNumber.gridx = 2;
		gbc_txtOrderNumber.gridy = 0;
		panel_1.add(txtOrderNumber, gbc_txtOrderNumber);
		txtOrderNumber.setColumns(10);
		
		JLabel lblGiftCode = new JLabel("Gavekode");
		GridBagConstraints gbc_lblGiftCode = new GridBagConstraints();
		gbc_lblGiftCode.anchor = GridBagConstraints.WEST;
		gbc_lblGiftCode.insets = new Insets(0, 0, 5, 5);
		gbc_lblGiftCode.gridx = 4;
		gbc_lblGiftCode.gridy = 0;
		panel_1.add(lblGiftCode, gbc_lblGiftCode);
		
		txtGiftCode = new JTextField();
		txtGiftCode.setEditable(false);
		GridBagConstraints gbc_txtGiftCode = new GridBagConstraints();
		gbc_txtGiftCode.insets = new Insets(0, 0, 5, 0);
		gbc_txtGiftCode.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtGiftCode.gridx = 5;
		gbc_txtGiftCode.gridy = 0;
		panel_1.add(txtGiftCode, gbc_txtGiftCode);
		txtGiftCode.setColumns(10);
		
		JLabel lblB2BCustomer = new JLabel("Kunde");
		GridBagConstraints gbc_lblB2BCustomer = new GridBagConstraints();
		gbc_lblB2BCustomer.insets = new Insets(0, 0, 0, 5);
		gbc_lblB2BCustomer.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblB2BCustomer.gridx = 1;
		gbc_lblB2BCustomer.gridy = 1;
		panel_1.add(lblB2BCustomer, gbc_lblB2BCustomer);
		
		txtB2BCustomer = new JTextField();
		txtB2BCustomer.setEditable(false);
		GridBagConstraints gbc_txtB2BCustomer = new GridBagConstraints();
		gbc_txtB2BCustomer.insets = new Insets(0, 0, 0, 5);
		gbc_txtB2BCustomer.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtB2BCustomer.gridx = 2;
		gbc_txtB2BCustomer.gridy = 1;
		panel_1.add(txtB2BCustomer, gbc_txtB2BCustomer);
		txtB2BCustomer.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.insets = new Insets(0, 0, 0, 5);
		gbc_lblEmail.gridx = 4;
		gbc_lblEmail.gridy = 1;
		panel_1.add(lblEmail, gbc_lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setEditable(false);
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.gridx = 5;
		gbc_txtEmail.gridy = 1;
		panel_1.add(txtEmail, gbc_txtEmail);
		txtEmail.setColumns(10);
	}
	/**
	 * Metoden initierer productCtrl, tabelmodellerne og den tråd der står for at opdatere
	 * ordrelinjerne. Derudover sættes modeldata og tekstfelterne udfyldes.
	 * @param orderCtrl					er den orderCtrl, som styrer den nuværende ordre
	 * @param giftNo					er det nummer, som brugeren har indtastet
	 * @throws DataAccessException		smides hvis forbindelsen til databasen forsvinder eller
	 * ikke kan oprettes.
	 */
	public void init(OrderCtrl orderCtrl, String giftNo) throws DataAccessException{
		choice = false;
		this.giftNo = giftNo;
		this.orderCtrl = orderCtrl;
		this.productCtrl = new ProductCtrl();
		orderChoiceTableModel = new OrderChoiceTableModel();
		this.tblChoices.setModel(orderChoiceTableModel);
		this.txtOrderNumber.setText(Integer.toString(orderCtrl.getOrder().getOrderNo()));
		this.txtB2BCustomer.setText(orderCtrl.getOrder().getB2BCustomer().getCompanyName());
		this.txtGiftCode.setText(giftNo);
		for(String email : orderCtrl.getOrder().getEmailGiftNo().keySet()) {
			if(orderCtrl.getOrder().getEmailGiftNo().get(email).equals(giftNo)){
				this.txtEmail.setText(email);
			}
		}
		orderChoiceOrderTableModel = new OrderChoiceOrderTableModel();
		this.tblB2BOrder.setModel(orderChoiceOrderTableModel);
		refresh();
		new Thread(() -> {
			while(true) {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// should not happen - we don't interrupt this thread
					e.printStackTrace();
				}
				try {
					this.updateOrderList();
				} catch (DataAccessException e) {
					JOptionPane.showMessageDialog(this, "Kunne ikke opdatere ordrelisten", 
							"Data access error", JOptionPane.ERROR_MESSAGE);				
				}
			}
		}).start();
	}
	/**
	 * Metoden sender en besked om at gemme valget af den valgte pakke og sletter vinduet.
	 * @throws DataAccessException	smides hvis der ikke kan gemmes til databasen
	 */
	private void saveChoiceClicked()  {
		try {
			orderCtrl.saveChoice(giftNo, barcode);
			productCtrl.updateStock(barcode);
			this.dispose();
		} catch (DataAccessException e) {
			JOptionPane.showMessageDialog(this, "Kunne ikke gemme ordren", 
					"Data access error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} 
	}
	/**
	 * Metoden tager den markerede række og sender den til choosePack metoden. Herefter
	 * opdateres tabellerne.
	 */
	private void choiceClicked() {
		int selected = tblChoices.getSelectedRow();
		if(selected >= 0 && !choice) {
		choosePack(selected);
		choice = true;
		refresh();
		}
	}

//	private void updateOrder(int selected) {
//		orderChoiceOrderTableModel.getElementAtIndex(selected);
//		updateQuantity();
//	}
	/**
	 * Metoden tager rækken, finder barcode på den pågældende pakke og tilføjer 1 til quantity på ordrelinjen.
	 * @param selected er den markerede række i tabellen.
	 */
	private void choosePack(int selected) {
		barcode = orderCtrl.getOrder().getOrderLines().get(selected).getProduct().getBarcode();
		orderCtrl.choosePack(barcode);
		
	}
	/**
	 * Metoden opdaterer tabellerne.
	 */
	private void refresh() {
		List<B2BOrderLine> currOrderLines = orderCtrl.getOrder().getOrderLines();
		this.orderChoiceTableModel.setModelData(currOrderLines);
		this.orderChoiceOrderTableModel.setModelData(currOrderLines);
	}
	/**
	 * Metoden opdaterer den ene af tabellerne med ordrelinjer, og kaldes fra tråden.
	 * @throws DataAccessException	smides hvis <code>ResultSet<code> ikke kan læses.
	 */
	private void updateOrderList() throws  DataAccessException {
		SwingUtilities.invokeLater(() -> {
		try {
			orderCtrl.pullOrderLines(orderCtrl.getOrder());
		} catch (DataAccessException e) {
			//e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Kunne ikke opdatere ordrelisten", "Data access error", 
					JOptionPane.ERROR_MESSAGE);
		}
		this.orderChoiceOrderTableModel.setModelData(orderCtrl.getOrder().getOrderLines());
		});
	}
}
