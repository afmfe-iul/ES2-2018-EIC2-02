package tests.optimization.platform.jMetal.problems;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;

import main.optimization.platform.gui.MainLayout;
import main.optimization.platform.jMetal.OptimizationProcess;
import main.optimization.platform.jMetal.problems.BinaryProblem;

public class BinaryProblemTest {

	@Test
	public void constructorMustReturnAnObjectThatInheritsFromIntegerProblem() {
		List<String> jarPaths = new ArrayList<String>();
		BinaryProblem instance;
		BinarySolution solution = null; 
		for(int i = 0; i<2;i++) {
			jarPaths.add("input/FalseNegatives.jar");
		}
		try {
			
			instance = new BinaryProblem(new OptimizationProcess(new MainLayout()), 2, jarPaths, null, 5);
			assertTrue("BinaryProblem intances should extend the AbstractBinaryProblem class.",
					instance.getClass().getSuperclass().equals(AbstractBinaryProblem.class));
			assertTrue("Number of bits returned by the getter shoud be equal to the variable passed in the "
					+ "constructor", instance.getNumberOfBits(0)==5);
			instance.evaluate(solution);
		} catch (Exception e) {}
		
	}
}