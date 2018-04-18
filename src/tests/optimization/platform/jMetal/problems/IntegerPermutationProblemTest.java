package tests.optimization.platform.jMetal.problems;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.uma.jmetal.problem.impl.AbstractIntegerPermutationProblem;

import main.optimization.platform.jMetal.problems.IntegerPermutationProblem;

public class IntegerPermutationProblemTest {

	@Test
	public void constructorMustReturnAnObjectThatInheritsFromIntegerProblem() {
		List<String> decisionVariables = new ArrayList<String>();
		List<String> jarPaths = new ArrayList<String>();
		IntegerPermutationProblem instance;
		try {
			instance = new IntegerPermutationProblem(decisionVariables, jarPaths, null);
			assertTrue("IntegerPermutationProblem intances should extend the AbstractIntegerPermutationProblem class.",
					instance.getClass().getSuperclass().equals(AbstractIntegerPermutationProblem.class));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}