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
		List<String> filePaths = new ArrayList<String>();
		DataVisualization instance = new DataVisualization(filePaths);
		assertTrue("DataVisualization intances should extend the JComponent class.",
				JComponent.class.isAssignableFrom(instance.getClass()));
	}
	
	@Test
	public void runShouldReturnFalseIfTheFilePathsAreInvalid(){
		List<String> filePaths = new ArrayList<String>();
		DataVisualization instance = new DataVisualization(filePaths);
		assertFalse("DataVisualization run method must return false if there is a problem with the file paths.",
				instance.run());
	}
}
