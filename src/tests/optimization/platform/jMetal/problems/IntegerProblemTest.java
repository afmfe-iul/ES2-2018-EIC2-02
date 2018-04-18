package tests.optimization.platform.jMetal.problems;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;

import main.optimization.platform.jMetal.problems.IntegerProblem;

public class IntegerProblemTest {

	@Test
	public void constructorMustReturnAnObjectThatInheritsFromIntegerProblem() {
		List<String> decisionVariables = new ArrayList<String>();
		List<String> jarPaths = new ArrayList<String>();
		IntegerProblem instance;
		try {
			instance = new IntegerProblem(decisionVariables, null, null, null, jarPaths);
			assertTrue("IntegerProblem intances should extend the AbstractIntegerProblem class.",
					instance.getClass().getSuperclass().equals(AbstractIntegerProblem.class));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}