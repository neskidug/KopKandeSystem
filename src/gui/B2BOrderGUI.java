package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JTable;
import ctrl.OrderCtrl;
import ctrl.ProductCtrl;
import exceptions.DataAccessException;
import model.B2BOrderLine;
import model.Pack;
import javax.swing.JDesktopPane;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BoxLayout;


/**
 * 
 * @authors Rasmus Gudiksen, Jakob Kjeldsteen, Emil Tolstrup Petersen, Christian
 *          Funder og Mark Drongesen
 * 
 *          <p>
 *          Denne klasse styrer interaktionen mellem bruger og tilføjelsen af emails og pakker til ordren.
 *
 */
public class B2BOrderGUI extends JFrame {

	private JPanel contentPane;
	private JTable productTable;
	private JTable loginTable;
	private JLabel lblCustomerName;
	private JLabel lblDate;
	private OrderCtrl orderCtrl;
	private ProductCtrl productCtrl;
	private OrderTableModel orderTableModel;
	private OrderLoginTableModel orderLoginTableModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					B2BOrderGUI frame = new B2BOrderGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Constructoren til B2BOrderHUI objektet
	 * 
	 * @param cvr				er cvrnummeret på firmaet
	 * @param companyName		er navnet på firmaet
	 * @param endDate			er datoen for, hvornår ordren skal udløbe
	 * @param orderCtrl			er den controller, som holder den nuværende ordre
	 */
	public B2BOrderGUI(int cvr, String companyName, String endDate, OrderCtrl orderCtrl) {
		this();
		init(cvr, companyName, endDate, orderCtrl);
	}
	
	/**
	 * Constructoren  der laver GUI
	 */
	public B2BOrderGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel Header = new JPanel();
		contentPane.add(Header, BorderLayout.NORTH);
		Header.setLayout(new BorderLayout(0, 0));
		
		JLabel lblOrderLabel = new JLabel("Ordre");
		lblOrderLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblOrderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		Header.add(lblOrderLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		Header.add(panel, BorderLayout.SOUTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		lblCustomerName = new JLabel("Kunde: ");
		GridBagConstraints gbc_lblCustomerName = new GridBagConstraints();
		gbc_lblCustomerName.insets = new Insets(0, 0, 5, 0);
		gbc_lblCustomerName.gridx = 0;
		gbc_lblCustomerName.gridy = 0;
		panel.add(lblCustomerName, gbc_lblCustomerName);
		
		lblDate = new JLabel("Dato: ");
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.gridx = 0;
		gbc_lblDate.gridy = 1;
		panel.add(lblDate, gbc_lblDate);
		
		JPanel bottomPanel = new JPanel();
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		bottomPanel.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnAddProduct = new JButton("Tilføj Produkt");
		btnAddProduct.addActionListener((e -> {
			addProductClicked();
		}));
		panel_2.add(btnAddProduct);
		
		JButton btnAddLogin = new JButton("Tilføj Login");
		btnAddLogin.addActionListener((e -> {
			addLoginClicked();
		}));
		panel_2.add(btnAddLogin);
		
		JButton btnEndOrder = new JButton("Afslut Ordre");
		btnEndOrder.addActionListener((e -> {
			endOrderClicked();
		}));
		panel_2.add(btnEndOrder);
		
		JPanel pnlSubTotal = new JPanel();
		bottomPanel.add(pnlSubTotal, BorderLayout.NORTH);
		
		JLabel lblSubTotal = new JLabel("Subtotal:");
		lblSubTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		GroupLayout gl_pnlSubTotal = new GroupLayout(pnlSubTotal);
		gl_pnlSubTotal.setHorizontalGroup(
			gl_pnlSubTotal.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnlSubTotal.createSequentialGroup()
					.addContainerGap(346, Short.MAX_VALUE)
					.addComponent(lblSubTotal)
					.addGap(34))
		);
		gl_pnlSubTotal.setVerticalGroup(
			gl_pnlSubTotal.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnlSubTotal.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblSubTotal))
		);
		pnlSubTotal.setLayout(gl_pnlSubTotal);
		
		JDesktopPane desktopPane = new JDesktopPane();
		contentPane.add(desktopPane, BorderLayout.CENTER);
		desktopPane.setLayout(new BoxLayout(desktopPane, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		desktopPane.add(scrollPane);
		
		productTable = new JTable();
		scrollPane.setViewportView(productTable);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		desktopPane.add(scrollPane_1);
		
		loginTable = new JTable();
		scrollPane_1.setViewportView(loginTable);
	}
	/**
	 * Metoden initierer modellen for tabellerne, sætter teksfelterne, initierer productCtrl
	 * og laver en ny ordre gennem orderCtrl. De exceptions, som bliver smidt, håndteres 
	 * her ved at give brugeren en besked om fejlen.
	 * @param cvr
	 * @param companyName
	 * @param endDate
	 * @param orderCtrl
	 */
	private void init(int cvr, String companyName, String endDate, OrderCtrl orderCtrl) {
		lblCustomerName.setText("Kunde: " + companyName);
		lblDate.setText("Slutdato: " + endDate);
		this.orderCtrl = orderCtrl;
		orderTableModel = new OrderTableModel();
		this.productTable.setModel(orderTableModel);
		orderLoginTableModel = new OrderLoginTableModel();
		this.loginTable.setModel(orderLoginTableModel);
		try {
			orderCtrl.registerB2BOrder(endDate, cvr);
		} catch (DataAccessException e) {
			JOptionPane.showMessageDialog(this, "Kan ikke få adgang til database", "Data access error",
					JOptionPane.OK_OPTION);
			//e.printStackTrace();
		}
		try {
			productCtrl = new ProductCtrl();
		} catch (DataAccessException e) {
			JOptionPane.showMessageDialog(this, "Kan ikke få adgang til database", "Data access error",
					JOptionPane.OK_OPTION);
			//e.printStackTrace();
		}
	}
	/**
	 * Metoden laver en <code>JOptionPane<code> og beder om strgkode input. Inputtet tages
	 * og bruges til at finde en pack, som tilføjes til ordren på orderCtrl.
	 */
	private void addProductClicked() {
		String insertBarcode = JOptionPane.showInputDialog("Indtast stregkode");
		Pack currPack = null;
		try {
			currPack = productCtrl.findPack(insertBarcode);
		} catch (DataAccessException e1) {
			JOptionPane.showMessageDialog(this, "Kan ikke finde produkt", "Data access error",
					JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
		if(currPack != null) {
			try {
				if(orderCtrl.addPackage(currPack.getBarcode()) != null) {
					refresh();
				}
				
			} catch (DataAccessException e) {
				JOptionPane.showMessageDialog(this, "Kan ikke få adgang til database", "Data access error",
						JOptionPane.ERROR_MESSAGE);
				//e.printStackTrace();
			}
			
		}else if(insertBarcode != null){
			JOptionPane.showMessageDialog(this, "Forkert stregkode", "Stregkodefejl!",
					JOptionPane.ERROR_MESSAGE);
			addProductClicked();
		}
	
	}
	/**
	 * Metoden opdaterer modellerne i vinduet med orderLines og emailGiftNo.
	 */
	private void refresh() {
		List<B2BOrderLine> currOrderLines = orderCtrl.getOrder().getOrderLines();
		this.orderTableModel.setModelData(currOrderLines);
		HashMap<String,String> currLogins = orderCtrl.getOrder().getEmailGiftNo();
		this.orderLoginTableModel.setModelData(currLogins);
	}
	/**
	 * Metoden afslutter en ordre og sender den til ordreDB for at blive gemt. 
	 * Hvis det lykkedes vises en besked, ellers vises en fejlmeddelelse.
	 */
	private void endOrderClicked() {
		try {
			if(orderCtrl.endOrder() != null) {
//				orderCtrl.endOrder();
				JOptionPane.showMessageDialog(this, "Din Ordre gemt");
				this.dispose();
			}else {
				JOptionPane.showMessageDialog(this, "Kan ikke gemme tom ordre", "Data access error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (DataAccessException e) {
			JOptionPane.showMessageDialog(this, "Kan ikke få adgang til database", "Data access error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	/**
	 * Metoden laver en <code>JOptionPane<code>, som brugeren inputter en email gennem. Inputtet tjekkes 
	 * for duplikater og tilføjes ellers til ordren.
	 */
	private void addLoginClicked() {
		boolean login = false;
		String insertEmail = JOptionPane.showInputDialog("Indtast email til login");
		if(insertEmail != null){
			if(checkEmail(insertEmail)) {
		try {
			login = orderCtrl.addB2BLogin(insertEmail);
		} catch (DataAccessException e) {
			JOptionPane.showMessageDialog(this, "Kan ikke få adgang til database", "Data access error",
					JOptionPane.ERROR_MESSAGE);
			//e.printStackTrace();
		}
			if(login) {
				refresh();
			}else {
				JOptionPane.showMessageDialog(null, "Mailadresse er allerede tilføjet", "Fejl", 
						JOptionPane.ERROR_MESSAGE);
			}
		}else {
			JOptionPane.showMessageDialog(null, "Ugyldigt email format", "Fejlmeddelelse",
					JOptionPane.ERROR_MESSAGE);
			addLoginClicked();
		}
		}
	}
	/**
	 * Metoden tjekker en indtastes streng for brud mod et givent format.
	 * @param email	er emailen, som brugeren har indtastet, og som skal tilføjes til ordren.
	 * @return en boolean. som returnerer <code>True<code> hvis den instastede streng overholder formatet.
	 */
	private boolean checkEmail(String email) {
		String emailPattern = "^[ÆØÅæøåa-zA-Z0-9._%+-]+@[ÆØÅæøåa-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";

		Pattern pattern = Pattern.compile(emailPattern);

		Matcher m = pattern.matcher(email);

		if(m.find()) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	
}
