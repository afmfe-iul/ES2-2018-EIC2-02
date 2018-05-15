package main.optimization.platform.jMetal.problems;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;

public class BinaryProblem extends AbstractBinaryProblem {
	private static final long serialVersionUID = 1L;
	private int numberOfObjectives;
	private List<String> decisionVariables;
	private List<ProblemHelper> problemHelpers;
	private int bitsPerVariable;

	public BinaryProblem(List<String> decisionVariables, List<String> jarPaths, String name, int bitsPerVariable) throws Exception {
		numberOfObjectives = jarPaths.size();
		this.decisionVariables = decisionVariables;
		this.bitsPerVariable = bitsPerVariable;
		problemHelpers = new ArrayList<ProblemHelper>();
		for (int i = 0; i < numberOfObjectives; i++) {
			problemHelpers.add(new ProblemHelper(jarPaths.get(i), this.decisionVariables));
		}
		
		setNumberOfObjectives(numberOfObjectives);
		setNumberOfVariables(decisionVariables.size());
		setName(name);
	}

	@Override
	public void evaluate(BinarySolution solution) {
		for (int i = 0; i < numberOfObjectives; i++) {
			problemHelpers.get(i).evaluate(solution);
		}		
	}

	@Override
	//TODO check later
	protected int getBitsPerVariable(int index) {
		return bitsPerVariable;
	}

}
