package main.optimization.platform.jMetal.problems;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.problem.impl.AbstractIntegerDoubleProblem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.impl.DefaultIntegerDoubleSolution;

public class IntegerDoubleProblem<S> extends AbstractIntegerDoubleProblem<S> {
	private static final long serialVersionUID = 1L;
	private int numberOfObjectives;
	private List<String> decisionVariables;
	private List<ProblemHelper> problemHelpers;

	public IntegerDoubleProblem(List<String> decisionVariables,List<Number>lowerBounds, List<Number>upperBounds  ,List<String> jarPaths,String problemName) throws Exception {
		setLowerLimit(lowerBounds);
		setUpperLimit(upperBounds);
		this.decisionVariables = decisionVariables;
		numberOfObjectives=jarPaths.size();
		setName(problemName);
		setNumberOfVariables(this.decisionVariables.size());
		setNumberOfObjectives(numberOfObjectives);
		problemHelpers = new ArrayList<ProblemHelper>();
		for (int i = 0; i < numberOfObjectives; i++) {
			problemHelpers.add(new ProblemHelper(jarPaths.get(i), this.decisionVariables));
		}
		
	}

	@Override
	public void evaluate(S solution) {
		for (int i = 0; i < numberOfObjectives; i++) {
			problemHelpers.get(i).evaluate((Solution<?>) solution);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public S createSolution() {
		return (S) new DefaultIntegerDoubleSolution(this) ;
	}

}
