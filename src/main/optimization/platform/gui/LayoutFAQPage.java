package main.optimization.platform.gui;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.SystemColor;

public class LayoutFAQPage {
	
	public JFrame frame;
	private JTextField question1;
	private JTextField question2;
	private JTextField questions3;
	private JTextField question4;
	private JTextField question5;
	private JTextPane answer5;
	private JTextPane answer4;
	private JTextPane answer3;
	private JTextPane answer2;
	private JTextPane answer1;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void initialize(){
		frame = new JFrame("FAQPage");
		frame.getContentPane().setLayout(null);
		
		question1 = new JTextField();
		question1.setBackground(UIManager.getColor("Button.background"));
		question1.setEditable(false);
		question1.setText("Can I optimize some of my problems?");
		question1.setBounds(10, 11, 561, 20);
		frame.getContentPane().add(question1);
		question1.setColumns(10);
		
		question2 = new JTextField();
		question2.setEditable(false);
		question2.setText("How can I get started? ");
		question2.setColumns(10);
		question2.setBounds(10, 84, 561, 20);
		frame.getContentPane().add(question2);
		
		questions3 = new JTextField();
		questions3.setEditable(false);
		questions3.setBackground(UIManager.getColor("Button.background"));
		questions3.setText("Can I get notified of the optimization process?");
		questions3.setColumns(10);
		questions3.setBounds(10, 160, 561, 20);
		frame.getContentPane().add(questions3);
		
		question4 = new JTextField();
		question4.setBackground(UIManager.getColor("Button.background"));
		question4.setText("Is my personal and problem data protected?");
		question4.setColumns(10);
		question4.setBounds(10, 222, 561, 20);
		frame.getContentPane().add(question4);
		
		question5 = new JTextField();
		question5.setBackground(UIManager.getColor("Button.background"));
		question5.setText("My question is not on this page, what can I do?");
		question5.setColumns(10);
		question5.setBounds(10, 286, 561, 20);
		frame.getContentPane().add(question5);
		
		answer5 = new JTextPane();
		answer5.setBackground(new Color(240, 240, 240));
		answer5.setText("You can send an email threw our platform to ask questions to the administrator, if your question is good we will add it to our FAQ page. ");
		answer5.setEditable(false);
		answer5.setBounds(10, 317, 561, 34);
		frame.getContentPane().add(answer5);
		
		answer4 = new JTextPane();
		answer4.setText("Please check our declaration/agreement on the Data protection declaration tab.");
		answer4.setEditable(false);
		answer4.setBackground(SystemColor.menu);
		answer4.setBounds(10, 253, 561, 23);
		frame.getContentPane().add(answer4);
		
		answer3 = new JTextPane();
		answer3.setText("Yes you receive notifications in regards of the process itself to your personal email.");
		answer3.setEditable(false);
		answer3.setBackground(SystemColor.menu);
		answer3.setBounds(10, 188, 561, 23);
		frame.getContentPane().add(answer3);
		
		answer2 = new JTextPane();
		answer2.setText("You just click on the executable and start filling up the specification of the problem that you want to see optimized, then just choose the different algorithms suggested and optimize.");
		answer2.setEditable(false);
		answer2.setBackground(SystemColor.menu);
		answer2.setBounds(10, 115, 561, 34);
		frame.getContentPane().add(answer2);
		
		answer1 = new JTextPane();
		answer1.setText("Yes sure, that\u2019s the utility of our application, you can specify your problem and then chose to optimize threw various JMetal framework or if you are in a rush just save It to a .xml file and start the process later. ");
		answer1.setEditable(false);
		answer1.setBackground(SystemColor.menu);
		answer1.setBounds(10, 39, 561, 46);
		frame.getContentPane().add(answer1);
	}
}


