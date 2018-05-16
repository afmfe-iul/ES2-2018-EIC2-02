package main.optimization.platform.jMetal.problems;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;

public class IntegerProblem extends AbstractIntegerProblem {
	private static final long serialVersionUID = 1L;
	private List<String> decisionVariables;
	private List<ProblemHelper> problemHelpers;
	private int numberOfObjectives;

	/**
	 * Constructor instantiates a IntegerProblem.
	 * 
	 * @throws Exception
	 */
	public IntegerProblem(List<String> decisionVariables, List<Integer> lowerBounds , List<Integer> upperBound,String problemname , List<String> jarPaths) throws Exception {
		this.decisionVariables = decisionVariables;
		numberOfObjectives = jarPaths.size();
		
		setNumberOfVariables(decisionVariables.size());
	    setNumberOfObjectives(jarPaths.size());
	    
	    setLowerLimit(lowerBounds);
	    setUpperLimit(upperBound);
	    
//	    for (int i = 0; i < lowerBounds.size(); i++) {
//			System.out.println(getLowerBound(i)+ " minimo");
//			System.out.println(getUpperBound(i) + " maximo");
//		}
	    
	    
		setName(problemname);
		
		problemHelpers = new ArrayList<ProblemHelper>();
		for (int i = 0; i < jarPaths.size(); i++) {
			problemHelpers.add(new ProblemHelper(jarPaths.get(i), this.decisionVariables));
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



 


