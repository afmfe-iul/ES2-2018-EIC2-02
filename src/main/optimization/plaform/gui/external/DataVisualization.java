package main.optimization.plaform.gui.external;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class DataVisualization  extends JFXPanel{
	private static final long serialVersionUID = 1L;
	private static final String CURR_DIR = System.getProperty("curr.dir") == null ? "" : System.getProperty("curr.dir") + "/";
	private boolean dataFileBuiltSuccessfuly = false; // TODO must check this
	
	// TODO atm this constructor only allows to build from .rs files (decision variables weights)
	// it must be changed in the future to allow to build .rf files (optimization variables, ex: FP/FN)
	public DataVisualization(List<String> algorithmsNames, List<String> filePaths, List<String> decisionVariables) {
		// TODO generate data.tsv files based on the .rs an .rf files in the filePaths
		// the .tsv formats should be: Decision Variable Name (column 1), Algorithm 1 (column 2), Algorithm 2, etc..
		// and Optimiation Criterium number (column 1), Algorithm 1 (column 2), Algorithm 2, etc..
		
		writeTemporaryTSVFile();
	}

	public boolean run() {
		// TODO If there was a problem generating the .tsv files return false, otherwise
		// start a new webView, set it as this component scene, load a html file to the webView and return true
		return dataFileBuiltSuccessfuly;
	}
	
	private void writeTemporaryTSVFile(){
		try {
			PrintWriter writer = new PrintWriter(CURR_DIR + "visualizations/data.tsv");
			writer.println("test");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		List<String> algorithmsNames = new ArrayList<String>();
		algorithmsNames.add("NSGAII");
		
		List<String> filePaths = new ArrayList<String>();
		filePaths.add( CURR_DIR+ "experimentsBaseDirectory/testResults/AntiSpamFilterProblem.NSGAII.rf");
		
		List<String> decisionVariables = new ArrayList<String>();
		File file = new File(CURR_DIR + "experimentsBaseDirectory/testResults/rules.cf");
		
		try {
	        Scanner sc = new Scanner(file);
	        while(sc.hasNextLine()){   
	        	String variableName = sc.nextLine();
	            decisionVariables.add(variableName);
	        } 
	        sc.close();
	    }
	    catch (FileNotFoundException e) {
	    	e.printStackTrace();
	    }
		
		DataVisualization dv = new DataVisualization(algorithmsNames, filePaths, decisionVariables);
		// TODO add dv to a jframe and run it properly
		JFrame jFrame = new JFrame("Main");
		jFrame.setSize(800, 600);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.add(dv);

		Platform.runLater(() -> {
		    WebView webView = new WebView();
		    dv.setScene(new Scene(webView));
		    WebEngine webEngine = webView.getEngine();
		    webEngine.loadContent("Test");
		});
		
		jFrame.setVisible(true);
	}
}