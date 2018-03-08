package tests.optimization.plaform.jMetal.problems;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;

import main.optimization.plaform.jMetal.problems.DoubleProblem;

public class DoubleProblemTest {

	@Test
	public void constructorMustReturnAnObjectThatInheritsFromIntegerProblem() {
		List<String> decisionVariables = new ArrayList<String>();
		List<String> jarPaths = new ArrayList<String>();
		// The DoubleProblem assumes the input files (ham.log and spam.log) paths
		// are hardcoded in the admin xml file
		DoubleProblem instance = new DoubleProblem(decisionVariables, jarPaths);
		assertTrue("DoubleProblem intances should extend the AbstractDoubleProblem class.",
				instance.getClass().getSuperclass().equals(AbstractDoubleProblem.class));
	}
}