package main.optimization.platform.gui;

import java.awt.EventQueue;
import java.util.List;
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

public class MainLayout {
	public JFrame frame;
	public JTable tableManualConfig;
	public JTable tableControl;
	public JDialog progressDialog;
	public JScrollPane scrollPaneTabel1;
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
	private JTextField txtVariablesNumber;
	private JTextArea txtProblemDescription;
	private JComboBox<String> comboBoxType;
	private JScrollPane scrollPaneTabel2;
	JFileChooser fileChooserJar;
	private DefaultTableModel modelTableButton;
	private JTextField txtCriteria;

	
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
		FileNameExtensionFilter filterXml = new FileNameExtensionFilter("Xml files", "xml", "xml");
		frame = new JFrame("Optimizer");
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
		scrollPaneTabel1 = new JScrollPane();
		scrollPaneTabel2 = new JScrollPane();

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
		txtVariablesNumber = new JTextField();
		txtVariablesNumber.setColumns(10);
		JButton btnLoadTable = new JButton("Load Table");
		btnLoadTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadTable();
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
		
		txtCriteria = new JTextField();
		txtCriteria.setColumns(10);
		
		JButton btnCriteria = new JButton("Load Table");
		btnCriteria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadCriteriaTable();
			}

			
		});
		
		
		
		// TODO END of demo code
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(42)
											.addComponent(btnLoadTable))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(scrollPaneTabel1, GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
											.addPreferredGap(ComponentPlacement.RELATED)))
									.addGap(38)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(btnRunDemo)
										.addComponent(btnVisDemo)))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(comboBoxType, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(txtVariablesNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
										.addComponent(txtMaximumTime, 92, 92, 92))))
							.addGap(30)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(txtCriteria, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnCriteria))
								.addComponent(scrollPaneTabel2, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)))
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
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnOpenXmlProblem)
								.addComponent(btnSaveXmlProblemL))))
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
						.addComponent(txtVariablesNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtVariablesName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtMaximumTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtCriteria, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCriteria))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnLoadTable)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPaneTabel2, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(scrollPaneTabel1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
							.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
								.addComponent(btnRunDemo)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnVisDemo))))
					.addGap(18)
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
						.addComponent(btnOpenXmlProblem))
					.addGap(69))
		);

		resetTableModels();
		frame.getContentPane().setLayout(groupLayout);
	}
	
	public void promptUser(String message, boolean error){

		String title = error ?  "Error!" : "Warning!";
		int iconType = error ? JOptionPane.ERROR_MESSAGE : JOptionPane.WARNING_MESSAGE;
		JOptionPane.showMessageDialog(frame, message, title, iconType);
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
	private void loadCriteriaTable() {
		// TODO Auto-generated method stub

		tableControl.setModel( modelTableButton = new DefaultTableModel(
		
			new Object[][]{},
			new String[] {
			"Solution known","Nome","Path", "Add Path"}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class,String.class,String.class, ButtonColumn.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		int i = Integer.parseInt(txtCriteria.getText());
		for (;i>0;i--){
		modelTableButton.addRow(new Object[]{null,null, null,null});
		}
		tableManualConfig.getColumnModel().getColumn(0).setResizable(false);
		tableManualConfig.getColumnModel().getColumn(1).setResizable(false);
		scrollPaneTabel2.setViewportView(tableControl);
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
		ButtonColumn buttonColumn = new ButtonColumn(tableControl, insertPath, 3);
	}
	
	@SuppressWarnings({"serial", "unchecked", "rawtypes"})
	public  void resetTableModels() {
		tableManualConfig = new JTable();
		tableControl= new JTable();
		tableControl.setRowSelectionAllowed(false);
		tableManualConfig.setRowSelectionAllowed(false);
		tableManualConfig
		.setModel(new DefaultTableModel(
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
		});	 
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
	private void loadTable(){
		DefaultTableModel modelManual = null;
		if(comboBoxType.getSelectedItem().toString()=="Integer"){
			modelManual = new DefaultTableModel(
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
			 modelManual = new DefaultTableModel(
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
			 modelManual = new DefaultTableModel(
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
		
		int counter =  Integer.parseInt(txtVariablesNumber.getText());
		for(int i=0; i<counter; i++){
			modelManual.addRow(new Object[]{null, null});
		}
		scrollPaneTabel1.setViewportView(tableManualConfig);
		tableManualConfig.setModel(modelManual);
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
			txtVariablesNumber.setText(Integer.toString(problem.getVariablesNumber()));
			txtProblemDescription.setText(problem.getProblemDescription());
			txtProblemName.setText(problem.getProblemTitle());
			txtVariablesName.setText(problem.getVariablesName());
			txtMaximumTime.setText(Integer.toString(problem.getMaxWaitingTime()));
			loadTable();
			 List <TableRow> lista =problem.getList();
			 TableModel modelManual = tableManualConfig.getModel();
			 
			 if (comboBoxType.getSelectedItem()=="Double")
			 for (int i=0;i<tableManualConfig.getRowCount();i++){
				 modelManual.setValueAt(lista.get(i).getName(), i, 0);
				 modelManual.setValueAt(lista.get(i).getRule(), i, 1);
				 modelManual.setValueAt(Double.parseDouble(lista.get(i).getMinimo()), i, 2);
				 modelManual.setValueAt(Double.parseDouble(lista.get(i).getMaximo()), i, 3);
				 if(lista.get(i).getForbidden()!=null)
				 modelManual.setValueAt(Double.parseDouble(lista.get(i).getForbidden()), i, 4);
				 
			 }
			 if (comboBoxType.getSelectedItem()=="Integer")
				 for (int i=0;i<tableManualConfig.getRowCount();i++){
					 modelManual.setValueAt(lista.get(i).getName(), i, 0);
					 modelManual.setValueAt(lista.get(i).getRule(), i, 1);
					 modelManual.setValueAt(Integer.parseInt(lista.get(i).getMinimo()), i, 2);
					 modelManual.setValueAt(Integer.parseInt(lista.get(i).getMaximo()), i, 3);
					 if(lista.get(i).getForbidden()!=null)
					 modelManual.setValueAt(Integer.parseInt(lista.get(i).getForbidden()), i, 4);
					 
				 }
			 if (comboBoxType.getSelectedItem()=="Boolean")
				 for (int i=0;i<tableManualConfig.getRowCount();i++){
					 modelManual.setValueAt(lista.get(i).getName(), i, 0);
					 modelManual.setValueAt(lista.get(i).getRule(), i, 1);
					 if(lista.get(i).getForbidden()!=null)
					 modelManual.setValueAt(Boolean.parseBoolean(lista.get(i).getForbidden()), i, 4);
					 
				 }
			//TODO load combobox and table
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		  problem.setVariablesNumber(Integer.parseInt(txtVariablesNumber.getText()));
		  problem.setProblemDescription(txtProblemDescription.getText());
		  problem.setProblemTitle(txtProblemName.getText());
		  problem.setEmail(txtEmail.getText());
		  problem.setTipo(comboBoxType.getSelectedItem().toString());
		  List <TableRow> lista = new ArrayList<TableRow>();
		  
		  if(tipo=="Integer"){
			  for (int i=0;i<tableManualConfig.getRowCount();i++){
				  TableRow m = new TableRow();
				  m.setName((String) tableManualConfig.getValueAt(i, 0));
				  m.setRule((String) tableManualConfig.getValueAt(i, 1));
				  m.setMinimo(Integer.toString((int) tableManualConfig.getValueAt(i, 2)));
				  m.setMaximo(Integer.toString((int) tableManualConfig.getValueAt(i, 3)));
				  m.setForbidden(Integer.toString((int) tableManualConfig.getValueAt(i, 4)));
				  lista.add(m);
			  }
		  }
		  if(tipo=="Double"){
			  for (int i=0;i<tableManualConfig.getRowCount();i++){
				  TableRow m = new TableRow();
				  if( tableManualConfig.getValueAt(i, 0)!=null){
				  m.setName((String) tableManualConfig.getValueAt(i, 0));
				  }
				  else{
					  m.setName(null);
				  }
				  m.setRule((String) tableManualConfig.getValueAt(i, 1));
				  m.setMinimo(Double.toString((Double) tableManualConfig.getValueAt(i, 2)));
				  m.setMaximo( Double.toString((Double) tableManualConfig.getValueAt(i, 3)));
				  if( tableManualConfig.getValueAt(i, 4)!=null){
					  m.setForbidden(Double.toString((Double) tableManualConfig.getValueAt(i, 4)));
					  }
					  else{
						  m.setForbidden(null);
					  }
				  lista.add(m);
			  }
		  }
		  if(tipo=="Boolean"){
			  for (int i=0;i<tableManualConfig.getRowCount();i++){
				  TableRow m = new TableRow();
				  m.setName((String) tableManualConfig.getValueAt(i, 0));
				  m.setRule((String) tableManualConfig.getValueAt(i, 1));
				  m.setForbidden(Boolean.toString((Boolean) tableManualConfig.getValueAt(i, 2)));
				  lista.add(m);
			  }
		  }
		  problem.setList(lista);
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
		OptimizationProcess op = new OptimizationProcess();
		List<String> decisionVariables = new ArrayList<String>();
		for(int i = 0; i < tableManualConfig.getModel().getRowCount(); i++){
			decisionVariables.add((String) tableManualConfig.getModel().getValueAt(i, 1));
		}
		List<String> jarPaths = new ArrayList<String>();
		jarPaths.add("testJars/FalseNegatives.jar");
		jarPaths.add("testJars/FalsePositives.jar");
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
				op.run(decisionVariables, jarPaths, (String)comboBoxType.getSelectedItem(), "NSGAII", txtProblemName.getText());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
	}
	
	private void visualizeDemo(){
		List<String> algorithmsNames = new ArrayList<String>();
		algorithmsNames.add("NSGAII");
		
		List<String> filePaths = new ArrayList<String>();
		filePaths.add("experimentsBaseDirectory/" + txtProblemName.getText() + "/" + 
						(String)comboBoxType.getSelectedItem() + "Problem.rs");
		
		List<String> decisionVariables = new ArrayList<String>();
		for(int i = 0; i < tableManualConfig.getModel().getRowCount(); i++){
			decisionVariables.add((String) tableManualConfig.getModel().getValueAt(i, 1));
		}
		DataVisualization dv = new DataVisualization(algorithmsNames, filePaths, decisionVariables);
	
		JFrame frame = new JFrame("Graphical Visualization");
		frame.getContentPane().add(dv);
		WindowListener exitListener = new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	frame.remove(dv);
		    }
		};
		frame.addWindowListener(exitListener);
		frame.setSize(900, 600);
		dv.run();
		frame.setVisible(true);
	}
}