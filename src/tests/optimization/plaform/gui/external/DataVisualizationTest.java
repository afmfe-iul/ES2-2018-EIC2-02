package tests.optimization.plaform.gui.external;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.junit.Test;
import main.optimization.plaform.gui.external.DataVisualization;

public class DataVisualizationTest {

	@Test
	public void constructorMustReturnAnObjectThatInheritsFromJComponent(){
		List<String> algorithmsNames = new ArrayList<String>();
		List<String> filePaths = new ArrayList<String>();
		List<String> decisionVariables = new ArrayList<String>();
		DataVisualization instance = new DataVisualization(algorithmsNames, filePaths, decisionVariables);
		assertTrue("DataVisualization intances should extend the JComponent class.",
				JComponent.class.isAssignableFrom(instance.getClass()));
	}
	
	@Test
	public void runShouldReturnFalseIfTheFilePathsAreInvalid(){
		List<String> algorithmsNames = new ArrayList<String>();
		List<String> filePaths = new ArrayList<String>();
		List<String> decisionVariables = new ArrayList<String>();
		DataVisualization instance = new DataVisualization(algorithmsNames, filePaths, decisionVariables);
		assertFalse("DataVisualization run method must return false if there is a problem with the file paths.",
				instance.run());
	}
}
