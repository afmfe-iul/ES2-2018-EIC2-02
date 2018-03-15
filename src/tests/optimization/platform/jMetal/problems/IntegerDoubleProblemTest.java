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
		IntegerDoubleProblem<DoubleSolution> instance = new IntegerDoubleProblem<DoubleSolution>(decisionVariables, jarPaths);
		assertTrue("IntegerDoubleProblem intances should extend the AbstractIntegerDoubleProblem class.",
				instance.getClass().getSuperclass().equals(AbstractIntegerDoubleProblem.class));
	}
}