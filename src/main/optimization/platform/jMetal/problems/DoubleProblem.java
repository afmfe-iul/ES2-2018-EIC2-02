package main.optimization.platform.jMetal.problems;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import main.optimization.platform.jMetal.OptimizationProcess;
/**
 * 
 * @author Tiago Feliciano
 *
 */
 public class DoubleProblem extends AbstractDoubleProblem {
	private static final long serialVersionUID = 1L;
	private final OptimizationProcess optimizationProcess;
	private int numberOfObjectives;
	private List<ProblemHelper> problemHelpers;

	/**
	 * Constructor that instantiates a Double Problem
	 * @param optimizationProcess 
	 * @param numberOfVariables Int that represents the number of variables for the problem instantiated
	 * @param lowerBounds List of Doubles that represent the minimum value for each problem variable
	 * @param upperBounds List of Doubles that represent the maximum value for each problem variable
	 * @param jarPaths List with paths for each decision variable jar with evaluate method
	 * @param problemName String Problem name
	 * @throws Exception
	 */
	// TODO change the doc, new parameter
	public DoubleProblem(OptimizationProcess optimizationProcess, int numberOfVariables, List<Double> lowerBounds , List<Double> upperBounds, List<String> jarPaths, String problemName) throws Exception {
		this.optimizationProcess = optimizationProcess;
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
		optimizationProcess.callBack();
		for (int i = 0; i < numberOfObjectives; i++) {
			problemHelpers.get(i).evaluate(solution);
		}
	}

}
