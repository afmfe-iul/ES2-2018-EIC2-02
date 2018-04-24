package tests.optimization.platform.jMetal.problems;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.uma.jmetal.problem.impl.AbstractIntegerDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import main.optimization.platform.jMetal.problems.IntegerDoubleProblem;

public class IntegerDoubleProblemTest {

	@Test
	public void constructorMustReturnAnObjectThatInheritsFromIntegerProblem() {
		List<String> decisionVariables = new ArrayList<String>();
		List<String> jarPaths = new ArrayList<String>();
		List<Number> lowerBound = new ArrayList<Number>();
		List<Number> upperBound = new ArrayList<Number>();
		DoubleSolution solution = null;

		for (int i = 0; i < 2; i++) {
			decisionVariables.add("" + i);
			jarPaths.add("testJars/FalseNegatives.jar");
		}

		IntegerDoubleProblem<DoubleSolution> instance;
		try {
			instance = new IntegerDoubleProblem<DoubleSolution>(decisionVariables, lowerBound, upperBound, jarPaths,
					null);

			assertTrue("IntegerDoubleProblem intances should extend the AbstractIntegerDoubleProblem class.",
					instance.getClass().getSuperclass().equals(AbstractIntegerDoubleProblem.class));

			assertTrue("Number of variables to optimize are correct",
					instance.getNumberOfObjectives() == jarPaths.size());

			instance.evaluate(solution);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}