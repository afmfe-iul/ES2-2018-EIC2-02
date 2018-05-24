package main.optimization.platform.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTextField;
import main.optimization.platform.utils.EmailSender;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.Color;
/**
 *Extends JDialog class and provides a user interface pop-up to send emails 
 *from the user to the administrator, providing a mean to clarify possible problems about the optimization platform
 *@author Tiago Feliciano
 */
public class HelpPage extends JDialog {
	private static final long serialVersionUID = 1L;

	/**Constructor
	 * 
	 * @param frame  Parent JFrame
	 * @param emailAdmin  Admin´s email previously read from config.xml
	 */
	public HelpPage(JFrame frame, String emailAdmin, String passAdmin)  {
		setBackground(Color.GRAY);
		setTitle("Help");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JTextField textField = new JTextField();
		textField.setBounds(70, 11, 331, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(20, 14, 46, 14);
		getContentPane().add(lblEmail);
		
		JTextField textSubject = new JTextField();
		textSubject.setColumns(10);
		textSubject.setBounds(70, 53, 331, 20);
		getContentPane().add(textSubject);
		
		JLabel lblSubject = new JLabel("Subject");
		lblSubject.setBounds(14, 56, 62, 14);
		getContentPane().add(lblSubject);
		
		JTextArea textBody = new JTextArea();
		textBody.setBounds(70, 84, 331, 102);
		getContentPane().add(textBody);
		
		JLabel lblBody = new JLabel("Body");
		lblBody.setBounds(30, 81, 36, 14);
		getContentPane().add(lblBody);
		
		JButton btnSend = new JButton("Send");
		btnSend.setBounds(181, 197, 89, 23);
		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> to = new ArrayList<>();
				to.add(emailAdmin);
				
				try {
					EmailSender sender =  new EmailSender(emailAdmin, passAdmin, to, textSubject.getText(), textBody.getText());
					sender.sendFromGMail();
					JOptionPane.showMessageDialog(frame, "Email was sent");
					dispose();
				}catch (Exception excep) {
					excep.printStackTrace();
					JOptionPane.showMessageDialog(frame, "The input is not valid.");
				}
		}});
		
		getContentPane().add(btnSend);
		setLocationRelativeTo(frame);
		setModal(true);
		setResizable(false);
		setVisible(true);
	}
}