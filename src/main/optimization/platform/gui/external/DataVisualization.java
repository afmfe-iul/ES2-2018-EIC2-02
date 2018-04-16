package main.optimization.platform.gui.external;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import org.apache.commons.io.FileUtils;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

@SuppressWarnings("restriction")
public class DataVisualization  extends JFXPanel{
	private static final long serialVersionUID = 1L;
	private static final String CURR_DIR = System.getProperty("curr.dir") == null ? "" : System.getProperty("curr.dir") + "/";
	private boolean dataFileBuiltSuccessfuly = false;
	private static int VERSION = 0;
	
	// TODO atm this constructor only allows to build from .rs files (decision variables weights)
	// it must be changed in the future to allow to build .rf files (optimization variables, ex: FP/FN)
	public DataVisualization(List<String> algorithmsNames, List<String> filePaths, List<String> decisionVariables) {
		if(algorithmsNames.size() != filePaths.size() || algorithmsNames.size() == 0 || filePaths.size() == 0){
			dataFileBuiltSuccessfuly = false;
		}else{
			VERSION++;
			String firstLine = "variable";
			String[] lines = new String[decisionVariables.size()];
			for(int i = 0; i < lines.length; i++){
				lines[i] = decisionVariables.get(i);
			}
			
			for(int i = 0; i < filePaths.size(); i++){
		        Scanner sc;
				try {
					sc = new Scanner(new File(filePaths.get(i)));
					int differentRunsOfTheSameAlgorithm = 0;
			        while(sc.hasNextLine()){
			        	firstLine += "\t" + algorithmsNames.get(i) + "(" + differentRunsOfTheSameAlgorithm + ")";
			            String[] weights = sc.nextLine().split(" ");
			            if(weights.length != decisionVariables.size()){
			            	dataFileBuiltSuccessfuly = false;
			            	sc.close();
			            	return;
			            }
			            for(int j = 0; j < weights.length; j++){
			            	lines[j] += "\t" + weights[j]; 
			            }
			            differentRunsOfTheSameAlgorithm++;
			        } 
			        sc.close();
				} catch (FileNotFoundException e) {
					dataFileBuiltSuccessfuly = false;
					e.printStackTrace();
					return;
				}
			}
			writeTemporaryDataFile(firstLine, lines);
		}
	}

	public boolean run() {
		if(dataFileBuiltSuccessfuly){
			File f = new File("visualizations/temp" + VERSION + "/graphics.html");
			try {
				URL url = f.toURI().toURL();
				Platform.runLater(() -> {
					CookieHandler.setDefault(new CookieManager());
				    WebView webView = new WebView();
				    setScene(new Scene(webView));
				    WebEngine webEngine = webView.getEngine();
				    webEngine.load(url.toString());
				});
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return dataFileBuiltSuccessfuly;
	}
	
	private void writeTemporaryDataFile(String firstLine, String[] lines){
		try {
			deleteDirectory(new File(CURR_DIR + "visualizations"));
			String tempDir = CURR_DIR + "visualizations/temp" + VERSION;
			FileUtils.copyFile(new File(CURR_DIR + "visualizations/template.html"),
					new File(tempDir + "/graphics.html"));
			
			// Write the data file in tsv format (tab separated values)
			PrintWriter writer = new PrintWriter(tempDir + "/data.tsv");
			writer.println(firstLine);
			for(int i = 0; i < lines.length; i++){
				writer.println(lines[i]);
			}
			writer.close();
			
			// Write an auxiliar file so that javascript knows the algorithm names
			// and use them to loop over map values.
			writer = new PrintWriter(tempDir + "/auxiliar.tsv");
			String header = new String();
			for(int i = 0; i < firstLine.split("\t").length -1; i++){
				header += "\t" + "algorithm" + i;
			}
			writer.println(header.trim());
			writer.println(firstLine.substring("variable".length()).trim());
			writer.close();
			dataFileBuiltSuccessfuly = true;
			Platform.setImplicitExit(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/// XXX temporary for testing
	public static void main(String[] args) {
		List<String> algorithmsNames = new ArrayList<String>();
		algorithmsNames.add("NSGAII");
		
		List<String> filePaths = new ArrayList<String>();
		filePaths.add(CURR_DIR + "experimentsBaseDirectory/testResults/AntiSpamFilterProblem.NSGAII.rs");
		
		List<String> decisionVariables = new ArrayList<String>();
		File file = new File(CURR_DIR + "experimentsBaseDirectory/testResults/rules.cf");
		
		try {
	        Scanner sc = new Scanner(file);
	        while(sc.hasNextLine()){   
	        	String variableName = sc.nextLine();
	            decisionVariables.add(variableName);
	        } 
	        sc.close();
	    }catch (FileNotFoundException e) {
	    	e.printStackTrace();
	    }
		
		DataVisualization dv = new DataVisualization(algorithmsNames, filePaths, decisionVariables);
		JFrame jFrame = new JFrame("Main");
		jFrame.setSize(1300, 700);
		jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		jFrame.add(dv);
		dv.run();
		jFrame.setVisible(true);
	}
	
	private boolean deleteDirectory(File dir) {
	    File[] files = dir.listFiles();
	    if (files != null) {
	        for (int i = 0; i <files.length; i++) {
	            deleteDirectory(files[i]);
	        }
	    }
	    if(dir.getName().equals("template.html")){
	    	return true;
	    }else{
	    	return dir.delete();
	    }
	}
}