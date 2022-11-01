package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * 
 * @authors Rasmus Gudiksen, Jakob Kjeldsteen, Emil Tolstrup Petersen, Christian
 *          Funder og Mark Drongesen
 * 
 *          <p>
 *          Denne klasse styrer interaktionen mellem bruger og hovedmenuen.
 *
 */

public class MainMenu extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu();
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
	public MainMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{141, 0};
		gbl_panel.rowHeights = new int[]{83, 81, 80, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JButton btnNewOrder = new JButton("B2B Ordre");
		GridBagConstraints gbc_btnNewOrder = new GridBagConstraints();
		gbc_btnNewOrder.fill = GridBagConstraints.BOTH;
		gbc_btnNewOrder.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewOrder.gridx = 0;
		gbc_btnNewOrder.gridy = 0;
		panel.add(btnNewOrder, gbc_btnNewOrder);
		btnNewOrder.addActionListener((e -> newOrderClicked()));
		
		JButton btnFindOrder = new JButton("Find ordre");
		btnFindOrder.setEnabled(false);
		GridBagConstraints gbc_btnFindOrder = new GridBagConstraints();
		gbc_btnFindOrder.insets = new Insets(0, 0, 5, 0);
		gbc_btnFindOrder.fill = GridBagConstraints.BOTH;
		gbc_btnFindOrder.gridx = 0;
		gbc_btnFindOrder.gridy = 1;
		panel.add(btnFindOrder, gbc_btnFindOrder);
		
		JButton btnFindProduct = new JButton("Find produkter");
		GridBagConstraints gbc_btnFindProduct = new GridBagConstraints();
		gbc_btnFindProduct.fill = GridBagConstraints.BOTH;
		gbc_btnFindProduct.gridx = 0;
		gbc_btnFindProduct.gridy = 2;
		panel.add(btnFindProduct, gbc_btnFindProduct);
		btnFindProduct.setEnabled(false);

	}
	/**
	 * Metoden laver en ny B2BOrderMenu, gør den synlig og sletter denne <code>JFrame<code>
	 */
	private void newOrderClicked() {
		B2BOrderMenu orderMenu = new B2BOrderMenu();
		orderMenu.setVisible(true);
		this.dispose();
	}

}
