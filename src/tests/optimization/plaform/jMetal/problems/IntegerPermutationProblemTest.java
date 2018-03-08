package tests.optimization.plaform.jMetal.problems;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.uma.jmetal.problem.impl.AbstractIntegerPermutationProblem;
import main.optimization.plaform.jMetal.problems.IntegerPermutationProblem;

public class IntegerPermutationProblemTest {

	@Test
	public void constructorMustReturnAnObjectThatInheritsFromIntegerProblem() {
		List<String> decisionVariables = new ArrayList<String>();
		List<String> jarPaths = new ArrayList<String>();
		IntegerPermutationProblem instance = new IntegerPermutationProblem(decisionVariables, jarPaths);
		assertTrue("IntegerPermutationProblem intances should extend the AbstractIntegerPermutationProblem class.",
				instance.getClass().getSuperclass().equals(AbstractIntegerPermutationProblem.class));
	}
}