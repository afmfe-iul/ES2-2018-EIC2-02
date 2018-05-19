package main.optimization.platform.jMetal.problems;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
/**
 * 
 * @author Tiago Feliciano
 *
 */
@SuppressWarnings("serial")
public class DoubleProblem extends AbstractDoubleProblem {
	private int numberOfObjectives;
	private List<ProblemHelper> problemHelpers;

	/**
	 * Constructor that instantiates a Double Problem
	 * @param numberOfVariables Int that represents the number of variables for the problem instantiated
	 * @param lowerBounds List of Doubles that represent the minimum value for each problem variable
	 * @param upperBounds List of Doubles that represent the maximum value for each problem variable
	 * @param jarPaths List with paths for each decision variable jar with evaluate method
	 * @param problemName String Problem name
	 * @throws Exception
	 */
	public DoubleProblem(int numberOfVariables, List<Double> lowerBounds , List<Double> upperBounds, List<String> jarPaths, String problemName) throws Exception {
		numberOfObjectives = jarPaths.size();
		
		problemHelpers = new ArrayList<ProblemHelper>();
		for (int i = 0; i < numberOfObjectives; i++) {
			problemHelpers.add(new ProblemHelper(jarPaths.get(i)));
		}
		setNumberOfVariables(numberOfVariables);
		setNumberOfObjectives(numberOfObjectives);
		setName(problemName);

		// Adds the lower and upper bound to each decision variable
		setLowerLimit(lowerBounds);
		setUpperLimit(upperBounds);

	}

	/**
	 * Evaluates the fitness of a vector of weights.
	 * Calls the evaluate inside the specific jar for each decision variable thru the ProblemHelper class 
	 * @param DoubleSolution
	 */
	public void evaluate(DoubleSolution solution) {
		for (int i = 0; i < numberOfObjectives; i++) {
			problemHelpers.get(i).evaluate(solution);
		}
	}

}
