package main.optimization.platform.jMetal.problems;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.problem.impl.AbstractIntegerPermutationProblem;
import org.uma.jmetal.solution.PermutationSolution;

public class IntegerPermutationProblem extends AbstractIntegerPermutationProblem {
	
	private static final long serialVersionUID = 1L;
	
	private int numberOfCities ;
	private List<String> decisionVariables;
	private List<ProblemHelper> problemHelpers;
	private int numberOfObjectives;

	
	public IntegerPermutationProblem(List<String> decisionVariables, List<String> jarPaths, String name) throws Exception {
		numberOfObjectives = jarPaths.size();
		this.decisionVariables = decisionVariables;
		numberOfCities=this.decisionVariables.size();
		setNumberOfVariables(numberOfCities);
	    setNumberOfObjectives(numberOfObjectives);
	    setName(name);
	    
	    
	    problemHelpers = new ArrayList<ProblemHelper>();
		for (int i = 0; i < numberOfObjectives; i++) {
			problemHelpers.add(new ProblemHelper(jarPaths.get(i), this.decisionVariables));
		}
	}

	@Override
	public int getPermutationLength() {
		return numberOfCities;
	}

	@Override
	public void evaluate(PermutationSolution<Integer> solution) {
			for (int i = 0; i < numberOfObjectives; i++) {
				problemHelpers.get(i).evaluate(solution);
			}
	}

}
