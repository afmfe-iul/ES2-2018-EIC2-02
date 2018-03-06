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
				instance.getClass().getSuperclass().equals(JComponent.class));
	}
}
