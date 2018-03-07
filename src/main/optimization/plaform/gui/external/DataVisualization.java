package main.optimization.plaform.gui.external;

import java.util.List;
import javafx.embed.swing.JFXPanel;

public class DataVisualization  extends JFXPanel{
	private static final long serialVersionUID = 1L;

	public DataVisualization(List<String> filePaths) {
		// TODO generate data.tsv files based on the .rs an .rf files in the filePaths
		// the .tsv formats should be: Decision Variable Name (column 1), Algorithm 1 (column 2), Algorithm 2, etc..
		// and Optimiation Criterium number (column 1), Algorithm 1 (column 2), Algorithm 2, etc..
	}

	public boolean run() {
		// TODO If there was a problem generating the .tsv files return false, otherwise
		// start a new webView, set it as this component scene, load a html file to the webView and return true
		return false;
	}
}