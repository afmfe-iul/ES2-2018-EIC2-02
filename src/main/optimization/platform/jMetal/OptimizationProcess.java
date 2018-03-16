package main.optimization.platform.jMetal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.experiment.Experiment;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.component.ComputeQualityIndicators;
import org.uma.jmetal.util.experiment.component.ExecuteAlgorithms;
import org.uma.jmetal.util.experiment.component.GenerateBoxplotsWithR;
import org.uma.jmetal.util.experiment.component.GenerateLatexTablesWithStatistics;
import org.uma.jmetal.util.experiment.component.GenerateReferenceParetoSetAndFrontFromDoubleSolutions;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

import main.optimization.platform.jMetal.problems.DoubleProblem;

public class OptimizationProcess {

	public static final int INDEPENDENT_RUNS = 5;
	public static final String EXPERIMENT_BASE_DIRECTORY = "experimentsBaseDirectory";

	// TODO Do you need to find the Data types dynamicaly or hardcoded?
	public Set<String> getPossibleDataTypes() {
		Set<String> result = new HashSet<String>();
		result.add("Integer");
		result.add("Binary");
		result.add("Double");
		result.add("Integer/Double");

		return result;
	}

	// TODO Find all algorithms based on Datatype
	public List<String> getAlgorithmsFor(String dataType) {
		List<String> algoritms = new ArrayList<>();
		if (dataType.equals("Double")) {
			algoritms.add("NSGAII");
		}
		return algoritms;
	}

	// TODO do the UPPER and LOWER Bounds on constructor of the problem?
	// TODO name of problem from GUI input??
	public boolean run(List<String> decisionVariables, List<String> jarPaths, String dataType, String algorithm, String problemName) throws Exception {
		if(decisionVariables == null || jarPaths == null || decisionVariables.size() == 0 || jarPaths.size() == 0){
			return false;
		}
		
		if (dataType.equals("Double")) {
			List<ExperimentProblem<DoubleSolution>> problemList = new ArrayList<>();
			DoubleProblem problem = new DoubleProblem(decisionVariables, jarPaths, "DoubleProblem", -5, 5);
			problemList.add(new ExperimentProblem<>(problem));

			List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithmList = configureAlgorithmList(
					problemList, algorithm);

			Experiment<DoubleSolution, List<DoubleSolution>> experiment = new ExperimentBuilder<DoubleSolution, List<DoubleSolution>>(
					problemName).setAlgorithmList(algorithmList).setProblemList(problemList)
							.setExperimentBaseDirectory(EXPERIMENT_BASE_DIRECTORY).setOutputParetoFrontFileName("FUN")
							.setOutputParetoSetFileName("VAR")
							.setReferenceFrontDirectory(EXPERIMENT_BASE_DIRECTORY + "/" + problemName)
							.setIndicatorList(Arrays.asList(new PISAHypervolume<DoubleSolution>()))
							.setIndependentRuns(INDEPENDENT_RUNS).setNumberOfCores(8).build();

			new ExecuteAlgorithms<>(experiment).run();
			try {
				new GenerateReferenceParetoSetAndFrontFromDoubleSolutions(experiment).run();
				new ComputeQualityIndicators<>(experiment).run();
				new GenerateLatexTablesWithStatistics(experiment).run();
				new GenerateBoxplotsWithR<>(experiment).setRows(1).setColumns(1).run();
			} catch (IOException e) {
				return false;
			}

		}
		return true;
	}

	private List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> configureAlgorithmList(
			List<ExperimentProblem<DoubleSolution>> problemList, String algorithmSelected) {

		List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithms = new ArrayList<>();

		// if we do this if we can use the getAlgorithmsFor() for only UI proposes
		if (algorithmSelected.equals("NSGAII"))
			;
		for (int i = 0; i < problemList.size(); i++) {
			Algorithm<List<DoubleSolution>> algorithm = new NSGAIIBuilder<>(problemList.get(i).getProblem(),
					new SBXCrossover(1.0, 5),
					new PolynomialMutation(1.0 / problemList.get(i).getProblem().getNumberOfVariables(), 10.0))
							.setMaxEvaluations(2500).setPopulationSize(100).build();
			algorithms.add(new ExperimentAlgorithm<>(algorithm, problemList.get(i).getTag()));
		}
		return algorithms;
	}
	
	// TODO Main method for testing purposes
	// TODO remove this after the UI is done
	public static void main(String[] args) {
		OptimizationProcess op = new OptimizationProcess();
		List<String> decisionVariables = new ArrayList<String>();
		List<String> jarPaths = new ArrayList<String>();
		jarPaths.add("testJars/FalseNegatives.jar");
		jarPaths.add("testJars/FalsePositives.jar");
		
		File file = new File("experimentsBaseDirectory/testResults/rules.cf");
		
		try {
	        Scanner sc = new Scanner(file);
	        while(sc.hasNextLine()){   
	            decisionVariables.add(sc.nextLine());
	        } 
	        sc.close();
	    }catch (FileNotFoundException e) {
	    	e.printStackTrace();
	    }
		
		try {
			op.run(decisionVariables, jarPaths, "Double", "NSGAII", "AntiSpamFilter");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
