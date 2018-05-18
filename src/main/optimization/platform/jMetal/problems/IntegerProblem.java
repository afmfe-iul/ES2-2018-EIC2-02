package main.optimization.platform.jMetal.problems;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;

public class IntegerProblem extends AbstractIntegerProblem {
	private static final long serialVersionUID = 1L;
	private List<ProblemHelper> problemHelpers;
	private int numberOfObjectives;

	/**
	 * Constructor instantiates a IntegerProblem.
	 * 
	 * @throws Exception
	 */
	public IntegerProblem(int numberOfVariables, List<Integer> lowerBounds , List<Integer> upperBound,String problemname , List<String> jarPaths) throws Exception {
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
	 */
	@Override
	public void evaluate(IntegerSolution solution) {
		for (int i = 0; i < numberOfObjectives; i++) {
			problemHelpers.get(i).evaluate(solution);
		}
	}

}



 


