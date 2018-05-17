package main.optimization.platform.gui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import java.awt.Font;

public class LayoutDataAgreement extends JDialog{
	private static final long serialVersionUID = 1L;

	public LayoutDataAgreement(JFrame frame) {
		setFont(new Font("Tahoma", Font.PLAIN, 11));
		setTitle("Data protection declaration");
		setBounds(320, 30, 467, 195);
		setLayout(null);
		
		JTextPane txtpnThisOptimizationProblem = new JTextPane();
		txtpnThisOptimizationProblem.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtpnThisOptimizationProblem.setEditable(false);
		txtpnThisOptimizationProblem.setBackground(UIManager.getColor("Button.background"));
		txtpnThisOptimizationProblem.setText("This Optimization Problem Processing App,  includes the Standard Contractual Clauses "
				+ "adopted by the European Commission, as applicable, reflects the parties agreement with "
				+ "respect to the terms governing the Processing and protection of Personal Data under the Groups Customer Terms of Service "
				+ "(the \u201CAgreement\u201D).We shall follow the term of the Agreement and will maintain full closure of every "
				+ "personal or problem data inputed by every user.");
		txtpnThisOptimizationProblem.setBounds(10, 11, 441, 138);
		add(txtpnThisOptimizationProblem);

		setLocationRelativeTo(frame);
		setModal(true);
		setResizable(false);
		setVisible(true);
	}
}
