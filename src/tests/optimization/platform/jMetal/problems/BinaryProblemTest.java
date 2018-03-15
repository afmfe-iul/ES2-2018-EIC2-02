package tests.optimization.platform.jMetal.problems;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.uma.jmetal.problem.impl.AbstractBinaryProblem;

import main.optimization.platform.jMetal.problems.BinaryProblem;

public class BinaryProblemTest {

	@Test
	public void constructorMustReturnAnObjectThatInheritsFromIntegerProblem() {
		List<String> decisionVariables = new ArrayList<String>();
		List<String> jarPaths = new ArrayList<String>();
		BinaryProblem instance = new BinaryProblem(decisionVariables, jarPaths);
		assertTrue("BinaryProblem intances should extend the AbstractBinaryProblem class.",
				instance.getClass().getSuperclass().equals(AbstractBinaryProblem.class));
	}
}