package main.optimization.plaform.jMetal.problems;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

public class DoubleProblem extends AbstractDoubleProblem {
	private static final long serialVersionUID = 1L;
	private static int NUMBER_OF_OBJECTIVES;
	private static double LOWER_BOUND;
	private static double UPPER_BOUND;
	private static List<String> DECISION_VARIABLES;
	private static List<String> JAR_PATHS;

	/**
	 * Constructor instantiates a DoubleProblem.
	 */
	public DoubleProblem(List<String> decisionVariables, List<String> jarPaths, String problemName, double lowerBound,
			double upperBound) {
		DECISION_VARIABLES = decisionVariables;
		JAR_PATHS = jarPaths;
		NUMBER_OF_OBJECTIVES = decisionVariables.size();
		LOWER_BOUND = lowerBound;
		UPPER_BOUND = upperBound;

		setNumberOfVariables(DECISION_VARIABLES.size());
		setNumberOfObjectives(NUMBER_OF_OBJECTIVES);
		setName(problemName);

		// Adds the lower and upper bound to each decision variable
		// Assumed that the user restricts every decision variable with the same
		// interval
		List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables());
		List<Double> upperLimit = new ArrayList<>(getNumberOfVariables());
		for (int i = 0; i < getNumberOfVariables(); i++) {
			lowerLimit.add(LOWER_BOUND);
			upperLimit.add(UPPER_BOUND);
		}
		setLowerLimit(lowerLimit);
		setUpperLimit(upperLimit);

	}

	/**
	 * Evaluates the fitness of a vector of weights.
	 */
	public void evaluate(DoubleSolution solution) {
		for (int i = 0; i < NUMBER_OF_OBJECTIVES; i++) {
			ProblemUtils.evaluate(JAR_PATHS.get(i), solution, i);
		}
	}

}
