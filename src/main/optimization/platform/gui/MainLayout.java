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
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
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
import java.awt.ScrollPane;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class MainLayout {

	public JFrame frame;
	public JTable tableVariable;
	public JTable tableCriteria;
	public JDialog progressDialog;
	public JScrollPane scrollPanelTableVariable;
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
	private JScrollPane scrollPanelTableCriteria;
	private JFileChooser fileChooserJar;
	private DefaultTableModel modelTableButton;
	private JTextField txtNumberCriteria;
	private JCheckBox chckbxManual;
	private JCheckBox chckbxAutomatic;
	private JLabel lblOptimizationImpliesMinimizing;
	private String emailAdmin;
	private String pathInput;
	private String pathOutput;
	
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
			emailAdmin=adminXmlObject.getEmail();
			pathInput=adminXmlObject.getpathInput();
			pathOutput=adminXmlObject.getpathOutput();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
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

//		frame.addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowClosing(WindowEvent e) {
//				int confirm = JOptionPane.showOptionDialog(null, "Do you wish to save the File Paths?",
//						"Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
//				if (confirm == 0) {
//				}
//				System.exit(0);
//			}
//		});
//		btnSpam.setIcon(new ImageIcon(MainLayout.class.getResource("/imageWindowBuilder/foldericon.png")));
		 fileChooserJar = new JFileChooser();
		JFileChooser fileChooserXml= new JFileChooser();
		File k= new File("SavedProblems");
		fileChooserXml.setCurrentDirectory(k);
//		JFileChooser fileChooserOther = new JFileChooser();
		FileNameExtensionFilter filterJar = new FileNameExtensionFilter("jar Files", "jar", "jar");
//		FileNameExtensionFilter filter = new FileNameExtensionFilter("LOG Files", "log", "log");
		fileChooserJar.setFileFilter(filterJar);
		scrollPanelTableVariable = new JScrollPane();
		scrollPanelTableCriteria = new JScrollPane();

		comboBoxType = new JComboBox<String>();
		comboBoxType.addItem("Integer");
		comboBoxType.addItem("Boolean");
		comboBoxType.addItem("Double");
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
		lblMaximumtime = new JLabel("Maximum waiting time");
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
				createXml();
			}
		});
		
		JButton btnOpenXmlProblem = new JButton("Open problem");
		btnOpenXmlProblem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileChooserXml.setFileFilter(filterXml);
				int result = fileChooserXml.showOpenDialog(btnOpenXmlProblem);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooserXml.getSelectedFile();
					if(selectedFile.exists()){
						loadXmlProblem(selectedFile.getAbsolutePath());
					}
				
				}
			}
		});
		
		JButton btnRunDemo = new JButton("Run Demo");
		btnRunDemo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				runDemo();
			}
		});
		
		JButton btnVisDemo = new JButton("Visualize Demo");
		btnVisDemo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				visualizeDemo();
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
		chckbxAutomatic.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(chckbxAutomatic.isSelected()) {
					chckbxManual.setSelected(false);
					
				}
			}
		});
		
		 chckbxManual = new JCheckBox("Manual");
		chckbxManual.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(chckbxManual.isSelected()) {
					chckbxAutomatic.setSelected(false);
					
				}
			}
		});
		
		JLabel lblAlgorithms = new JLabel("Algorithms");
		
		lblOptimizationImpliesMinimizing = new JLabel("Optimization implies minimizing jars results");
		
		
		
		// TODO END of demo code
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(42)
							.addComponent(btnloadTableVariable))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(txtProblemDescription, GroupLayout.PREFERRED_SIZE, 467, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addComponent(lblDescrio)
									.addGroup(groupLayout.createSequentialGroup()
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
											.addComponent(lblNameProblem)
											.addComponent(lblEmail))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addComponent(txtEmail, GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
											.addComponent(txtProblemName, GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)))))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(btnOpenXmlProblem)
										.addComponent(btnSaveXmlProblemL)))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(41)
									.addComponent(lblOptimizationImpliesMinimizing))))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
									.addComponent(scrollPanelTableVariable, GroupLayout.PREFERRED_SIZE, 446, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addComponent(chckbxAutomatic)
											.addComponent(chckbxManual)
											.addComponent(btnRunDemo)
											.addComponent(btnVisDemo))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblAlgorithms)
											.addGap(41)))
									.addGap(18)
									.addComponent(scrollPanelTableCriteria, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(comboBoxType, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(txtNumberVariables, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(21)
											.addComponent(lblType)
											.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(lblVariablesNumber)))
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(txtVariablesName, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(74)
											.addComponent(lblNameVariables)))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblMaximumtime, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtMaximumTime, 92, 92, 92))
									.addPreferredGap(ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
									.addComponent(txtNumberCriteria, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnCriteria)))
							.addGap(173)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblVariablesNumber)
						.addComponent(lblType)
						.addComponent(lblNameVariables)
						.addComponent(lblMaximumtime, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBoxType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtNumberVariables, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtVariablesName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtMaximumTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtNumberCriteria, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCriteria))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(btnRunDemo)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnVisDemo)
							.addGap(17)
							.addComponent(lblAlgorithms)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxAutomatic)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(chckbxManual)
							.addGap(60))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnloadTableVariable)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPanelTableCriteria, Alignment.TRAILING, 0, 0, Short.MAX_VALUE)
								.addComponent(scrollPanelTableVariable, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
							.addGap(18)))
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNameProblem)
								.addComponent(txtProblemName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblEmail))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblDescrio))
						.addComponent(btnSaveXmlProblemL))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtProblemDescription, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnOpenXmlProblem)
							.addGap(34)
							.addComponent(lblOptimizationImpliesMinimizing)))
					.addGap(66))
		);

		resetTableModels();
		frame.getContentPane().setLayout(groupLayout);
	}
	public void loadData() {
		txtProblemName.setText("AntiSpamFilterProblem");
		txtVariablesName.setText("Anti Spam Rules");
		comboBoxType.setSelectedItem("Double");
		txtEmail.setText("demo@email.com");
		File file = new File("experimentsBaseDirectory/TestProblem/rules.cf");
		DefaultTableModel modelManual = new DefaultTableModel(
			new Object[][]{},
			new String[] {
				"Name","Rule",  "Minimum", "Maximum", "Forbidden"}
		) {
		
			Class[] columnTypes = new Class[] {
					String.class,String.class, Double.class, Double.class, Double.class
			};
		public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
		}
		};
		
		try {
	        Scanner sc = new Scanner(file);
	        int i=0;
	        while(sc.hasNextLine()){
	        	modelManual.addRow( new Object[]{"Name"+i,sc.nextLine(),-5.0, 5.0});
	        	i++;
	        } 
	        sc.close();
	        tableVariable.setModel(modelManual);
	    }catch (FileNotFoundException e) {
	    	e.printStackTrace();
	    }
		txtNumberVariables.setText(String.valueOf(modelManual.getRowCount()));
		scrollPanelTableVariable.setViewportView(tableVariable);

	}
	
	public void promptUser(String message, boolean error){

		String title = error ?  "Error!" : "Warning!";
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
		//If problem name contains spaces or special characters
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(txtProblemName.getText());
		if (m.find() || txtProblemName.getText().contains(" ")) {
			return false;
		}
		
		return true;
	}
	private void loadTableCriteria() {
		// TODO Auto-generated method stub

		tableCriteria.setModel( modelTableButton = new DefaultTableModel(
		
			new Object[][]{},
			new String[] {
			"Solution known","Name","Path", "Add Path"}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class,String.class,String.class, ButtonColumn.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		int i = Integer.parseInt(txtNumberCriteria.getText());
		for (;i>0;i--){
		modelTableButton.addRow(new Object[]{null,null, null,null});
		}
		tableCriteria.getColumnModel().getColumn(0).setResizable(false);
		tableCriteria.getColumnModel().getColumn(1).setResizable(false);
		scrollPanelTableCriteria.setViewportView(tableCriteria);
		Action insertPath = new AbstractAction()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        JTable table = (JTable)e.getSource();
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        chooseFileTable(modelRow);
		        //((DefaultTableModel)table.getModel()).removeRow(modelRow);
		    }
		};
		ButtonColumn buttonColumn = new ButtonColumn(tableCriteria, insertPath, 3);
	}
	
	@SuppressWarnings({"serial", "unchecked", "rawtypes"})
	public  void resetTableModels() {
		tableVariable = new JTable();
		tableCriteria= new JTable();
		tableCriteria.setRowSelectionAllowed(false);
		tableVariable.setRowSelectionAllowed(false);
//		tableVariable
//		.setModel(new DefaultTableModel(
//			new Object[][]{},
//			new String[] {
//				"Name","Rule", "Minimum", "Maximum", "Forbidden"}
//		) {
//			Class[] columnTypes = new Class[] {
//				String.class,String.class, Integer.class, Integer.class, Integer.class
//			};
//			public Class getColumnClass(int columnIndex) {
//				return columnTypes[columnIndex];
//			}
//		});	 
		
		scrollPanelTableVariable.setViewportView(null);
		scrollPanelTableCriteria.setViewportView(null);
		tableCriteria.putClientProperty("terminateEditOnFocusLost", true);
		tableVariable.putClientProperty("terminateEditOnFocusLost", true);
		
		
	}
	private void chooseFileTable(int m){
		int result = fileChooserJar.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooserJar.getSelectedFile();
			if(selectedFile.exists()){
				modelTableButton.setValueAt(selectedFile.getAbsolutePath(), m, 2);
			}
		}
	}

	@SuppressWarnings({"serial", "unchecked", "rawtypes"})
	private void loadTableVariable(){
		DefaultTableModel modelVariable = null;
		if(comboBoxType.getSelectedItem().toString()=="Integer"){
			modelVariable = new DefaultTableModel(
				new Object[][]{},
				new String[] {
					"Name","Rule", "Minimum", "Maximum", "Forbidden"}
			) {
			
				Class[] columnTypes = new Class[] {
					String.class,String.class, Integer.class, Integer.class, Integer.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			};
		}
		if(comboBoxType.getSelectedItem().toString()=="Double"){
			 modelVariable = new DefaultTableModel(
					new Object[][]{},
					new String[] {
						"Name","Rule",  "Minimum", "Maximum", "Forbidden"}
				) {
				
					Class[] columnTypes = new Class[] {
						String.class,String.class, Double.class, Double.class, Double.class
					};
					public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}
				};
			}
		if(comboBoxType.getSelectedItem().toString()=="Boolean"){
			 modelVariable = new DefaultTableModel(
					new Object[][]{},
					new String[] {
						"Name","Rule",  "Forbidden"}
				) {
				
					Class[] columnTypes = new Class[] {
						String.class,String.class, Boolean.class
					};
					public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}
				};
			}
		
		int counter =  Integer.parseInt(txtNumberVariables.getText());
		for(int i=0; i<counter; i++){
			modelVariable.addRow(new Object[]{null, null});
		}
		scrollPanelTableVariable.setViewportView(tableVariable);
		tableVariable.setModel(modelVariable);
	}
	
	private void loadXmlProblem(String xmlFile){
		File file = new File(xmlFile);
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(LayoutProblem.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			LayoutProblem problem = (LayoutProblem) jaxbUnmarshaller.unmarshal(file);
			//System.out.println(problem);
			comboBoxType.setSelectedItem(problem.getTipo());
			txtEmail.setText(problem.getEmail());
			txtNumberVariables.setText(Integer.toString(problem.getNumberVariables()));
			txtNumberCriteria.setText(Integer.toString(problem.getNumberCriteria()));
			txtProblemDescription.setText(problem.getProblemDescription());
			txtProblemName.setText(problem.getProblemTitle());
			txtVariablesName.setText(problem.getVariablesName());
			txtMaximumTime.setText(Integer.toString(problem.getMaxWaitingTime()));
			loadTableVariable();
			loadTableCriteria();
			List <TableRowVariable> listVariable =problem.getListVariable();
			List <TableRowCriteria> listCriteria =problem.getListCriteria();
			TableModel modelVariable = tableVariable.getModel();
			TableModel modelCriteria = tableCriteria.getModel();
			if (comboBoxType.getSelectedItem()=="Double")
			for (int i=0;i<tableVariable.getRowCount();i++){
				modelVariable.setValueAt(listVariable.get(i).getName(), i, 0);
				modelVariable.setValueAt(listVariable.get(i).getRule(), i, 1);
				modelVariable.setValueAt(Double.parseDouble(listVariable.get(i).getMinimo()), i, 2);
				modelVariable.setValueAt(Double.parseDouble(listVariable.get(i).getMaximo()), i, 3);
				if(listVariable.get(i).getForbidden()!=null)
				modelVariable.setValueAt(Double.parseDouble(listVariable.get(i).getForbidden()), i, 4);
			}
			if (comboBoxType.getSelectedItem()=="Integer")
				for (int i=0;i<tableVariable.getRowCount();i++){
					modelVariable.setValueAt(listVariable.get(i).getName(), i, 0);
					modelVariable.setValueAt(listVariable.get(i).getRule(), i, 1);
					modelVariable.setValueAt(Integer.parseInt(listVariable.get(i).getMinimo()), i, 2);
					modelVariable.setValueAt(Integer.parseInt(listVariable.get(i).getMaximo()), i, 3);
					if(listVariable.get(i).getForbidden()!=null)
					modelVariable.setValueAt(Integer.parseInt(listVariable.get(i).getForbidden()), i, 4);
				}
			if (comboBoxType.getSelectedItem()=="Boolean")
				for (int i=0;i<tableVariable.getRowCount();i++){
					modelVariable.setValueAt(listVariable.get(i).getName(), i, 0);
					modelVariable.setValueAt(listVariable.get(i).getRule(), i, 1);
					if(listVariable.get(i).getForbidden()!=null)
					modelVariable.setValueAt(Boolean.parseBoolean(listVariable.get(i).getForbidden()), i, 4);
				}
			for (int i=0; i<tableCriteria.getRowCount();i++) {
				modelCriteria.setValueAt(listCriteria.get(i).getSolutionKnown(), i, 0);
				modelCriteria.setValueAt(listCriteria.get(i).getName(), i, 1);
				modelCriteria.setValueAt(listCriteria.get(i).getPath(), i, 2);

			}
			//TODO load combobox and table
		} catch (Exception  e) {
			// TODO clean tables
			System.out.println("ERRO AO LER FICHEIRO");
			clearProblem();
		}
	
	}
	
	private void createXml(){
		if(validateProblemFields()) {
		String tipo=comboBoxType.getSelectedItem().toString();
		  LayoutProblem problem = new LayoutProblem();
		  if(txtMaximumTime.getText().isEmpty()){
			  problem.setMaxWaitingTime(0);

		  }
		  else {
			  problem.setMaxWaitingTime(Integer.parseInt(txtMaximumTime.getText()));

		  }

		  problem.setVariablesName(txtVariablesName.getText());
		  problem.setNumberVariables(Integer.parseInt(txtNumberVariables.getText()));
		  problem.setNumberCriteria(Integer.parseInt(txtNumberCriteria.getText()));
		  problem.setProblemDescription(txtProblemDescription.getText());
		  problem.setProblemTitle(txtProblemName.getText());
		  problem.setEmail(txtEmail.getText());
		  problem.setTipo(comboBoxType.getSelectedItem().toString());
		  List <TableRowVariable> listVariable = new ArrayList<TableRowVariable>();
		  List <TableRowCriteria> listCriteria = new ArrayList<TableRowCriteria>();
		  for(int i=0; i<tableCriteria.getRowCount(); i++) {
			  TableRowCriteria m = new TableRowCriteria();
			  m.setSolutionKnown((Integer)tableCriteria.getValueAt(i,0));
			  m.setName(tableCriteria.getValueAt(i,1).toString());
			  System.out.println(tableCriteria.getValueAt(i,2).toString());
			  m.setPath(tableCriteria.getValueAt(i,2).toString());
			  listCriteria.add(m);
		  }
		  if(tipo=="Integer"){
			  for (int i=0;i<tableVariable.getRowCount();i++){
				  TableRowVariable m = new TableRowVariable();
				  m.setName((String) tableVariable.getValueAt(i, 0));
				  m.setRule((String) tableVariable.getValueAt(i, 1));
				  m.setMinimo(Integer.toString((int) tableVariable.getValueAt(i, 2)));
				  m.setMaximo(Integer.toString((int) tableVariable.getValueAt(i, 3)));
				  m.setForbidden(Integer.toString((int) tableVariable.getValueAt(i, 4)));
				  listVariable.add(m);
			  }
		  }
		  if(tipo=="Double"){
			  for (int i=0;i<tableVariable.getRowCount();i++){
				  TableRowVariable m = new TableRowVariable();
				  if( tableVariable.getValueAt(i, 0)!=null){
				  m.setName((String) tableVariable.getValueAt(i, 0));
				  }
				  else{
					  m.setName(null);
				  }
				  m.setRule((String) tableVariable.getValueAt(i, 1));
				  m.setMinimo(Double.toString((Double) tableVariable.getValueAt(i, 2)));
				  m.setMaximo( Double.toString((Double) tableVariable.getValueAt(i, 3)));
				  if( tableVariable.getValueAt(i, 4)!=null){
					  m.setForbidden(Double.toString((Double) tableVariable.getValueAt(i, 4)));
					  }
					  else{
						  m.setForbidden(null);
					  }
				  listVariable.add(m);
			  }
		  }
		  if(tipo=="Boolean"){
			  for (int i=0;i<tableVariable.getRowCount();i++){
				  TableRowVariable m = new TableRowVariable();
				  m.setName((String) tableVariable.getValueAt(i, 0));
				  m.setRule((String) tableVariable.getValueAt(i, 1));
				  m.setForbidden(Boolean.toString((Boolean) tableVariable.getValueAt(i, 2)));
				  listVariable.add(m);
			  }
		  }
		  problem.setListVariable(listVariable);
		  problem.setListCriteria(listCriteria);
		  //String[][] teste = new String[20][4];

		  try {
			  Calendar calobj = Calendar.getInstance();
			  DateFormat df = new SimpleDateFormat("dd-MM-yy HH-mm-ss");
			  String s = df.format(calobj.getTime());
			File file = new File("SavedProblems/" +txtProblemName.getText()+df.format(calobj.getTime()) + ".xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(LayoutProblem.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(problem, file);
			jaxbMarshaller.marshal(problem, System.out);

	      } catch (JAXBException e) {
	    	  e.printStackTrace();
	      }
		}

	}


	private void runDemo(){
		loadData();
//		OptimizationProcess op = new OptimizationProcess();
//		List<String> decisionVariables = new ArrayList<String>();
//		for(int i = 0; i < tableVariable.getModel().getRowCount(); i++){
//			decisionVariables.add((String) tableVariable.getModel().getValueAt(i, 1));
//		}
//		List<String> jarPaths = new ArrayList<String>();
//		jarPaths.add("testJars/FalseNegatives.jar");
//		jarPaths.add("testJars/FalsePositives.jar");
//		Thread t = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//				op.run(decisionVariables, jarPaths, (String)comboBoxType.getSelectedItem(), "NSGAII", txtProblemName.getText());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		t.start();
	}
	
	private void visualizeDemo(){
		List<String> algorithmsNames = new ArrayList<String>();
		algorithmsNames.add("NSGAII");
		
		List<String> filePaths = new ArrayList<String>();
		filePaths.add("experimentsBaseDirectory/" + txtProblemName.getText() + "/" + 
						(String)comboBoxType.getSelectedItem() + "Problem.rs");
		
		List<String> decisionVariables = new ArrayList<String>();
		for(int i = 0; i < tableVariable.getModel().getRowCount(); i++){
			decisionVariables.add((String) tableVariable.getModel().getValueAt(i, 1));
		}
	//	DataVisualization dv = new DataVisualization(algorithmsNames, filePaths, decisionVariables);
	
		JFrame frame = new JFrame("Graphical Visualization");
	//	frame.getContentPane().add(dv);
		WindowListener exitListener = new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		   // 	frame.remove(dv);
		    }
		};
		frame.addWindowListener(exitListener);
		frame.setSize(900, 600);
		//dv.run();
		frame.setVisible(true);
	}
}