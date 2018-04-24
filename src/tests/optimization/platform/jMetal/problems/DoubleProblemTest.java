package tests.optimization.platform.jMetal.problems;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import main.optimization.platform.jMetal.problems.DoubleProblem;

public class DoubleProblemTest {

	@Test
	public void constructorMustReturnAnObjectThatInheritsFromIntegerProblem() {
		List<String> decisionVariables = new ArrayList<String>();
		List<String> jarPaths = new ArrayList<String>();
		List<Double> upperbounds = new ArrayList<Double>();
		List<Double> lowerbounds = new ArrayList<Double>();
		for(int i = 0; i<2;i++) {
			decisionVariables.add(""+i);
			jarPaths.add("testJars/FalseNegatives.jar");
		}
		DoubleProblem instance;
		DoubleSolution solution =null;
		try {
			instance = new DoubleProblem(decisionVariables, upperbounds, lowerbounds, jarPaths, null);
			assertTrue("DoubleProblem intances should extend the AbstractDoubleProblem class.",
			instance.getClass().getSuperclass().equals(AbstractDoubleProblem.class));
			
			assertTrue("Number of variables to optimize are correct", 
			instance.getNumberOfObjectives()==jarPaths.size());
			
			instance.evaluate(solution);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}