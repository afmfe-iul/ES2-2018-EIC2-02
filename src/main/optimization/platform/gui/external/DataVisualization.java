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
import java.util.Arrays;
import java.util.HashMap;
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
	
	// TODO revise how the auxiliar files are being built and take into account the new results folder format
	public DataVisualization(List<String> algorithmsNames, List<String> rsFilePaths, 
			List<String> rfFilePaths, List<String> decisionVariables, Integer knownSolution) {
		
		if(algorithmsNames.size() != rsFilePaths.size() || algorithmsNames.size() != rfFilePaths.size() ||
				algorithmsNames.size() == 0 || rsFilePaths.size() == 0 || rfFilePaths.size() == 0){
			dataFileBuiltSuccessfuly = false;
		}else{
			VERSION++;
			String rsFirstLine = "variable";
			String[] rsLines = new String[decisionVariables.size()];
			
			String rfFirstLine = "User Solution";
			String rfSecondLine = knownSolution == null ? "-1" : String.valueOf(knownSolution);
			for(int i = 0; i < rsLines.length; i++){
				rsLines[i] = decisionVariables.get(i);
			}

			for(int i = 0; i < rsFilePaths.size(); i++){
		        Scanner rsScanner;
		        Scanner rfScanner;
				try {
					rsScanner = new Scanner(new File(rsFilePaths.get(i)));
					rfScanner = new Scanner(new File(rfFilePaths.get(i)));
					
					int differentRunsOfTheSameAlgorithm = 0;
			        while(rsScanner.hasNextLine()){
			        	// logic to read .rs file
			        	String thisRunName = algorithmsNames.get(i) + "(" + differentRunsOfTheSameAlgorithm + ")";
			        	rsFirstLine += "\t" + thisRunName;
			            String[] weights = rsScanner.nextLine().split(" ");
			            if(weights.length != decisionVariables.size()){
			            	dataFileBuiltSuccessfuly = false;
			            	rsScanner.close();
			            	return;
			            }
			            
			            for(int j = 0; j < weights.length; j++){
			            	rsLines[j] += "\t" + weights[j]; 
			            }
			            differentRunsOfTheSameAlgorithm++;
			        
			            // logic to read .rf file
			            rfFirstLine += "\t" + thisRunName;
			            rfSecondLine += ("\t" + calculateRfFileResult(rfScanner.nextLine().split(" ")));
			        }
			        rsScanner.close();
			        rfScanner.close();
				} catch (FileNotFoundException e) {
					dataFileBuiltSuccessfuly = false;
					e.printStackTrace();
					return;
				}
			}
			writeVisualizationFiles(rsFirstLine, rsLines, rfFirstLine, rfSecondLine);
		}
	}

	public boolean run() {
		if(dataFileBuiltSuccessfuly){
			String tempDir = CURR_DIR + "visualizations/temp" + VERSION;
			File f = new File(tempDir + "/graphics.html");
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
	
	private String calculateRfFileResult(String[] rfFileLine) {
		int result = 0;
		for(int i = 0; i < rfFileLine.length; i++) {
			result += Double.valueOf(rfFileLine[i]);
		}
		return String.valueOf(result);
	}
	
	private void writeVisualizationFiles(String rsFirstLine, String[] rsLines, String rfFirstLine, String rfSecondLine){
		try {
			deleteDirectory(new File(CURR_DIR + "visualizations"));
			String tempDir = CURR_DIR + "visualizations/temp" + VERSION;
			FileUtils.copyFile(new File(CURR_DIR + "visualizations/template.html"),
					new File(tempDir + "/graphics.html"));
			
			// Write the data file in tsv format (tab separated values)
			PrintWriter writer = new PrintWriter(tempDir + "/data.tsv");
			writer.println(rsFirstLine);
			for(int i = 0; i < rsLines.length; i++){
				writer.println(rsLines[i]);
			}
			writer.close();
			
			// Write an auxiliar file so that javascript knows the algorithm names
			// and use them to loop over map values.
			writer = new PrintWriter(tempDir + "/auxiliar.tsv");
			String header = new String();
			for(int i = 0; i < rsFirstLine.split("\t").length -1; i++){
				header += "\t" + "algorithm" + i;
			}
			writer.println(header.trim());
			writer.println(rsFirstLine.substring("variable".length()).trim());
			writer.close();
			
			// Write an auxiliar file to be used by D3.js, where the rf file results are stored
			writer = new PrintWriter(tempDir + "/auxRf.tsv");
			
			// orders the results before saving to a file
			String[] algoName = rfFirstLine.split("\t");
			int[] algoResults = Arrays.asList(rfSecondLine.split("\t")).stream().mapToInt(Integer::parseInt).toArray();
			HashMap<Integer, String> resultToAlgoMap = new HashMap<>();
			for(int i = 0; i < algoName.length; i++) {
				if(resultToAlgoMap.containsKey(algoResults[i])){
					resultToAlgoMap.put(algoResults[i], 
							resultToAlgoMap.get(algoResults[i]) + "\t" + algoName[i]);
				}else {
					resultToAlgoMap.put(algoResults[i], algoName[i]);
				}
			}
			
			Arrays.sort(algoResults);
			writer.println("Algorithm\tResult");
			for(int i = 0; i < algoResults.length; i++) {
				String[] s = resultToAlgoMap.get(algoResults[i]).split("\t");
				if(s.length > 1) {
					String[] s2 = resultToAlgoMap.get(algoResults[i]).split("\t", 2);
					resultToAlgoMap.put(algoResults[i], s2[1]);
				}
				writer.println((s[0] + "\t" + algoResults[i]).trim());
			}
			writer.close();
			dataFileBuiltSuccessfuly = true;
			Platform.setImplicitExit(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	
	/// XXX temporary for testing
	public static void main(String[] args) {
		List<String> algorithmsNames = new ArrayList<String>();
		algorithmsNames.add("NSGAII");
		
		List<String> rsFilePaths = new ArrayList<String>();
		rsFilePaths.add(CURR_DIR + "experimentsBaseDirectory/AntiSpamFilterProblem/DoubleProblem.NSGAII.rs");
		
		List<String> rfFilePaths = new ArrayList<String>();
		rfFilePaths.add(CURR_DIR + "experimentsBaseDirectory/AntiSpamFilterProblem/DoubleProblem.NSGAII.rf");
		
		
		List<String> decisionVariables = new ArrayList<String>();
		File file = new File(CURR_DIR + "experimentsBaseDirectory/AntiSpamFilterProblem/rules.cf");
		
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
		
		DataVisualization dv = new DataVisualization(algorithmsNames, rsFilePaths, rfFilePaths, decisionVariables, null);
		JFrame jFrame = new JFrame("Main");
		jFrame.setSize(1300, 700);
		jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		jFrame.add(dv);
		dv.run();
		jFrame.setVisible(true);
	}
}