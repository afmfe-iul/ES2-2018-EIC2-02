package tests.optimization.platform.jMetal.problems;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;

import main.optimization.platform.jMetal.problems.DoubleProblem;

public class DoubleProblemTest {

	@Test
	public void constructorMustReturnAnObjectThatInheritsFromIntegerProblem() {
		List<String> decisionVariables = new ArrayList<String>();
		List<String> jarPaths = new ArrayList<String>();
		List<Double> upperbounds = new ArrayList<Double>();
		List<Double> lowerbounds = new ArrayList<Double>();

		// The DoubleProblem assumes the input files (ham.log and spam.log)
		// paths
		// are hardcoded in the admin xml file
		DoubleProblem instance;
		try {
			instance = new DoubleProblem(decisionVariables, upperbounds, lowerbounds, jarPaths, null);
			assertTrue("DoubleProblem intances should extend the AbstractDoubleProblem class.",
			instance.getClass().getSuperclass().equals(AbstractDoubleProblem.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}