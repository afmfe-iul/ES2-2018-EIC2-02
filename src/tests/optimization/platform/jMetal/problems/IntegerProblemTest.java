package tests.optimization.platform.jMetal.problems;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;

import main.optimization.platform.gui.MainLayout;
import main.optimization.platform.jMetal.OptimizationProcess;
import main.optimization.platform.jMetal.problems.IntegerProblem;

public class IntegerProblemTest {

	@Test
	public void constructorMustReturnAnObjectThatInheritsFromIntegerProblem() {
		List<String> jarPaths = new ArrayList<String>();
		List<Integer> upperbounds = new ArrayList<Integer>();
		List<Integer> lowerbounds = new ArrayList<Integer>();
		IntegerSolution solution =null;
		
		for (int i = 0; i < 2; i++) {
			jarPaths.add("input/FalseNegatives.jar");
		}

		IntegerProblem instance;
		try {
			instance = new IntegerProblem(new OptimizationProcess(new MainLayout()), 2, lowerbounds, upperbounds, null, jarPaths);
			
			assertTrue("IntegerProblem intances should extend the AbstractIntegerProblem class.",
					instance.getClass().getSuperclass().equals(AbstractIntegerProblem.class));
			
			assertTrue("Number of variables to optimize are correct", 
					instance.getNumberOfObjectives()==jarPaths.size());
			
			instance.evaluate(solution);
		} catch (Exception e) {}

	}
}