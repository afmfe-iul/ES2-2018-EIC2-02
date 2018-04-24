package tests.optimization.platform.jMetal.problems;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.uma.jmetal.problem.impl.AbstractIntegerPermutationProblem;
import org.uma.jmetal.solution.PermutationSolution;

import main.optimization.platform.jMetal.problems.IntegerPermutationProblem;

public class IntegerPermutationProblemTest {

	@Test
	public void constructorMustReturnAnObjectThatInheritsFromIntegerProblem() {
		List<String> decisionVariables = new ArrayList<String>();
		List<String> jarPaths = new ArrayList<String>();
		PermutationSolution<Integer> solution=null;
		
		for(int i = 0; i<2;i++) {
			decisionVariables.add(""+i);
			jarPaths.add("testJars/FalseNegatives.jar");
		}
		IntegerPermutationProblem instance;
		try {
			instance = new IntegerPermutationProblem(decisionVariables, jarPaths, null);

			assertTrue("Number of variables to optimize are correct", 
					instance.getNumberOfObjectives()==jarPaths.size());
			
			assertTrue("IntegerPermutationProblem intances should extend the AbstractIntegerPermutationProblem class.",
					instance.getClass().getSuperclass().equals(AbstractIntegerPermutationProblem.class));
			
			assertTrue("Variable numberOfCities is properly initialzed", 
					decisionVariables.size()==instance.getPermutationLength());
			
			instance.evaluate(solution);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}