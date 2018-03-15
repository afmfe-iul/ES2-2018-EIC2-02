package main.optimization.platform.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;



public class MainLayout {
	public JFrame frame;
	public JTextField txtRules;
	public JTextField txtHam;
	public JTextField txtSpam;
	public JTextField txtManualFPositive;
	public JTextField txtManualFNegative;
	public JTextField txtOptimalFPositive;
	public JTextField txtOptimalFNegative;
	public JTable tableManualConfig;
	public JTable tableOptimalConfig;
	public JButton btnGravarManual;
	public JButton btnGravarOptimal;
	public JButton btnEvaluateManual;
	public JButton btnGenerateOptimal;
	public JDialog progressDialog;
	public JScrollPane scrollPaneTabel1;
	public JScrollPane scrollPaneTabel2;
	public HashMap<String, Integer> rulesMap = new HashMap<String, Integer>();
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
					MainLayout window = new MainLayout();
					window.initialize();
					
					window.frame.setVisible(true);

			}
		});
	}
	
	public void initialize() {
		frame = new JFrame("AntiSpamConfigurationForLeisureMailbox");
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 667);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int confirm = JOptionPane.showOptionDialog(null, "Do you wish to save the File Paths?",
						"Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (confirm == 0) {
				}
				System.exit(0);
			}
		});

		JLabel lblRules = new JLabel("Rules");
		JLabel lblHam = new JLabel("Ham");
		JLabel lblSpam = new JLabel("Spam");

		txtRules = new JTextField();
		txtRules.setEditable(false);
		txtRules.setColumns(10);

		txtHam = new JTextField();
		txtHam.setEditable(false);
		txtHam.setColumns(10);

		txtSpam = new JTextField();
		txtSpam.setEditable(false);
		txtSpam.setColumns(10);

		JButton btnRules = new JButton("");
		//btnRules.setIcon(new ImageIcon(MainLayout.class.getResource("/imageWindowBuilder/foldericon.png")));

		JButton btnHam = new JButton("");
		//btnHam.setIcon(new ImageIcon(MainLayout.class.getResource("/imageWindowBuilder/foldericon.png")));

		JButton btnSpam = new JButton("");
		//btnSpam.setIcon(new ImageIcon(MainLayout.class.getResource("/imageWindowBuilder/foldericon.png")));
		JFileChooser fileChooserRules = new JFileChooser();
		JFileChooser fileChooserOther = new JFileChooser();
		FileNameExtensionFilter filterRules = new FileNameExtensionFilter("CF Files", "cf", "cf");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("LOG Files", "log", "log");
		fileChooserRules.setFileFilter(filterRules);
		fileChooserOther.setFileFilter(filter);
		
		btnRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = fileChooserRules.showOpenDialog(btnRules);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooserRules.getSelectedFile();
					if(selectedFile.exists()){
					txtRules.setText(selectedFile.getAbsolutePath());
					}
				}
			}
		});
		btnHam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = fileChooserOther.showOpenDialog(btnHam);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooserOther.getSelectedFile();
					if(selectedFile.exists()){
						txtHam.setText(selectedFile.getAbsolutePath());
						}
				}
			}
		});

		btnSpam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = fileChooserOther.showOpenDialog(btnSpam);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooserOther.getSelectedFile();
					if(selectedFile.exists()){
						txtSpam.setText(selectedFile.getAbsolutePath());
						}
				}
			}
		});

		scrollPaneTabel1 = new JScrollPane();
		scrollPaneTabel2 = new JScrollPane();

		btnEvaluateManual = new JButton("Evaluate");
		btnEvaluateManual.setEnabled(false);
		btnEvaluateManual.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		btnGravarManual = new JButton("Save");
		btnGravarManual.setEnabled(false);
		btnGravarManual.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		txtManualFPositive = new JTextField();
		txtManualFPositive.setEditable(false);
		txtManualFPositive.setColumns(10);
		txtManualFNegative = new JTextField();
		txtManualFNegative.setEditable(false);
		txtManualFNegative.setColumns(10);
		JLabel lblManualFPositive = new JLabel("False Positive:");
		JLabel lblManualFNegative = new JLabel("False Negative:");
		
		btnGenerateOptimal = new JButton("Generate configuration");
		btnGenerateOptimal.setEnabled(false);
		btnGenerateOptimal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						progressDialog.dispose();
					}
				}).start();
				//createProgressPopUp();
			}
		});

		btnGravarOptimal = new JButton("Save");
		btnGravarOptimal.setEnabled(false);
		btnGravarOptimal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		JLabel lblFalsePositive = new JLabel("False Positive:");

		JLabel lblFalseNegative = new JLabel("False Negative:");

		txtOptimalFPositive = new JTextField();
		txtOptimalFPositive.setEditable(false);
		txtOptimalFPositive.setColumns(10);

		txtOptimalFNegative = new JTextField();
		txtOptimalFNegative.setEditable(false);
		txtOptimalFNegative.setColumns(10);
		
		JButton btnFilePaths = new JButton("Carregar ficheiros");
		btnFilePaths.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblHam, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txtHam, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnHam, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblRules)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(txtRules, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE)
									.addGap(6)
									.addComponent(btnRules, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblSpam, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(txtSpam, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(btnFilePaths)
											.addGap(130)))
									.addComponent(btnSpam, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPaneTabel2, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPaneTabel1, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE))
							.addGap(49)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(39)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(btnGravarManual, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnEvaluateManual, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblFalseNegative)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txtOptimalFNegative, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblFalsePositive)
										.addGap(10)
										.addComponent(txtOptimalFPositive, 0, 0, Short.MAX_VALUE)))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(6)
									.addComponent(btnGenerateOptimal))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(29)
									.addComponent(btnGravarOptimal, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblManualFNegative)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txtManualFNegative, 0, 0, Short.MAX_VALUE))
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblManualFPositive)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(txtManualFPositive, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))))))
					.addContainerGap(28, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRules)
						.addComponent(txtRules, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnRules, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtHam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblHam)
						.addComponent(btnHam, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSpam)
						.addComponent(txtSpam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSpam, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(35)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPaneTabel1, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(26)
							.addComponent(btnEvaluateManual)
							.addGap(18)
							.addComponent(btnGravarManual)
							.addGap(26)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblManualFPositive)
								.addComponent(txtManualFPositive, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblManualFNegative)
								.addComponent(txtManualFNegative, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnGenerateOptimal)
							.addGap(18)
							.addComponent(btnGravarOptimal)
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblFalsePositive)
								.addComponent(txtOptimalFPositive, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblFalseNegative)
								.addComponent(txtOptimalFNegative, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(scrollPaneTabel2, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING))).addGroup(groupLayout.createSequentialGroup()
					.addGap(118)
					.addComponent(btnFilePaths))
		);

		resetTableModels();
		frame.getContentPane().setLayout(groupLayout);
	}
	public void promptUser(String message, boolean error){
		String title = error ?  "Error!" : "Warning!";
		int iconType = error ? JOptionPane.ERROR_MESSAGE : JOptionPane.WARNING_MESSAGE;
		JOptionPane.showMessageDialog(frame, message, title, iconType);
	}
	public  void resetTableModels() {
		tableOptimalConfig = new JTable();
		tableOptimalConfig.setEnabled(false);
		tableOptimalConfig.setRowSelectionAllowed(false);
		
		tableOptimalConfig.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Rules", "Weights"
				}
				){
			private static final long serialVersionUID = 1L;
			
			Class<?>[] columnTypes = new Class[] {
					String.class, Double.class
			};
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
					false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableOptimalConfig.getColumnModel().getColumn(0).setResizable(false);
		tableOptimalConfig.getColumnModel().getColumn(1).setResizable(false);
		tableManualConfig = new JTable();
		tableManualConfig.setRowSelectionAllowed(false);
		tableManualConfig
		.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Rules", "Weights"
				}
				) {
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
					String.class, Double.class
			};
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
					false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableManualConfig.getColumnModel().getColumn(0).setResizable(false);
		tableManualConfig.getColumnModel().getColumn(1).setResizable(false);
		scrollPaneTabel1.setViewportView(tableManualConfig);
		scrollPaneTabel2.setViewportView(tableOptimalConfig);
	}

	
	private void createXml(){
		//TODO create xml
	}
	
	private void loadJar(){
		//TODO load jar function
	}

}
