package main.optimization.platform.gui;

import java.awt.EventQueue;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
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
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;



public class MainLayout {
	public JFrame frame;
	public JTextField txtRules;
	public JTable tableManualConfig;
	public JDialog progressDialog;
	public JScrollPane scrollPaneTabel1;
	public HashMap<String, Integer> rulesMap = new HashMap<String, Integer>();
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
	private JComboBox comboBoxType;
	
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
		frame = new JFrame("AntiSpamConfigurationForLeisureMailbox");
		frame.setResizable(false);
		frame.setBounds(100, 100, 700, 667);

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

		JLabel lblRules = new JLabel("Jar");
		txtRules = new JTextField();
		txtRules.setEditable(false);
		txtRules.setColumns(10);

		JButton btnRules = new JButton("");
		//btnSpam.setIcon(new ImageIcon(MainLayout.class.getResource("/imageWindowBuilder/foldericon.png")));
		JFileChooser fileChooser = new JFileChooser();
		JFileChooser fileChooserXml= new JFileChooser();
		 File k= new File("SavedProblems");
		 fileChooserXml.setCurrentDirectory(k);
		//JFileChooser fileChooserOther = new JFileChooser();
		FileNameExtensionFilter filterRules = new FileNameExtensionFilter("CF Files", "cf", "cf");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("LOG Files", "log", "log");
		fileChooser.setFileFilter(filterRules);
		
		btnRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = fileChooser.showOpenDialog(btnRules);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					if(selectedFile.exists()){
					txtRules.setText(selectedFile.getAbsolutePath());
					}
				}
			}
		});

		scrollPaneTabel1 = new JScrollPane();
		
		JButton btnJarPath = new JButton("Load Jar");
		btnJarPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO send jar
			}
		});
		
		comboBoxType = new JComboBox();
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
		
		lblNameVariables = new JLabel("Variables name");
		
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
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(scrollPaneTabel1, GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
								.addComponent(lblDescrio)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblNameProblem)
										.addComponent(lblEmail))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(txtEmail, GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
										.addComponent(txtProblemName, GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)))
								.addComponent(txtProblemDescription, GroupLayout.PREFERRED_SIZE, 467, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnOpenXmlProblem)
								.addComponent(btnSaveXmlProblemL)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
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
											.addComponent(lblNameVariables))))
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblRules)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txtRules, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnRules)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblMaximumtime, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtMaximumTime, 92, 92, 92))))
					.addGap(102))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(131)
					.addComponent(btnJarPath, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(644))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(57)
					.addComponent(btnLoadTable)
					.addContainerGap(706, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(9)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblRules)
								.addComponent(txtRules, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(4)
							.addComponent(btnJarPath)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblVariablesNumber)
								.addComponent(lblType)
								.addComponent(lblNameVariables)
								.addComponent(lblMaximumtime, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addComponent(btnRules, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBoxType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtVariablesNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtVariablesName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtMaximumTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnLoadTable)
							.addGap(2)
							.addComponent(scrollPaneTabel1, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
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
						.addComponent(txtProblemDescription, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
						.addComponent(btnOpenXmlProblem))
					.addGap(26))
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
		tableManualConfig = new JTable();
		tableManualConfig.setRowSelectionAllowed(false);
		tableManualConfig
		.setModel(new DefaultTableModel(
			new Object[][]{},
			new String[] {
				"Name", "Rule","Minimum", "Maximum", "Forbidden"}
		) {
			Class[] columnTypes = new Class[] {
				String.class,String.class, Integer.class, Integer.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tableManualConfig.getColumnModel().getColumn(0).setResizable(false);
		tableManualConfig.getColumnModel().getColumn(1).setResizable(false);
		scrollPaneTabel1.setViewportView(tableManualConfig);
	}
	
	private void loadTable(){
		DefaultTableModel modelManual = null;
		if(comboBoxType.getSelectedItem().toString()=="Integer"){
			modelManual = new DefaultTableModel(
				new Object[][]{},
				new String[] {
					"Name","Rule",  "Minimum", "Maximum", "Forbidden"}
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
			
			txtVariablesNumber.setText(Integer.toString(problem.getVariablesNumber()));
			txtProblemDescription.setText(problem.getProblemDescription());
			txtProblemName.setText(problem.getProblemTitle());
			txtVariablesName.setText(problem.getVariablesName());
			txtMaximumTime.setText(Integer.toString(problem.getMaxWaitingTime()));
			//TODO load combobox and table
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	private void createXml(){
		String tipo=comboBoxType.getSelectedItem().toString();
		  LayoutProblem problem = new LayoutProblem();
		  problem.setMaxWaitingTime(Integer.parseInt(txtMaximumTime.getText()));
		  problem.setVariablesName(txtVariablesName.getText());
		  problem.setVariablesNumber(Integer.parseInt(txtVariablesNumber.getText()));
		  problem.setProblemDescription(txtProblemDescription.getText());
		  problem.setProblemTitle(txtProblemName.getText());
		  problem.setTipo(comboBoxType.getSelectedItem().toString());
		  List <tableRow> lista = new ArrayList<tableRow>(2);
		  
		  if(tipo=="Integer"){
			  for (int i=0;i<tableManualConfig.getRowCount();i++){
				  tableRow m = new tableRow();
			 m.setName((String) tableManualConfig.getValueAt(i, 0));
			 m.setRule((String) tableManualConfig.getValueAt(i, 1));
			  m.setMaximo( Integer.toString((int) tableManualConfig.getValueAt(i, 2)));
			  m.setMinimo(Integer.toString((int) tableManualConfig.getValueAt(i, 3)));
			  m.setForbidden(Integer.toString((int) tableManualConfig.getValueAt(i, 4)));
			  lista.add(m);
			  }
		  }
		  if(tipo=="Double"){
			  for (int i=0;i<tableManualConfig.getRowCount();i++){
				  tableRow m = new tableRow();
			 m.setName((String) tableManualConfig.getValueAt(i, 0));
			 m.setRule((String) tableManualConfig.getValueAt(i, 1));
			  m.setMaximo( Double.toString((Double) tableManualConfig.getValueAt(i, 2)));
			  m.setMinimo(Double.toString((Double) tableManualConfig.getValueAt(i, 3)));
			  m.setForbidden(Double.toString((Double) tableManualConfig.getValueAt(i, 4)));
			  lista.add(m);
			  }
		  }
		  if(tipo=="Boolean"){
			  for (int i=0;i<tableManualConfig.getRowCount();i++){
				  tableRow m = new tableRow();
			 m.setName((String) tableManualConfig.getValueAt(i, 0));
			 m.setRule((String) tableManualConfig.getValueAt(i, 1));
			  m.setForbidden(Boolean.toString((Boolean) tableManualConfig.getValueAt(i, 2)));
			  lista.add(m);
			  }
		  }
		  problem.setList(lista);
		  //String[][] teste = new String[20][4];

		  try {

			File file = new File("SavedProblems/" +txtProblemName.getText() + ".xml");
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
	
	private void loadJar(){
		//TODO load jar function
	}
}
