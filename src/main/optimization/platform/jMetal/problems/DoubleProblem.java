package main.optimization.platform.jMetal.problems;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

@SuppressWarnings("serial")
public class DoubleProblem extends AbstractDoubleProblem {
	private int numberOfObjectives;
	private double lowerBound;
	private double upperBound;
	private List<String> decisionVariables;
	private List<ProblemHelper> problemHelpers;

	/**
	 * Constructor instantiates a DoubleProblem.
	 * 
	 * @throws Exception
	 */
	public DoubleProblem(List<String> decisionVariables, List<String> jarPaths, String problemName, double lowerBound,
			double upperBound) throws Exception {
		this.decisionVariables = decisionVariables;
		numberOfObjectives = jarPaths.size();
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;

		problemHelpers = new ArrayList<ProblemHelper>();
		for (int i = 0; i < numberOfObjectives; i++) {
			problemHelpers.add(new ProblemHelper(jarPaths.get(i), this.decisionVariables));
		}
		setNumberOfVariables(this.decisionVariables.size());
		setNumberOfObjectives(numberOfObjectives);
		setName(problemName);

		// Adds the lower and upper bound to each decision variable
		// Assumed that the user restricts every decision variable with the same
		// interval
		List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables());
		List<Double> upperLimit = new ArrayList<>(getNumberOfVariables());
		for (int i = 0; i < getNumberOfVariables(); i++) {
			lowerLimit.add(this.lowerBound);
			upperLimit.add(this.upperBound);
		}
		setLowerLimit(lowerLimit);
		setUpperLimit(upperLimit);

	}

	/**
	 * Evaluates the fitness of a vector of weights.
	 */
	public void evaluate(DoubleSolution solution) {
		for (int i = 0; i < numberOfObjectives; i++) {
			problemHelpers.get(i).evaluate(solution);
		}
	}

}
