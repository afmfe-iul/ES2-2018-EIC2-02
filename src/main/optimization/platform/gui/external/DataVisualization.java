package main.optimization.platform.gui.external;

import java.awt.Container;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.apache.commons.io.FileUtils;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import main.optimization.platform.utils.Builders;

@SuppressWarnings("restriction")
public class DataVisualization  extends JFXPanel{
	private static final long serialVersionUID = 1L;
	private boolean dataFileBuiltSuccessfuly = false;
	private String buildErrorMessage;
	private static int VERSION = 0;
	private Button btnBack;
	private Button btnReload;
	private WebEngine webEngine;
	
	public DataVisualization(List<String> algorithmsNames, List<String> rsFilePaths, 
			List<String> rfFilePaths, List<String> decisionVariables, Integer knownSolution) {
		Collections.sort(algorithmsNames);
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
			            	buildErrorMessage = "The Problem format seems to have an error.";
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
	            	buildErrorMessage = "The Problem results are not ready to display yet, or the foler where they were written to was deleted.";
					e.printStackTrace();
					return;
				}
			}
			writeVisualizationFiles(rsFirstLine, rsLines, rfFirstLine, rfSecondLine);
		}
	}

	public boolean run() {
		if(dataFileBuiltSuccessfuly){
			String tempDir = Builders.BASE_DIRECTORY + "visualizations/temp" + VERSION;
			File f = new File(tempDir + "/graphics.html");
			try {
				setName("Results Visualization");
			    BorderPane root = new BorderPane();
			    root.setTop(getButtonPanel());
				
				URL url = f.toURI().toURL();
				Platform.runLater(() -> {
					CookieHandler.setDefault(new CookieManager());
				    WebView webView = new WebView();
				    root.setCenter(webView);
				    setScene(new Scene(root));
				    webEngine = webView.getEngine();
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
			deleteDirectory(new File(Builders.BASE_DIRECTORY + "visualizations"));
			String tempDir = Builders.BASE_DIRECTORY + "visualizations/temp" + VERSION;
			FileUtils.copyFile(new File("resources/template.html"),
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

	public void addBackButtonActionListener(JFrame frame, Container mainPanel, DataVisualization dv) {
		btnBack.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				frame.remove(dv);
				frame.setContentPane(mainPanel);
				frame.revalidate();
				frame.repaint();
				frame.setBounds(320, 30, 1000, 667);
			}
		});
	}
	
	public String getBuildErrorMessage() {
		return buildErrorMessage;
	}
	
	private HBox getButtonPanel() {
	    HBox hbox = new HBox();
	    hbox.setPadding(new Insets(5, 0, 5, 0));
	    hbox.setSpacing(100);
	    hbox.setStyle("-fx-background-color: #336699;");
	    hbox.setAlignment(Pos.CENTER);

	    btnBack = new Button("Go Back");
	    btnBack.setStyle("-fx-font: 22 arial; -fx-font-weight: bold;");
	    btnBack.setPrefSize(200, 30);
	    btnReload = new Button("Reload Frame");
	    btnReload.setStyle("-fx-font: 22 arial; -fx-font-weight: bold;");
	    btnReload.setPrefSize(200, 30);
		btnReload.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				webEngine.reload();
			}
		});
	    
	    hbox.getChildren().addAll(btnBack, btnReload);
	    return hbox;
	}
}