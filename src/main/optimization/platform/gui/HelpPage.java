package main.optimization.platform.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Color;

public class HelpPage extends JDialog {

	private JFrame frmHelp;
	private JTextField textField;
	private JPasswordField passwordField;
	private JTextField textField_1;

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	public HelpPage(JFrame frame)  {
		
		setBackground(Color.GRAY);
		setTitle("Help");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(70, 11, 331, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(20, 14, 46, 14);
		getContentPane().add(lblEmail);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(70, 42, 138, 20);
		getContentPane().add(passwordField);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(4, 45, 62, 14);
		getContentPane().add(lblPassword);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(70, 73, 331, 20);
		getContentPane().add(textField_1);
		
		JLabel lblSubject = new JLabel("Subject");
		lblSubject.setBounds(14, 76, 62, 14);
		getContentPane().add(lblSubject);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(70, 104, 331, 102);
		getContentPane().add(textArea);
		
		JLabel lblBody = new JLabel("Body");
		lblBody.setBounds(30, 101, 36, 14);
		getContentPane().add(lblBody);
		
		JButton btnSend = new JButton("Send");
		btnSend.setBounds(181, 217, 89, 23);
		getContentPane().add(btnSend);
		
		setLocationRelativeTo(frame);
		setModal(true);
		setResizable(false);
		setVisible(true);
		
		
	}
}
