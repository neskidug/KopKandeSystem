package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ctrl.CustomerCtrl;
import ctrl.OrderCtrl;
import exceptions.DataAccessException;
import model.B2BCustomer;
import model.B2BOrder;
import java.sql.SQLException;

/**
 * 
 * @authors Rasmus Gudiksen, Jakob Kjeldsteen, Emil Tolstrup Petersen, Christian
 *          Funder og Mark Drongesen
 * 
 *          <p>
 *          Denne klasse styrer interaktionen mellem bruger B2B ordremenuen.
 *
 */

public class B2BOrderMenu extends JFrame {

	private JPanel contentPane;
	private CustomerCtrl customerCtrl;
	private OrderCtrl orderCtrl;
	private int cvr;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					B2BOrderMenu frame = new B2BOrderMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public B2BOrderMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 60, 60, 60, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JButton btnNyB2BOrdre = new JButton("Ny B2B Ordre");
		btnNyB2BOrdre.addActionListener((e -> {
			newB2BOrderClicked();
		}));
		GridBagConstraints gbc_btnNyB2BOrdre = new GridBagConstraints();
		gbc_btnNyB2BOrdre.insets = new Insets(0, 0, 5, 0);
		gbc_btnNyB2BOrdre.fill = GridBagConstraints.BOTH;
		gbc_btnNyB2BOrdre.gridx = 0;
		gbc_btnNyB2BOrdre.gridy = 0;
		panel.add(btnNyB2BOrdre, gbc_btnNyB2BOrdre);

		JButton btnGiftChoice = new JButton("B2B Gavevalg");
		btnGiftChoice.addActionListener((e) -> {
			giftChoiceClicked();
		});
		
		GridBagConstraints gbc_btnGiftChoice = new GridBagConstraints();
		gbc_btnGiftChoice.fill = GridBagConstraints.BOTH;
		gbc_btnGiftChoice.insets = new Insets(0, 0, 5, 0);
		gbc_btnGiftChoice.gridx = 0;
		gbc_btnGiftChoice.gridy = 1;
		panel.add(btnGiftChoice, gbc_btnGiftChoice);

		JButton btnTestData = new JButton("Ny ordre med testdata");
		btnTestData.addActionListener((e -> {
				testDataClicked();
		}));
		GridBagConstraints gbc_btnTestData = new GridBagConstraints();
		gbc_btnTestData.fill = GridBagConstraints.BOTH;
		gbc_btnTestData.gridx = 0;
		gbc_btnTestData.gridy = 2;
		panel.add(btnTestData, gbc_btnTestData);

		init();
	}

	

	
	/**
	 * Metoden initierer customerCtrl og orderCtrl.
	 */
	private void init() {
		try {
			customerCtrl = new CustomerCtrl();
		} catch (DataAccessException e) {
			JOptionPane.showMessageDialog(this, "Kan ikke få adgang til database", "Data access error",
					JOptionPane.OK_OPTION);
//			e.printStackTrace();
		}
		try {
			orderCtrl = new OrderCtrl();
		} catch (DataAccessException e) {
			JOptionPane.showMessageDialog(this, "Kan ikke få adgang til database", "Data access error",
					JOptionPane.OK_OPTION);
			//e.printStackTrace();
		}
	}
	/**
	 * Metoden laver en <code>JOptionPane<code> hvor brugeren indtaster en gavekode, som tjekkes i forhold
	 * til gavekoder i databasen. Findes koden laves der en instans af GiftChoiceGUI og denne sættes til synlig.
	 * @throws SQLException kastes hvis er fejl i forbindelse med databasen
	 * @throws DataAccessException smides hvis ikke der kan fås adgang til databasen eller <code>ResultSet<code>
	 * ikke kan læses.
	 */
	private void giftChoiceClicked()  {
		String insertGiftNo = JOptionPane.showInputDialog("Indtast gavekode");
		B2BOrder currOrder = null;
		
		try {
			currOrder = orderCtrl.registerB2BOrderChoice(insertGiftNo);
		} catch (DataAccessException e) {
			JOptionPane.showMessageDialog(this, "Kan ikke få adgang til database", "Data access error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		if(!(currOrder == null)) {
			GiftChoiceGUI gcgui;
			try {
				gcgui = new GiftChoiceGUI(orderCtrl, insertGiftNo);
				gcgui.setVisible(true);
			} catch (DataAccessException e) {
//				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Kunne ikke få forbindelse til databsen, prøv igen senere", "Fejl", 
						JOptionPane.ERROR_MESSAGE);
			}
			
		}else {
			JOptionPane.showMessageDialog(null, "Kunne ikke finde koden i systemet eller koden er brugt.", "Fejl", 
					JOptionPane.ERROR_MESSAGE);
		}
		
	}
	/**
	 * Metoden finder et firma ud fra dets CVR nummer og laver en <code>JOptionPane<code> som beder om input i form
	 * af endDate. Hvis disse findes og overholder formatet instantieres en ny B2BOrderGUI og dette vindue afmonteres.
	 */
	private void newB2BOrderClicked() {
		String companyName = findCompanyNameByCVR();
		if(companyName != null && companyName.equals("close!")) {
			
		}
		else if(companyName != null) {
			String endDate = JOptionPane.showInputDialog("Indtast slut dato: 'DD-MM-YYYY'");
			if(endDate == null) {
				
			}
			else if(checkDate(endDate).equals("ok")) {
			B2BOrderGUI orderGUI = new B2BOrderGUI(cvr, companyName, endDate, orderCtrl);
			orderGUI.setVisible(true);
			this.dispose();
			}else if(checkDate(endDate).equals("dato fejl")){
				JOptionPane.showMessageDialog(this, "Dato skal være efter dags dato", "Input fejl",
						JOptionPane.OK_OPTION);
				newB2BOrderClicked();
			}else if(checkDate(endDate).equals("format fejl")) {
				JOptionPane.showMessageDialog(this, "Dato skal være af format 'DD-MM-YYYY'", "Input fejl", 
						JOptionPane.OK_OPTION);
				newB2BOrderClicked();
			}
		}else{
			newB2BOrderClicked();
		}
	}
	/**
	 * Metoden finder et firma ud fra CVR nummer, som indtastes i <code>JOptionPane<code>. 
	 * @return en streng med firmaets navn.
	 */
	private String findCompanyNameByCVR() {
		String insertCVR = JOptionPane.showInputDialog("Indtast CVR");
		cvr = -1;
		if(isANumber(insertCVR) && !insertCVR.isBlank()) {
			cvr = Integer.parseInt(insertCVR);
		}else if(insertCVR == null){
			return "close!";
		}else {
			JOptionPane.showMessageDialog(this, "Input skal være et tal.", "Input fejl", JOptionPane.OK_OPTION);
			return null;
		}

		B2BCustomer currCustomer = null;

		try {
			currCustomer = customerCtrl.findB2BCustomer(cvr);
		} catch (DataAccessException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(this, "Kan ikke få adgang til database", "Data access error",
					JOptionPane.OK_OPTION);
		}

		if (currCustomer == null) {
			JOptionPane.showMessageDialog(null, "Kunne ikke finde kunde ud fra indtastede cvr.", "Fejlmeddelelse",
					JOptionPane.OK_OPTION);
			return null;
		}
		return currCustomer.getCompanyName();
	}
	/**
	 * Metoden tjekker om en indtastet dato overholder datoformatet og er efter dags dato.
	 * @param date er endDate, som brugeren indtaster
	 * @return En streng, som indeholder datoen i det korrekte format.
	 */
	private String checkDate(String date) {
		String res = "";
		
		//datePattern describes a dd-mm-yyyy date pattern.
		String datePattern = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$";
		LocalDate endDate = null;

		Pattern pattern = Pattern.compile(datePattern);

		Matcher m = pattern.matcher(date);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			try {
				endDate = LocalDate.parse(date, formatter);
				if(m.find() && endDate.isAfter(LocalDate.now())) {
					res = "ok";
				}else if(LocalDate.now().isAfter(endDate)) {
					res = "dato fejl";
				}
			} catch (Exception e) {
				res  = "format fejl";
			}
		return res;
	}
	/**
	 * Metoden bruges til at test om en indtastet Integer er et tal
	 * @param string er den indtastede streng, som skal tjekkes for at være et nummer.
	 * @return En boolean, som er sand hvis der er tale om et tal.
	 */
	private boolean isANumber(String string) {
		try {
			Double.parseDouble(string);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * Metoden laver en testordre med CVR, firmanavn, slutdato og orderCtrl allerede 
	 * associeret.
	 */
	private void testDataClicked() {
		B2BOrderGUI orderGUI = new B2BOrderGUI(123456789, "CGI", "02-02-2222", orderCtrl);
		orderGUI.setVisible(true);
		this.dispose();		
	}
}