package main.optimization.platform.jMetal.problems;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;

import main.optimization.platform.jMetal.OptimizationProcess;
/**
 * 
 * @author Tiago Feliciano
 *
 */
public class BinaryProblem extends AbstractBinaryProblem {
	private static final long serialVersionUID = 1L;
	private final OptimizationProcess optimizationProcess;
	private int numberOfObjectives;
	private List<ProblemHelper> problemHelpers;
	private int bitsPerVariable;

	/**
	 * Constructor that instantiates a Binary Problem
	 * @param optimizationProcess 
	 * @param numberOfVariables Int that represents the  number of variables for the problem instantiated
	 * @param jarPaths List with paths for each decision variable jar with evaluate method
	 * @param name String Problem name
	 * @param bitsPerVariable Int bitsPerVariable
	 * @throws Exception
	 */
	// TODO change the doc, new parameter
	public BinaryProblem(OptimizationProcess optimizationProcess, int numberOfVariables, List<String> jarPaths, String name, int bitsPerVariable) throws Exception {
		this.optimizationProcess = optimizationProcess;
		numberOfObjectives = jarPaths.size();
		this.bitsPerVariable = bitsPerVariable;
		problemHelpers = new ArrayList<ProblemHelper>();
		for (int i = 0; i < numberOfObjectives; i++) {
			problemHelpers.add(new ProblemHelper(jarPaths.get(i)));
		}
		
		setNumberOfObjectives(numberOfObjectives);
		setNumberOfVariables(numberOfVariables);
		setName(name);
	}

	/**
	 * Evaluates the fitness of a vector of weights.
	 * Calls the evaluate inside the specific jar for each decision variable thru the ProblemHelper class 
	 * @param BinarySolution
	 */
	@Override
	public void evaluate(BinarySolution solution) {
		optimizationProcess.callBack();
		for (int i = 0; i < numberOfObjectives; i++) {
			problemHelpers.get(i).evaluate(solution);
		}		
	}

	/** Gets BitsPervariable from the problem inputed by the user.
	 * @return Int BitsPervariable from the problem inputed by the user.
	*/
	@Override
	protected int getBitsPerVariable(int index) {
		return bitsPerVariable;
	}

}
