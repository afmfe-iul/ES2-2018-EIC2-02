package main.optimization.platform.gui;

import java.awt.EventQueue;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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

import javax.swing.JCheckBox;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;

public class MainLayout {
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
	private String pathInput;
	private String pathOutput;
	private JTextField txtSolutionKnown;
	private JMenuBar menuBar;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				MainLayout window = new MainLayout();
				window.initialize();
				window.frame.setVisible(true);
			}
		});
	}

	public void loadAdminCfgFile() {
		File file = new File("config.xml");
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(AdminXmlObject.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			AdminXmlObject adminXmlObject = (AdminXmlObject) jaxbUnmarshaller.unmarshal(file);
			emailAdmin = adminXmlObject.getEmail();
			pathInput = adminXmlObject.getpathInput();
			pathOutput = adminXmlObject.getpathOutput();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public void initialize() {
		loadAdminCfgFile();
		FileNameExtensionFilter filterXml = new FileNameExtensionFilter("Xml files", "xml", "xml");
		frame = new JFrame("Optimizer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setBounds(320, 30, 1000, 667);
		fileChooserJar = new JFileChooser();
		JFileChooser fileChooserXml = new JFileChooser();
		File k = new File("SavedProblems");
		fileChooserXml.setCurrentDirectory(k);
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
				loadTableVariable();

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
				readProblemFromInterface();
				runDemo();
				// opens data agreement
				LayoutDataAgreement.initialize();
				// opens FAQ page
				LayoutFAQPage.initialize();
				;
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

		JLabel lblAlgorithms = new JLabel("Algorithms");

		lblOptimizationImpliesMinimizing = new JLabel("Optimization implies minimizing jars results");

		scrollPanelAlgorithms = new JScrollPane();

		txtSolutionKnown = new JTextField();
		txtSolutionKnown.setColumns(10);

		JLabel lblSolutionKnown = new JLabel("Solution known");
		Border emptyBorder = BorderFactory.createEmptyBorder();
		JToolBar toolBar = new JToolBar();
		JButton bttFaq = new JButton("Faq");
		JButton bttAgreement = new JButton("Agreement");
		bttFaq.setBorder(emptyBorder);
		bttAgreement.setBorder(emptyBorder);
		toolBar.add(bttFaq);
		toolBar.addSeparator();
		toolBar.add(bttAgreement);

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addContainerGap()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
										.createSequentialGroup().addGroup(groupLayout
												.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
														.createSequentialGroup().addGroup(groupLayout
																.createParallelGroup(Alignment.TRAILING, false)
																.addGroup(groupLayout.createSequentialGroup()
																		.addComponent(comboBoxType,
																				GroupLayout.PREFERRED_SIZE, 80,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addComponent(txtNumberVariables,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
																.addGroup(groupLayout.createSequentialGroup().addGap(21)
																		.addComponent(lblType).addPreferredGap(
																				ComponentPlacement.RELATED,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(lblVariablesNumber)))
														.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																.addGroup(groupLayout.createSequentialGroup()
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addComponent(txtVariablesName,
																				GroupLayout.PREFERRED_SIZE, 230,
																				GroupLayout.PREFERRED_SIZE))
																.addGroup(
																		groupLayout.createSequentialGroup().addGap(74)
																				.addComponent(lblNameVariables))))
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(
																txtProblemDescription, GroupLayout.DEFAULT_SIZE, 447,
																Short.MAX_VALUE)
														.addComponent(lblDescrio)
														.addGroup(groupLayout.createSequentialGroup().addGroup(
																groupLayout.createParallelGroup(Alignment.TRAILING)
																		.addComponent(
																				lblNameProblem)
																		.addComponent(lblEmail))
																.addPreferredGap(ComponentPlacement.UNRELATED)
																.addGroup(groupLayout
																		.createParallelGroup(Alignment.LEADING, false)
																		.addComponent(txtProblemName).addComponent(
																				txtEmail, GroupLayout.DEFAULT_SIZE, 363,
																				Short.MAX_VALUE)))
														.addComponent(scrollPanelTableVariable,
																GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)))
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(
														groupLayout
																.createSequentialGroup().addPreferredGap(
																		ComponentPlacement.RELATED)
																.addGroup(groupLayout.createParallelGroup(
																		Alignment.LEADING, false).addComponent(
																				txtMaximumTime)
																		.addComponent(lblMaximumtime,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE))
																.addPreferredGap(
																		ComponentPlacement.RELATED,
																		GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
												.addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout
														.createParallelGroup(Alignment.LEADING)
														.addGroup(groupLayout.createSequentialGroup().addGap(18)
																.addGroup(groupLayout
																		.createParallelGroup(Alignment.LEADING)
																		.addGroup(groupLayout
																				.createSequentialGroup()
																				.addComponent(
																						scrollPanelAlgorithms,
																						GroupLayout.DEFAULT_SIZE, 154,
																						Short.MAX_VALUE)
																				.addPreferredGap(
																						ComponentPlacement.RELATED))
																		.addComponent(btnOpenXmlProblem)
																		.addComponent(btnSaveXmlProblemL)))
														.addGroup(groupLayout.createSequentialGroup()
																.addPreferredGap(ComponentPlacement.RELATED, 33,
																		Short.MAX_VALUE)
																.addGroup(groupLayout.createParallelGroup(
																		Alignment.TRAILING)
																		.addGroup(groupLayout.createSequentialGroup()
																				.addGroup(groupLayout
																						.createParallelGroup(
																								Alignment.TRAILING,
																								false)
																						.addComponent(
																								chckbxAutomatic,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								chckbxManual,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addGroup(groupLayout
																								.createSequentialGroup()
																								.addComponent(
																										lblAlgorithms)
																								.addGap(11)))
																				.addGap(29))
																		.addGroup(groupLayout.createSequentialGroup()
																				.addGroup(groupLayout
																						.createParallelGroup(
																								Alignment.LEADING,
																								false)
																						.addGroup(groupLayout
																								.createSequentialGroup()
																								.addGap(18)
																								.addComponent(
																										btnVisDemo))
																						.addGroup(groupLayout
																								.createSequentialGroup()
																								.addGap(29)
																								.addComponent(
																										btnRunDemo)))
																				.addGap(18)))))
														.addGap(11)))
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createSequentialGroup()
														.addComponent(txtNumberCriteria, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addComponent(btnCriteria))
												.addComponent(scrollPanelTableCriteria, GroupLayout.PREFERRED_SIZE, 223,
														GroupLayout.PREFERRED_SIZE)
												.addGroup(groupLayout.createSequentialGroup().addGap(24)
														.addComponent(lblSolutionKnown).addGap(18)
														.addComponent(txtSolutionKnown, GroupLayout.PREFERRED_SIZE, 76,
																GroupLayout.PREFERRED_SIZE)))
										.addGap(132)).addComponent(lblOptimizationImpliesMinimizing)
										.addComponent(btnloadTableVariable).addComponent(toolBar,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup().addGap(5)
				.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblVariablesNumber)
						.addComponent(lblType).addComponent(lblNameVariables).addComponent(lblMaximumtime,
								GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
								.addGap(5).addComponent(lblDescrio).addGap(7)
								.addComponent(txtProblemDescription, GroupLayout.PREFERRED_SIZE, 160,
										GroupLayout.PREFERRED_SIZE)
								.addGap(23).addComponent(lblOptimizationImpliesMinimizing))
						.addGroup(groupLayout.createSequentialGroup().addComponent(btnRunDemo)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnVisDemo)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblAlgorithms)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(chckbxAutomatic)
								.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(chckbxManual)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(scrollPanelAlgorithms, GroupLayout.PREFERRED_SIZE, 210,
										GroupLayout.PREFERRED_SIZE)
								.addGap(50).addComponent(btnSaveXmlProblemL)
								.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnOpenXmlProblem))
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(scrollPanelTableCriteria, GroupLayout.PREFERRED_SIZE, 222,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblSolutionKnown).addComponent(txtSolutionKnown,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))))
				.addContainerGap(41, Short.MAX_VALUE)));

		resetTableModels();
		frame.getContentPane().setLayout(groupLayout);

		menuBar = new JMenuBar();
		// menuBar.add(bttAgreement);
		frame.setJMenuBar(menuBar);
	}

	public void loadData() {
		txtProblemName.setText("AntiSpamFilterProblem");
		txtVariablesName.setText("Anti Spam Rules");
		comboBoxType.setSelectedItem("Double");
		txtEmail.setText("demo@email.com");
		File file = new File("experimentsBaseDirectory/TestProblem/rules.cf");
		DefaultTableModel modelManual = new DefaultTableModel(new Object[][] {},
				new String[] { "Name", "Rule", "Minimum", "Maximum", "Forbidden" }) {
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class, Double.class, Double.class, Double.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		};

		try {
			Scanner sc = new Scanner(file);
			int i = 0;
			while (sc.hasNextLine()) {
				modelManual.addRow(new Object[] { "Name" + i, sc.nextLine(), -5.0, 5.0 });
				i++;
			}
			sc.close();
			tableVariable.setModel(modelManual);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		txtNumberVariables.setText(String.valueOf(modelManual.getRowCount()));
		scrollPanelTableVariable.setViewportView(tableVariable);

	}

	public void promptUser(String message, boolean error) {

		String title = error ? "Error!" : "Warning!";
		int iconType = error ? JOptionPane.ERROR_MESSAGE : JOptionPane.WARNING_MESSAGE;
		JOptionPane.showMessageDialog(frame, message, title, iconType);
	}

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

	public boolean validateProblemFields() {
		// If problem name contains spaces or special characters
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(txtProblemName.getText());
		if (m.find() || txtProblemName.getText().contains(" ")) {
			return false;
		}

		return true;
	}

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
	}

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

	private void chooseFileTable(int m) {
		int result = fileChooserJar.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooserJar.getSelectedFile();
			if (selectedFile.exists()) {
				modelTableButton.setValueAt(selectedFile.getAbsolutePath(), m, 1);
			}
		}
	}

	@SuppressWarnings({ "serial", "rawtypes" })
	private void loadTableVariable() {
		DefaultTableModel modelVariable = null;
		if (comboBoxType.getSelectedItem().toString() == "Integer") {
			modelVariable = new DefaultTableModel(new Object[][] {},
					new String[] { "Name", "Rule", "Minimum", "Maximum", "Forbidden" }) {

				Class[] columnTypes = new Class[] { String.class, String.class, Integer.class, Integer.class,
						Integer.class };

				public Class<?> getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			};
		}
		if (comboBoxType.getSelectedItem().toString() == "Double") {
			modelVariable = new DefaultTableModel(new Object[][] {},
					new String[] { "Name", "Rule", "Minimum", "Maximum", "Forbidden" }) {

				Class[] columnTypes = new Class[] { String.class, String.class, Double.class, Double.class,
						Double.class };

				public Class<?> getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			};
		}
		if (comboBoxType.getSelectedItem().toString() == "Boolean") {
			modelVariable = new DefaultTableModel(new Object[][] {}, new String[] { "Name", "Rule", "Forbidden" }) {

				Class[] columnTypes = new Class[] { String.class, String.class, Boolean.class };

				public Class<?> getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			};
		}

		int counter = Integer.parseInt(txtNumberVariables.getText());
		for (int i = 0; i < counter; i++) {
			modelVariable.addRow(new Object[] { null, null });
		}
		scrollPanelTableVariable.setViewportView(tableVariable);
		tableVariable.setModel(modelVariable);
	}

	private void loadXmlProblem(String xmlFile) {
		File file = new File(xmlFile);
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(LayoutProblem.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			currentProblem = (LayoutProblem) jaxbUnmarshaller.unmarshal(file);
			// System.out.println(problem);
			comboBoxType.setSelectedItem(currentProblem.getType());
			txtEmail.setText(currentProblem.getEmail());
			txtNumberVariables.setText(Integer.toString(currentProblem.getNumberVariables()));
			txtNumberCriteria.setText(Integer.toString(currentProblem.getNumberCriteria()));
			txtProblemDescription.setText(currentProblem.getProblemDescription());
			txtProblemName.setText(currentProblem.getProblemTitle());
			txtVariablesName.setText(currentProblem.getVariablesName());
			txtMaximumTime.setText(Integer.toString(currentProblem.getMaxWaitingTime()));
			txtSolutionKnown.setText(Integer.toString(currentProblem.getSolutionKnown()));
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
			if (comboBoxType.getSelectedItem() == "Double")
				for (int i = 0; i < tableVariable.getRowCount(); i++) {
					modelVariable.setValueAt(listVariable.get(i).getName(), i, 0);
					modelVariable.setValueAt(listVariable.get(i).getRule(), i, 1);
					modelVariable.setValueAt(Double.parseDouble(listVariable.get(i).getMinimo()), i, 2);
					modelVariable.setValueAt(Double.parseDouble(listVariable.get(i).getMaximo()), i, 3);
					if (listVariable.get(i).getForbidden() != null)
						modelVariable.setValueAt(Double.parseDouble(listVariable.get(i).getForbidden()), i, 4);
				}
			if (comboBoxType.getSelectedItem() == "Integer")
				for (int i = 0; i < tableVariable.getRowCount(); i++) {
					modelVariable.setValueAt(listVariable.get(i).getName(), i, 0);
					modelVariable.setValueAt(listVariable.get(i).getRule(), i, 1);
					modelVariable.setValueAt(Integer.parseInt(listVariable.get(i).getMinimo()), i, 2);
					modelVariable.setValueAt(Integer.parseInt(listVariable.get(i).getMaximo()), i, 3);
					if (listVariable.get(i).getForbidden() != null)
						modelVariable.setValueAt(Integer.parseInt(listVariable.get(i).getForbidden()), i, 4);
				}
			if (comboBoxType.getSelectedItem() == "Boolean")
				for (int i = 0; i < tableVariable.getRowCount(); i++) {
					modelVariable.setValueAt(listVariable.get(i).getName(), i, 0);
					modelVariable.setValueAt(listVariable.get(i).getRule(), i, 1);
					if (listVariable.get(i).getForbidden() != null)
						modelVariable.setValueAt(Boolean.parseBoolean(listVariable.get(i).getForbidden()), i, 4);
				}
			for (int i = 0; i < tableCriteria.getRowCount(); i++) {
				modelCriteria.setValueAt(listCriteria.get(i).getName(), i, 0);
				modelCriteria.setValueAt(listCriteria.get(i).getPath(), i, 1);

			}
		} catch (Exception e) {
			clearProblem();
		}

	}

	private void readProblemFromInterface() {
		String problemType = comboBoxType.getSelectedItem().toString();
		currentProblem = new LayoutProblem();
		if (txtMaximumTime.getText().isEmpty()) {
			// TODO verificar se este valor creio que não seja o correto
			currentProblem.setMaxWaitingTime(0);
		} else {
			currentProblem.setMaxWaitingTime(Integer.parseInt(txtMaximumTime.getText()));
		}

		currentProblem.setSolutionKnown(Integer.parseInt(txtSolutionKnown.getText()));
		currentProblem.setVariablesName(txtVariablesName.getText());
		currentProblem.setNumberVariables(Integer.parseInt(txtNumberVariables.getText()));
		currentProblem.setNumberCriteria(Integer.parseInt(txtNumberCriteria.getText()));
		currentProblem.setProblemDescription(txtProblemDescription.getText());
		currentProblem.setProblemTitle(txtProblemName.getText());
		currentProblem.setEmail(txtEmail.getText());
		currentProblem.setType(comboBoxType.getSelectedItem().toString());
		if (chckbxAutomatic.isSelected()) {
			currentProblem.setAutomatic(true);
		} else {
			currentProblem.setAutomatic(false);
		}
		ArrayList<String> listAlgorithms = new ArrayList<String>();
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
				m.setRule((String) tableVariable.getValueAt(i, 1));
				m.setMinimo(Integer.toString((int) tableVariable.getValueAt(i, 2)));
				m.setMaximo(Integer.toString((int) tableVariable.getValueAt(i, 3)));
				m.setForbidden(Integer.toString((int) tableVariable.getValueAt(i, 4)));
				listVariable.add(m);
			}
		}
		if (problemType == "Double") {
			for (int i = 0; i < tableVariable.getRowCount(); i++) {
				TableRowVariable m = new TableRowVariable();
				if (tableVariable.getValueAt(i, 0) != null) {
					m.setName((String) tableVariable.getValueAt(i, 0));
				} else {
					m.setName(null);
				}
				m.setRule((String) tableVariable.getValueAt(i, 1));
				m.setMinimo(Double.toString((Double) tableVariable.getValueAt(i, 2)));
				m.setMaximo(Double.toString((Double) tableVariable.getValueAt(i, 3)));
				if (tableVariable.getValueAt(i, 4) != null) {
					m.setForbidden(Double.toString((Double) tableVariable.getValueAt(i, 4)));
				} else {
					m.setForbidden(null);
				}
				listVariable.add(m);
			}
		}
		if (problemType == "Boolean") {
			for (int i = 0; i < tableVariable.getRowCount(); i++) {
				TableRowVariable m = new TableRowVariable();
				m.setName((String) tableVariable.getValueAt(i, 0));
				m.setRule((String) tableVariable.getValueAt(i, 1));
				m.setForbidden(Boolean.toString((Boolean) tableVariable.getValueAt(i, 2)));
				listVariable.add(m);
			}
		}
		currentProblem.setListVariable(listVariable);
		currentProblem.setListCriteria(listCriteria);
	}

	private void saveXmlProblem() {
		if (validateProblemFields()) {
			readProblemFromInterface();
			try {
				Calendar calobj = Calendar.getInstance();
				DateFormat df = new SimpleDateFormat("dd-MM-yy HH-mm-ss");
				File file = new File(
						"SavedProblems/" + txtProblemName.getText() + df.format(calobj.getTime()) + ".xml");
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

	private void loadTableAlgorithm() {
		OptimizationProcess k = new OptimizationProcess();
		try {
			ArrayList<String> listAlgorithms = (ArrayList<String>) k
					.getAlgorithmsFor((String) comboBoxType.getSelectedItem());
			tableAlgorithms = new JTable();
			DefaultTableModel modelTable;
			tableAlgorithms.setModel(modelTable = new DefaultTableModel(

					new Object[][] {}, new String[] { "Algorithms", "Active" }) {
				private static final long serialVersionUID = 1L;
				@SuppressWarnings("rawtypes")
				Class[] columnTypes = new Class[] { String.class, Boolean.class };

				public Class<?> getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
			for (int i = 0; i < listAlgorithms.size(); i++) {
				modelTable.addRow(new Object[] { listAlgorithms.get(i), false });
			}
			scrollPanelAlgorithms.setViewportView(tableAlgorithms);
			tableAlgorithms.setModel(modelTable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	// TODO Still hardcoded needs to get the information from the interface in the
	// future
	private void visualizeDemo(LayoutProblem problem) {
		List<String> rsFilePaths = new ArrayList<String>();
		rsFilePaths.add("experimentsBaseDirectory/" + txtProblemName.getText() + "/"
				+ (String) comboBoxType.getSelectedItem() + "Problem.rs");
		List<String> rfFilePaths = new ArrayList<String>();
		rfFilePaths.add("experimentsBaseDirectory/" + txtProblemName.getText() + "/"
				+ (String) comboBoxType.getSelectedItem() + "Problem.rf");

		List<String> decisionVariables = new ArrayList<String>();
		for (int i = 0; i < tableVariable.getModel().getRowCount(); i++) {
			decisionVariables.add((String) tableVariable.getModel().getValueAt(i, 1));
		}

		DataVisualization dv = new DataVisualization(problem.getListAlgorithms(), rsFilePaths, rfFilePaths,
				decisionVariables, 6);

		if (dv.run()) {
			JFrame frame = new JFrame("Graphical Visualization");
			frame.getContentPane().add(dv);
			WindowListener exitListener = new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					frame.remove(dv);
				}
			};
			frame.addWindowListener(exitListener);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			frame.setVisible(true);
		}
	}
}