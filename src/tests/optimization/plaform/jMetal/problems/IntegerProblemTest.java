package tests.optimization.plaform.jMetal.problems;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.junit.Test;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;

public class IntegerProblemTest {

	@Test
	public void constructorMustReturnAnObjectThatInheritsFromIntegerProblem() {
		List<String> decisionVariables = new ArrayList<String>();
		List<String> jarPaths = new ArrayList<String>();
		IntegerProblem instance = new IntegerProblem(decisionVariables, jarPaths);
		assertTrue("IntegerProblem intances should extend the AbstractIntegerProblem class.",
				instance.getClass().getSuperclass().equals(AbstractIntegerProblem.class));
	}
}