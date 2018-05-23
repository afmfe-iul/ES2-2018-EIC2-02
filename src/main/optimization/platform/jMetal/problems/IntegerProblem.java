package main.optimization.platform.jMetal.problems;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;

import main.optimization.platform.jMetal.OptimizationProcess;
/**
 * 
 * @author Tiago Feliciano
 *
 */
public class IntegerProblem extends AbstractIntegerProblem {
	private static final long serialVersionUID = 1L;
	private final OptimizationProcess optimizationProcess;
	private List<ProblemHelper> problemHelpers;
	private int numberOfObjectives;

	/**
	 * Constructor that instantiates a Integer Problem
	 * @param optimizationProcess 
	 * @param numberOfVariables Int that represents the number of variables for the problem instantiated
	 * @param lowerBounds List of Integers that represent the minimum value for each problem variable
	 * @param upperBound List of Integers that represent the maximum value for each problem variable
	 * @param problemname String Problem name
	 * @param jarPaths List with paths for each decision variable jar with evaluate method
	 * @throws Exception
	 */
	// TODO change the doc, new parameter
	public IntegerProblem(OptimizationProcess optimizationProcess, int numberOfVariables, List<Integer> lowerBounds , List<Integer> upperBound,String problemname , List<String> jarPaths) throws Exception {
		this.optimizationProcess = optimizationProcess;
		numberOfObjectives = jarPaths.size();
		
		setNumberOfVariables(numberOfVariables);
	    setNumberOfObjectives(jarPaths.size());
	    
	    setLowerLimit(lowerBounds);
	    setUpperLimit(upperBound);
		setName(problemname);
		
		problemHelpers = new ArrayList<ProblemHelper>();
		for (int i = 0; i < jarPaths.size(); i++) {
			problemHelpers.add(new ProblemHelper(jarPaths.get(i)));
		}
	}
	
	/**
	 * Evaluates the fitness of a vector of weights.
	 * Calls the evaluate inside the specific jar for each decision variable thru the ProblemHelper class 
	 * @param DoubleSolution
	 */
	@Override
	public void evaluate(IntegerSolution solution) {
		optimizationProcess.callBack();
		for (int i = 0; i < numberOfObjectives; i++) {
			problemHelpers.get(i).evaluate(solution);
		}
	}

}



 


