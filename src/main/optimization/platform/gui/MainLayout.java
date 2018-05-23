package main.optimization.platform.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import main.optimization.platform.gui.external.DataVisualization;
import main.optimization.platform.jMetal.OptimizationProcess;
import main.optimization.platform.jMetal.OptimizationProcess.DATA_TYPES;
import main.optimization.platform.utils.Builders;
import javax.swing.JCheckBox;
import javax.swing.JToolBar;

/**
 * GUI of the optimizaion platform
 * 
 * @author Daniel Caldeira, Hugo Alexandre, André Freire, Tiago Feliciano
 * 
 *
 */
public class MainLayout {
	public static String PATH_INPUT;
	public static String PATH_OUTPUT;

	public JFrame frame;
	private JTable tableVariable;
	private JTable tableCriteria;
	private JTable tableAlgorithms;
	private JScrollPane scrollPanelTableVariable;
	private JScrollPane scrollPanelTableCriteria;
	private JScrollPane scrollPanelAlgorithms;
	private JLabel lblEmail;
	private JLabel lblNameProblem;
	private JTextField txtProblemName;
	private JTextField txtEmail;
	private JLabel lblNameVariables;
	private JTextField txtVariablesName;
	private JLabel lblMaximumtime;
	private JTextField txtMaximumTime;
	private JLabel lblBitsPerVariable;
	private JLabel lblType;
	private JLabel lblVariablesNumber;
	private JTextField txtNumberVariables;
	private JTextArea txtProblemDescription;
	private JComboBox<String> comboBoxType;
	private JFileChooser fileChooserJar;
	private DefaultTableModel modelTableButton;
	private JTextField txtNumberCriteria;
	private JCheckBox chckbxManual;
	private JCheckBox chckbxAutomatic;
	private JLabel lblOptimizationImpliesMinimizing;
	private LayoutProblem currentProblem;
	private String emailAdmin;
	private JTextField txtSolutionKnown;
	private Container mainPanel;
	private JTextField txtBitsPerVariable;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				MainLayout window = new MainLayout();
				window.initialize();
				window.frame.setVisible(true);

			}
		});
	}

	/**
	 * Loads config.xml file into different variables in the MainLayout class
	 */
	public void loadAdminCfgFile() {
		File file = new File("config.xml");
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(AdminXmlObject.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			AdminXmlObject adminXmlObject = (AdminXmlObject) jaxbUnmarshaller.unmarshal(file);
			emailAdmin = adminXmlObject.getEmail();
			PATH_INPUT = adminXmlObject.getpathInput();
			PATH_OUTPUT = adminXmlObject.getpathOutput();
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Initializes every graphical component
	 */
	public void initialize() {
		loadAdminCfgFile();
		FileNameExtensionFilter filterXml = new FileNameExtensionFilter("Xml files", "xml", "xml");
		frame = new JFrame("Optimizer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setBounds(320, 30, 1000, 667);
		fileChooserJar = new JFileChooser(new File(PATH_INPUT));
		JFileChooser fileChooserXml = new JFileChooser(new File(PATH_INPUT));
		FileNameExtensionFilter filterJar = new FileNameExtensionFilter("jar Files", "jar", "jar");
		fileChooserJar.setFileFilter(filterJar);
		scrollPanelTableVariable = new JScrollPane();
		scrollPanelTableCriteria = new JScrollPane();
		comboBoxType = new JComboBox<String>();

		for (DATA_TYPES t : DATA_TYPES.values()) {
			comboBoxType.addItem(t.toString());
		}

		txtProblemDescription = new JTextArea();
		JLabel lblDescrio = new JLabel("Description");
		lblEmail = new JLabel("Email :");
		lblNameProblem = new JLabel("Problem name: ");
		txtProblemName = new JTextField();
		txtProblemName.setColumns(10);
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		lblNameVariables = new JLabel("Variable Group Name");
		txtVariablesName = new JTextField();
		txtVariablesName.setColumns(10);
		lblMaximumtime = new JLabel("Maximum waiting time (in iterations)");
		txtMaximumTime = new JTextField();
		txtMaximumTime.setColumns(10);
		lblType = new JLabel("Type");
		lblVariablesNumber = new JLabel("Variables number");
		txtNumberVariables = new JTextField();
		txtNumberVariables.setColumns(10);
		JButton btnloadTableVariable = new JButton("Load Table");
		btnloadTableVariable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Pattern numbersPattern = Pattern.compile("[^0-9]", Pattern.CASE_INSENSITIVE);
				Matcher matcher;
				matcher = numbersPattern.matcher(txtNumberVariables.getText());
				if (txtNumberVariables.getText().isEmpty() || matcher.find()) {
					promptUser("Variables number contains invalid input", true);
					return;
				}
				currentProblem.setType(comboBoxType.getSelectedItem().toString());
				if(chckbxManual.isSelected())
					loadTableAlgorithm();
				loadTableVariable();
				if (currentProblem.getType().equals("Binary")) {
					txtBitsPerVariable.setVisible(true);
					lblBitsPerVariable.setVisible(true);
				}
				else {
					txtBitsPerVariable.setVisible(false);
					lblBitsPerVariable.setVisible(false);
				}

			}
		});

		JButton btnSaveXmlProblemL = new JButton("Save problem");
		btnSaveXmlProblemL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveXmlProblem();
			}
		});

		JButton btnOpenXmlProblem = new JButton("Open problem");
		btnOpenXmlProblem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileChooserXml.setFileFilter(filterXml);
				int result = fileChooserXml.showOpenDialog(btnOpenXmlProblem);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooserXml.getSelectedFile();
					if (selectedFile.exists()) {
						loadXmlProblem(selectedFile.getAbsolutePath());
					}

				}
			}
		});

		JButton btnRunDemo = new JButton("Run Demo");
		btnRunDemo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (validateProblemFields()) {
					readProblemFromInterface();
					runDemo();
				}
			}
		});

		JButton btnVisDemo = new JButton("Visualize Demo");
		btnVisDemo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				visualizeDemo(currentProblem);
			}
		});

		txtNumberCriteria = new JTextField();
		txtNumberCriteria.setColumns(10);

		JButton btnCriteria = new JButton("Load Table");
		btnCriteria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadTableCriteria();
			}

		});

		chckbxAutomatic = new JCheckBox("Automatic");
		chckbxAutomatic.setSelected(true);
		chckbxAutomatic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxAutomatic.isSelected()) {
					chckbxManual.setSelected(false);
					scrollPanelAlgorithms.setVisible(false);
				} else
					chckbxAutomatic.setSelected(true);

			}
		});

		chckbxManual = new JCheckBox("Manual");
		chckbxManual.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxManual.isSelected()) {
					chckbxAutomatic.setSelected(false);
					scrollPanelAlgorithms.setVisible(true);
					loadTableAlgorithm();
				} else
					chckbxManual.setSelected(true);

			}
		});

		JLabel lblAlgorithms = new JLabel("    Algorithms");

		lblOptimizationImpliesMinimizing = new JLabel("Optimization implies minimizing jars results");

		scrollPanelAlgorithms = new JScrollPane();
		txtSolutionKnown = new JTextField();
		txtSolutionKnown.setColumns(10);

		JLabel lblSolutionKnown = new JLabel("Solution known");
		Border emptyBorder = BorderFactory.createEmptyBorder();
		JToolBar toolBar = new JToolBar();
		JButton bttFaq = new JButton("Faq");
		JButton bttHelp = new JButton("Help");
		JButton bttAgreement = new JButton("Agreement");
		bttFaq.setBorder(emptyBorder);
		bttFaq.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new LayoutFAQPage(frame);
			}
		});
		bttAgreement.setBorder(emptyBorder);
		bttAgreement.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new LayoutDataAgreement(frame);
			}
		});
		bttHelp.setBorder(emptyBorder);
		bttHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new HelpPage(frame, emailAdmin);
			}
		});

		toolBar.add(bttFaq);
		toolBar.addSeparator();
		toolBar.add(bttAgreement);
		toolBar.addSeparator();
		toolBar.add(bttHelp);

		JLabel lblCriteria = new JLabel("Number of Criterias");

		lblBitsPerVariable = new JLabel("BitsPerVariable");

		txtBitsPerVariable = new JTextField();
		txtBitsPerVariable.setColumns(10);
		lblBitsPerVariable.setVisible(false);
		txtBitsPerVariable.setVisible(false);

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
						.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
								.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(comboBoxType, GroupLayout.PREFERRED_SIZE, 80,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(txtNumberVariables, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup().addGap(21).addComponent(lblType)
												.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(lblVariablesNumber)))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(txtVariablesName, GroupLayout.PREFERRED_SIZE, 230,
														GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup().addGap(74)
												.addComponent(lblNameVariables))))
								.addGroup(
										groupLayout.createParallelGroup(Alignment.LEADING).addComponent(
												txtProblemDescription, GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
												.addComponent(lblDescrio).addGroup(groupLayout.createSequentialGroup()
														.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
																.addComponent(lblNameProblem).addComponent(lblEmail))
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addGroup(groupLayout.createParallelGroup(Alignment.LEADING,
																false).addComponent(txtProblemName).addComponent(
																		txtEmail, GroupLayout.DEFAULT_SIZE, 363,
																		Short.MAX_VALUE)))
												.addComponent(scrollPanelTableVariable, GroupLayout.DEFAULT_SIZE, 447,
														Short.MAX_VALUE)))
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
								.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(txtMaximumTime).addComponent(lblMaximumtime,
												GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
								.addGroup(groupLayout.createSequentialGroup().addGap(51).addGroup(groupLayout
										.createParallelGroup(Alignment.LEADING, false)
										.addComponent(btnRunDemo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(btnVisDemo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(groupLayout.createSequentialGroup().addGap(11)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(chckbxAutomatic, GroupLayout.PREFERRED_SIZE, 83,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(chckbxManual, GroupLayout.PREFERRED_SIZE, 72,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(lblAlgorithms)))))
								.addGroup(groupLayout.createSequentialGroup().addGap(18).addGroup(groupLayout
										.createParallelGroup(Alignment.LEADING).addComponent(btnOpenXmlProblem)
										.addComponent(scrollPanelAlgorithms, GroupLayout.DEFAULT_SIZE, 159,
												Short.MAX_VALUE)
										.addComponent(btnSaveXmlProblemL))))
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(txtNumberCriteria, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnCriteria))
								.addComponent(scrollPanelTableCriteria, GroupLayout.PREFERRED_SIZE, 223,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblCriteria)
								.addGroup(groupLayout.createSequentialGroup().addGap(24).addGroup(groupLayout
										.createParallelGroup(Alignment.LEADING, false)
										.addGroup(groupLayout.createSequentialGroup().addComponent(lblBitsPerVariable)
												.addGap(18).addComponent(txtBitsPerVariable, 0, 0, Short.MAX_VALUE))
										.addGroup(groupLayout.createSequentialGroup().addComponent(lblSolutionKnown)
												.addGap(18).addComponent(txtSolutionKnown, GroupLayout.PREFERRED_SIZE,
														76, GroupLayout.PREFERRED_SIZE)))))
						.addGap(132)).addComponent(btnloadTableVariable)
						.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblOptimizationImpliesMinimizing))
				.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup().addGap(5)
				.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblVariablesNumber)
						.addComponent(lblType).addComponent(lblNameVariables)
						.addComponent(lblMaximumtime, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addComponent(lblCriteria))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBoxType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(txtNumberVariables, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(txtVariablesName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(txtMaximumTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(txtNumberCriteria, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCriteria))
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnloadTableVariable)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout
						.createParallelGroup(
								Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(scrollPanelTableVariable, GroupLayout.PREFERRED_SIZE, 217,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(
										groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNameProblem)
												.addComponent(txtProblemName, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblEmail))
								.addGap(5).addComponent(lblDescrio).addGap(7).addComponent(txtProblemDescription,
										GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup().addComponent(btnRunDemo)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnVisDemo)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblAlgorithms)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(chckbxAutomatic).addGap(3)
								.addComponent(chckbxManual).addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(scrollPanelAlgorithms, GroupLayout.PREFERRED_SIZE, 210,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSaveXmlProblemL)
								.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnOpenXmlProblem))
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(scrollPanelTableCriteria, GroupLayout.PREFERRED_SIZE, 222,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblSolutionKnown).addComponent(txtSolutionKnown,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblBitsPerVariable).addComponent(txtBitsPerVariable,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))))
				.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblOptimizationImpliesMinimizing)
				.addContainerGap(52, Short.MAX_VALUE)));
		resetTableModels();
		mainPanel = frame.getContentPane();
		mainPanel.setLayout(groupLayout);
		scrollPanelAlgorithms.setBorder(BorderFactory.createEmptyBorder());
		scrollPanelTableCriteria.setBorder(BorderFactory.createEmptyBorder());
		scrollPanelTableVariable.setBorder(BorderFactory.createEmptyBorder());

	}

	/**
	 * Loads data from default testing problem Anti Spam filter
	 */
	public void loadData() {
		txtProblemName.setText("AntiSpamFilterProblem");
		txtVariablesName.setText("Anti Spam Names");
		comboBoxType.setSelectedItem("Double");
		txtEmail.setText("demo@email.com");
		File file = new File("experimentsBaseDirectory/TestProblem/Names.cf");
		DefaultTableModel modelManual = new DefaultTableModel(new Object[][] {},
				new String[] { "Name", "Minimum", "Maximum", "Forbidden" }) {
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, Double.class, Double.class, Double.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		};

		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				modelManual.addRow(new Object[] { sc.nextLine(), -5.0, 5.0 });
			}
			sc.close();
			tableVariable.setModel(modelManual);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		txtNumberVariables.setText(String.valueOf(modelManual.getRowCount()));
		scrollPanelTableVariable.setViewportView(tableVariable);

	}

	/**
	 * Prompt user with a message passed in the method's arguments
	 * 
	 * @param message
	 *            Message to be displayed
	 * @param error
	 *            Indicates if the Message dialog displayed is an error message or
	 *            not
	 */
	public void promptUser(String message, boolean error) {
		String title = error ? "Error!" : "Warning!";
		int iconType = error ? JOptionPane.ERROR_MESSAGE : JOptionPane.WARNING_MESSAGE;
		JOptionPane.showMessageDialog(frame, message, title, iconType);
	}

	/**
	 * Clears graphical user interface
	 */
	public void clearProblem() {
		resetTableModels();
		txtNumberCriteria.setText("");
		txtEmail.setText("");
		txtMaximumTime.setText("");
		txtProblemDescription.setText("");
		txtProblemName.setText("");
		txtVariablesName.setText("");
		txtNumberVariables.setText("");
	}

	/**
	 * checks inputs for wrong data
	 */
	public boolean validateProblemFields() {
		// If problem name contains spaces or special characters
		Pattern Namepattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher matcherName = Namepattern.matcher(txtProblemName.getText());
		Pattern stringPattern = Pattern.compile("[^a-z ]", Pattern.CASE_INSENSITIVE);
		Pattern numbersPattern = Pattern.compile("[^0-9]", Pattern.CASE_INSENSITIVE);
		Matcher matcher;
		String textboxText = txtProblemName.getText();

		if (textboxText.isEmpty() || matcherName.find() || textboxText.contains(" ")) {
			promptUser("Problem name contains invalid input", true);
			return false;
		}
		matcher = stringPattern.matcher(txtVariablesName.getText());
		textboxText = txtVariablesName.getText();
		if (textboxText.isEmpty()) {
			promptUser("Variables name contains invalid input", true);
			return false;
		}
		matcher = numbersPattern.matcher(txtNumberVariables.getText());
		textboxText = txtNumberVariables.getText();
		if (textboxText.isEmpty() || matcher.find() || Integer.parseInt(textboxText) != tableVariable.getRowCount()) {
			promptUser("Variables number contains invalid input", true);
			return false;
		}
		matcher = numbersPattern.matcher(txtMaximumTime.getText());
		textboxText = txtMaximumTime.getText();
		if (matcher.find()) {
			promptUser("Maximum Time contains invalid input", true);
			return false;
		}
		matcher = numbersPattern.matcher(txtNumberCriteria.getText());
		textboxText = txtNumberCriteria.getText();
		if (textboxText.isEmpty() || matcher.find() || Integer.parseInt(textboxText) != tableCriteria.getRowCount()) {
			promptUser("Number Criteria contains invalid input", true);
			return false;
		}
		matcher = numbersPattern.matcher(txtSolutionKnown.getText());
		textboxText = txtSolutionKnown.getText();
		if (matcher.find()) {
			promptUser("Solution Known contains invalid input", true);
			return false;
		}
		if (txtProblemDescription.getText().isEmpty()) {
			promptUser("Problem description is empty", true);
			return false;
		}
		if (txtEmail.getText().isEmpty()) {
			promptUser("Email is empty", true);
			return false;
		}
		matcher = numbersPattern.matcher(txtBitsPerVariable.getText());
		textboxText = txtBitsPerVariable.getText();
		if (currentProblem.getType().equals("Binary") && (matcher.find() || textboxText.isEmpty())) {
			promptUser("BitsPerVariable contains invalid input",true);
			return false;
		}

		return true;
	}

	/**
	 * Loads a table model for the decision variables with a number of rows inputed
	 * by the user in the JTextField txtNumberCriteria
	 */
	private void loadTableCriteria() {
		tableCriteria.setModel(modelTableButton = new DefaultTableModel(

				new Object[][] {}, new String[] { "Name", "Path", "Add Path" }) {
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class, ButtonColumn.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		try {
			int i = Integer.parseInt(txtNumberCriteria.getText());
			for (; i > 0; i--) {
				modelTableButton.addRow(new Object[] { null, null, null });
			}
			tableCriteria.getColumnModel().getColumn(0).setResizable(false);
			tableCriteria.getColumnModel().getColumn(1).setResizable(false);
			scrollPanelTableCriteria.setViewportView(tableCriteria);
			Action insertPath = new AbstractAction() {
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					int modelRow = Integer.valueOf(e.getActionCommand());
					chooseFileTable(modelRow);
				}
			};
			@SuppressWarnings("unused")
			ButtonColumn buttonColumn = new ButtonColumn(tableCriteria, insertPath, 2);

		} catch (Exception e) {
			promptUser("Invalid data on number of criterias field", true);
		}

	}

	/**
	 * Resets all table models
	 */
	public void resetTableModels() {
		tableVariable = new JTable();
		tableCriteria = new JTable();
		tableCriteria.setRowSelectionAllowed(false);
		tableVariable.setRowSelectionAllowed(false);
		scrollPanelTableVariable.setViewportView(null);
		scrollPanelTableCriteria.setViewportView(null);
		tableCriteria.putClientProperty("terminateEditOnFocusLost", true);
		tableVariable.putClientProperty("terminateEditOnFocusLost", true);

	}

	/**
	 * File chooser for the decision variable Jars
	 * 
	 * @param m
	 */
	private void chooseFileTable(int m) {
		int result = fileChooserJar.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooserJar.getSelectedFile();
			if (selectedFile.exists()) {
				modelTableButton.setValueAt(selectedFile.getAbsolutePath(), m, 1);
			}
		}
	}

	/**
	 * Loads a different table model for the problem variables for each problem type
	 * inputed by the user
	 */
	@SuppressWarnings({ "serial", "rawtypes" })
	private void loadTableVariable() {
		DefaultTableModel modelVariable = modelVariable();
		try {
			int counter = Integer.parseInt(txtNumberVariables.getText());
			for (int i = 0; i < counter; i++) {
				modelVariable.addRow(new Object[] { null, null });
			}
			scrollPanelTableVariable.setViewportView(tableVariable);
			tableVariable.setModel(modelVariable);
		} catch (Exception e) {
			promptUser("Invalid data on Variable number field", true);
		}

	}

	private DefaultTableModel modelVariable() {
		DefaultTableModel modelVariable = null;
		if (currentProblem.getType().equals("Integer")) {
			modelVariable = new DefaultTableModel(new Object[][] {},
					new String[] { "Name", "Minimum", "Maximum", "Forbidden" }) {
				Class[] columnTypes = new Class[] { String.class, Integer.class, Integer.class, Integer.class };

				public Class<?> getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			};
		}
		if (currentProblem.getType().equals("Double")) {
			modelVariable = new DefaultTableModel(new Object[][] {},
					new String[] { "Name", "Minimum", "Maximum", "Forbidden" }) {
				Class[] columnTypes = new Class[] { String.class, Double.class, Double.class, Double.class };

				public Class<?> getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			};
		}
		if (currentProblem.getType().equals("Binary")) {
			modelVariable = new DefaultTableModel(new Object[][] {}, new String[] { "Name", "Forbidden" }) {
				Class[] columnTypes = new Class[] { String.class, Integer.class };

				public Class<?> getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			};
		}
		return modelVariable;
	}

	/**
	 * Loads a already saved problem from a given .xml file name
	 * 
	 * @param xmlFile
	 *            String that indicates the name of the .xml supposed to be loaded
	 *            to the GUI
	 */
	private void loadXmlProblem(String xmlFile) {
		File file = new File(xmlFile);
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(LayoutProblem.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			currentProblem = (LayoutProblem) jaxbUnmarshaller.unmarshal(file);
			comboBoxType.setSelectedItem(currentProblem.getType());
			txtEmail.setText(currentProblem.getEmail());
			txtNumberVariables.setText(Integer.toString(currentProblem.getNumberVariables()));
			txtNumberCriteria.setText(Integer.toString(currentProblem.getNumberCriteria()));
			txtProblemDescription.setText(currentProblem.getProblemDescription());
			txtProblemName.setText(currentProblem.getProblemTitle());
			txtVariablesName.setText(currentProblem.getVariablesName());
			
			if(currentProblem.getMaxWaitingTime()!=0) {
				txtMaximumTime.setText(Integer.toString(currentProblem.getMaxWaitingTime()));
			}
			else {
				txtMaximumTime.setText("");
			}
			if(currentProblem.getSolutionKnown()!=0) {
				txtSolutionKnown.setText(Integer.toString(currentProblem.getSolutionKnown()));
			}
			else {
				txtSolutionKnown.setText("");
			}
			
			loadTableVariable();
			loadTableCriteria();
			List<String> listAlgorithm = currentProblem.getListAlgorithms();

			if (currentProblem.isAutomatic()) {
				chckbxAutomatic.setSelected(true);
				chckbxManual.setSelected(false);
			} else {
				chckbxAutomatic.setSelected(false);
				chckbxManual.setSelected(true);
				loadTableAlgorithm();
				int counter = 0;
				for (int i = 0; i < tableAlgorithms.getRowCount() && counter < listAlgorithm.size(); i++) {
					if (tableAlgorithms.getValueAt(i, 0).toString().equals(listAlgorithm.get(counter))) {
						tableAlgorithms.setValueAt(true, i, 1);
						counter++;
					}
				}
			}
			List<TableRowVariable> listVariable = currentProblem.getListVariable();
			List<TableRowCriteria> listCriteria = currentProblem.getListCriteria();
			TableModel modelVariable = tableVariable.getModel();
			TableModel modelCriteria = tableCriteria.getModel();
			if (currentProblem.getType().equals("Double")) {
				txtBitsPerVariable.setVisible(false);
			lblBitsPerVariable.setVisible(false);
				for (int i = 0; i < tableVariable.getRowCount(); i++) {
					modelVariable.setValueAt(listVariable.get(i).getName(), i, 0);
					modelVariable.setValueAt(Double.parseDouble(listVariable.get(i).getMinimo()), i, 1);
					modelVariable.setValueAt(Double.parseDouble(listVariable.get(i).getMaximo()), i, 2);
					if (listVariable.get(i).getForbidden() != null)
						modelVariable.setValueAt(Double.parseDouble(listVariable.get(i).getForbidden()), i, 3);
				}
			}

			if (currentProblem.getType().equals("Integer")) {
				txtBitsPerVariable.setVisible(false);
				lblBitsPerVariable.setVisible(false);
				for (int i = 0; i < tableVariable.getRowCount(); i++) {
					modelVariable.setValueAt(listVariable.get(i).getName(), i, 0);
					modelVariable.setValueAt(Integer.parseInt(listVariable.get(i).getMinimo()), i, 1);
					modelVariable.setValueAt(Integer.parseInt(listVariable.get(i).getMaximo()), i, 2);
					if (listVariable.get(i).getForbidden() != null)
						modelVariable.setValueAt(Integer.parseInt(listVariable.get(i).getForbidden()), i, 3);
				}
			}
			if (currentProblem.getType().equals("Binary")) {
				txtBitsPerVariable.setVisible(true);
				lblBitsPerVariable.setVisible(true);
				txtBitsPerVariable.setText(Integer.toString(currentProblem.getBitsPerVariable()));
				for (int i = 0; i < tableVariable.getRowCount(); i++) {
					modelVariable.setValueAt(listVariable.get(i).getName(), i, 0);
					if (listVariable.get(i).getForbidden() != null)
						modelVariable.setValueAt(Integer.parseInt(listVariable.get(i).getForbidden()), i, 1);
				}
			}
			for (int i = 0; i < tableCriteria.getRowCount(); i++) {
				modelCriteria.setValueAt(listCriteria.get(i).getName(), i, 0);
				modelCriteria.setValueAt(listCriteria.get(i).getPath(), i, 1);

			}
		} catch (Exception e) {
			e.printStackTrace();
			clearProblem();
		}

	}

	/**
	 * Reads the problem inputed by the user on the optimization platform
	 */
	private void readProblemFromInterface() {
		String problemType = currentProblem.getType();
		currentProblem = new LayoutProblem();
		if (!txtMaximumTime.getText().isEmpty())
			currentProblem.setMaxWaitingTime(Integer.parseInt(txtMaximumTime.getText()));
		if (!txtSolutionKnown.getText().isEmpty())
			currentProblem.setSolutionKnown(Integer.parseInt(txtSolutionKnown.getText()));
		currentProblem.setVariablesName(txtVariablesName.getText());
		currentProblem.setNumberVariables(Integer.parseInt(txtNumberVariables.getText()));
		currentProblem.setNumberCriteria(Integer.parseInt(txtNumberCriteria.getText()));
		currentProblem.setProblemDescription(txtProblemDescription.getText());
		currentProblem.setProblemTitle(txtProblemName.getText());
		currentProblem.setEmail(txtEmail.getText());
		currentProblem.setType(problemType);
		if (chckbxAutomatic.isSelected()) {
			currentProblem.setAutomatic(true);
		} else {
			currentProblem.setAutomatic(false);
		}
		ArrayList<String> listAlgorithms = new ArrayList<String>();
		if (chckbxManual.isSelected())
			for (int i = 0; i < tableAlgorithms.getRowCount(); i++) {
				if ((Boolean) tableAlgorithms.getValueAt(i, 1))
					listAlgorithms.add(tableAlgorithms.getValueAt(i, 0).toString());
			}
		currentProblem.setListAlgorithms(listAlgorithms);
		ArrayList<TableRowVariable> listVariable = new ArrayList<TableRowVariable>();
		ArrayList<TableRowCriteria> listCriteria = new ArrayList<TableRowCriteria>();
		for (int i = 0; i < tableCriteria.getRowCount(); i++) {
			TableRowCriteria m = new TableRowCriteria();
			m.setName(tableCriteria.getValueAt(i, 0).toString());
			m.setPath(tableCriteria.getValueAt(i, 1).toString());
			listCriteria.add(m);
		}
		if (problemType == "Integer") {
			for (int i = 0; i < tableVariable.getRowCount(); i++) {
				TableRowVariable m = new TableRowVariable();
				m.setName((String) tableVariable.getValueAt(i, 0));
				m.setMinimo(Integer.toString((int) tableVariable.getValueAt(i, 1)));
				m.setMaximo(Integer.toString((int) tableVariable.getValueAt(i, 2)));
				if (tableVariable.getValueAt(i, 3) != null) {
					m.setForbidden(Integer.toString((int) tableVariable.getValueAt(i, 3)));

				} else {
					m.setForbidden(null);
				}
				listVariable.add(m);
			}
		}
		if (problemType == "Double") {
			for (int i = 0; i < tableVariable.getRowCount(); i++) {
				TableRowVariable m = new TableRowVariable();
				m.setName((String) tableVariable.getValueAt(i, 0));
				m.setMinimo(Double.toString((Double) tableVariable.getValueAt(i, 1)));
				m.setMaximo(Double.toString((Double) tableVariable.getValueAt(i, 2)));
				if (tableVariable.getValueAt(i, 3) != null) {
					m.setForbidden(Double.toString((Double) tableVariable.getValueAt(i, 3)));
				} else {
					m.setForbidden(null);
				}
				listVariable.add(m);
			}
		}
		if (problemType == "Binary") {
			currentProblem.setBitsPerVariable(Integer.parseInt(txtBitsPerVariable.getText()));
			for (int i = 0; i < tableVariable.getRowCount(); i++) {
				TableRowVariable m = new TableRowVariable();
				m.setName((String) tableVariable.getValueAt(i, 0));

				if (tableVariable.getValueAt(i, 1) != null) {
					m.setForbidden(Integer.toString((int) tableVariable.getValueAt(i, 1)));
				} else {
					m.setForbidden(null);
				}
				listVariable.add(m);
			}
		}
		currentProblem.setListVariable(listVariable);
		currentProblem.setListCriteria(listCriteria);
	}

	/**
	 * Saves a problem definition inputed by the user to a .xml file on the input
	 * directory
	 */
	private void saveXmlProblem() {
		if (validateProblemFields()) {
			readProblemFromInterface();
			try {
				Calendar calobj = Calendar.getInstance();
				DateFormat df = new SimpleDateFormat("dd-MM-yy HH-mm-ss");

				File file = new File(PATH_INPUT + "savedProblems" + File.separator + txtProblemName.getText()
						+ df.format(calobj.getTime()) + ".xml");
				JAXBContext jaxbContext = JAXBContext.newInstance(LayoutProblem.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

				// output pretty printed
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

				jaxbMarshaller.marshal(currentProblem, file);
				jaxbMarshaller.marshal(currentProblem, System.out);

			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Loads the table that shows available algorithms capable to solve the problem
	 * type inputed
	 */
	private void loadTableAlgorithm() {
		OptimizationProcess k = new OptimizationProcess();
		try {
			ArrayList<String> listAlgorithms = (ArrayList<String>) k
					.getAlgorithmsFor(currentProblem.getType());
			DefaultTableModel modelAlgorithms = modelAlgorithms();
			for (int i = 0; i < listAlgorithms.size(); i++) {
				modelAlgorithms.addRow(new Object[] { listAlgorithms.get(i), false });
			}
			scrollPanelAlgorithms.setViewportView(tableAlgorithms);
			tableAlgorithms.setModel(modelAlgorithms);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private DefaultTableModel modelAlgorithms() {
		tableAlgorithms = new JTable();
		DefaultTableModel modelAlgorithms;
		tableAlgorithms.setModel(
				modelAlgorithms = new DefaultTableModel(new Object[][] {}, new String[] { "Algorithms", "Active" }) {
					private static final long serialVersionUID = 1L;
					@SuppressWarnings("rawtypes")
					Class[] columnTypes = new Class[] { String.class, Boolean.class };

					public Class<?> getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}
				});
		return modelAlgorithms;
	}

	/**
	 * Runs problem
	 */
	private void runDemo() {
		OptimizationProcess op = new OptimizationProcess();
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					op.run(currentProblem);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
	}

	/**
	 * Allows the user to check the solutions discovered by the platform thru the
	 * external DataVisualization class
	 * 
	 * @param problem
	 *            LayoutProblem
	 */
	private void visualizeDemo(LayoutProblem problem) {
		if (problem != null) {
			List<String> rfFilePaths = new ArrayList<String>();
			List<String> rsFilePaths = new ArrayList<String>();

			File folder = new File(Builders.BASE_DIRECTORY + problem.getProblemTitle() + "/referenceFronts");
			File[] listOfFiles = folder.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {
				// listOfFiles[i].getName().split(".").length > 2 is to prevent getting the
				// DoubleProblem.rf/BinaryProblem.rf, etc files
				// and take into account the rf and rs files for each specific algorithm, for
				// example DoubleProblem.MOEAD.rf
				if (listOfFiles[i].isFile() && listOfFiles[i].getName().split("\\.").length > 2) {
					if (listOfFiles[i].getName().endsWith(".rf")) {
						rfFilePaths.add(Builders.BASE_DIRECTORY + problem.getProblemTitle() + "/referenceFronts/"
								+ listOfFiles[i].getName());
					} else if (listOfFiles[i].getName().endsWith(".rs")) {
						rsFilePaths.add(Builders.BASE_DIRECTORY + problem.getProblemTitle() + "/referenceFronts/"
								+ listOfFiles[i].getName());
					}
				}
			}

			List<String> decisionVariables = new ArrayList<String>();
			for (int i = 0; i < tableVariable.getModel().getRowCount(); i++) {
				decisionVariables.add((String) tableVariable.getModel().getValueAt(i, 0));
			}

			DataVisualization dv = new DataVisualization(problem.getListAlgorithms(), rsFilePaths, rfFilePaths,
					decisionVariables, problem.getSolutionKnown());

			if (dv.run()) {
				dv.addBackButtonActionListener(frame, mainPanel, dv);
				frame.remove(mainPanel);
				frame.setContentPane(new Container());
				frame.getContentPane().setLayout(new BorderLayout());
				frame.getContentPane().add(dv, BorderLayout.CENTER);
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.revalidate();
				frame.repaint();
			} else {
				promptUser(dv.getBuildErrorMessage(), true);
			}
		} else {
			promptUser("Before trying to visualize results, you must load a Problem or define a new one and run it.",
					true);
		}
	}
}