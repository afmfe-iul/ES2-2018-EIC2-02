package main.optimization.platform.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.abyss.ABYSSBuilder;
import org.uma.jmetal.algorithm.multiobjective.gde3.GDE3Builder;
import org.uma.jmetal.algorithm.multiobjective.ibea.IBEABuilder;
import org.uma.jmetal.algorithm.multiobjective.mocell.MOCellBuilder;
import org.uma.jmetal.algorithm.multiobjective.mochc.MOCHCBuilder;
import org.uma.jmetal.algorithm.multiobjective.moead.AbstractMOEAD;
import org.uma.jmetal.algorithm.multiobjective.moead.MOEADBuilder;
import org.uma.jmetal.algorithm.multiobjective.moead.MOEADBuilder.Variant;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.omopso.OMOPSOBuilder;
import org.uma.jmetal.algorithm.multiobjective.paes.PAESBuilder;
import org.uma.jmetal.algorithm.multiobjective.randomsearch.RandomSearchBuilder;
import org.uma.jmetal.algorithm.multiobjective.smsemoa.SMSEMOABuilder;
import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2Builder;
import org.uma.jmetal.algorithm.multiobjective.wasfga.WASFGA;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.HUXCrossover;
import org.uma.jmetal.operator.impl.crossover.IntegerSBXCrossover;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.operator.impl.mutation.NonUniformMutation;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.mutation.UniformMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.operator.impl.selection.RandomSelection;
import org.uma.jmetal.operator.impl.selection.RankingAndCrowdingSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.archive.impl.CrowdingDistanceArchive;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.util.experiment.Experiment;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.component.ComputeQualityIndicators;
import org.uma.jmetal.util.experiment.component.ExecuteAlgorithms;
import org.uma.jmetal.util.experiment.component.GenerateBoxplotsWithR;
import org.uma.jmetal.util.experiment.component.GenerateLatexTablesWithStatistics;
import org.uma.jmetal.util.experiment.component.GenerateReferenceParetoFront;
import org.uma.jmetal.util.experiment.component.GenerateReferenceParetoSetAndFrontFromDoubleSolutions;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;
import main.optimization.platform.gui.MainLayout;
import main.optimization.platform.jMetal.OptimizationProcess;
import main.optimization.platform.jMetal.problems.BinaryProblem;
import main.optimization.platform.jMetal.problems.DoubleProblem;
import main.optimization.platform.jMetal.problems.IntegerProblem;

/**
 * Class with all the builders for all three types of problems
 * 
 * @author Tiago Feliciano, Hugo Alexandre, Andr� Freire
 *
 */
public class Builders {
	public static final int DEFAULT_ITERATIONS = 2500;
	private static final int INDEPENDENT_RUNS = 5;
	public static final String BASE_DIRECTORY = (MainLayout.PATH_OUTPUT.endsWith(File.separator) ? MainLayout.PATH_OUTPUT : 
		MainLayout.PATH_OUTPUT + File.separator) + "experimentsBaseDirectory" + File.separator;

	/**
	 * Method that instantiates and runs every chosen algorithm from GUI for Double
	 * Problems
	 * @param optimizationProcess 
	 *			  the parent Optimization Process reference that will be passed to the problem 	
	 * @param numberOfVariables
	 *            Int that represents the number of variables for the problem
	 *            instantiated
	 * @param problemName
	 *            String Problem name
	 * @param algorithmsSelected
	 *            List of algorithms select by the user
	 * @param lowerBounds
	 *            List of Doubles that represent the minimum value for each problem
	 *            variable
	 * @param upperBounds
	 *            List of Doubles that represent the maximum value for each problem
	 *            variable
	 * @param jarPaths
	 *            List with paths for each decision variable jar with evaluate
	 *            method
	 * @param maxEvaluations
	 * @return
	 */
	public static boolean DoubleBuilder(OptimizationProcess optimizationProcess, int numberOfVariables, String problemName, List<String> algorithmsSelected,
			List<Double> lowerBounds, List<Double> upperBounds, List<String> jarPaths, int maxEvaluations) {

		List<ExperimentProblem<DoubleSolution>> problemList = new ArrayList<>();
		DoubleProblem problem;
		maxEvaluations = maxEvaluations / INDEPENDENT_RUNS;
		try {
			problem = new DoubleProblem(optimizationProcess, numberOfVariables, lowerBounds, upperBounds, jarPaths, "DoubleProblem");
			problemList.add(new ExperimentProblem<>(problem));
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithmList = configureDoubleAlgorithmList(
				problemList, algorithmsSelected, maxEvaluations);

		removeDirectoriesRecursively(new File(BASE_DIRECTORY + problemName));
		Experiment<DoubleSolution, List<DoubleSolution>> experiment = new ExperimentBuilder<DoubleSolution, List<DoubleSolution>>(
				"ExperimentsDouble").setAlgorithmList(algorithmList).setProblemList(problemList)
						.setExperimentBaseDirectory(BASE_DIRECTORY + problemName).setOutputParetoFrontFileName("FUN")
						.setOutputParetoSetFileName("VAR")
						.setReferenceFrontDirectory(BASE_DIRECTORY + problemName + "/referenceFronts")
						.setIndicatorList(Arrays.asList(new PISAHypervolume<DoubleSolution>()))
						.setIndependentRuns(INDEPENDENT_RUNS).setNumberOfCores(8).build();

		new ExecuteAlgorithms<>(experiment).run();
		try {
			new GenerateReferenceParetoSetAndFrontFromDoubleSolutions(experiment).run();
			new ComputeQualityIndicators<>(experiment).run();
			new GenerateLatexTablesWithStatistics(experiment).run();
			new GenerateBoxplotsWithR<>(experiment).setRows(1).setColumns(1).run();
			return true;
		} catch (JMetalException e) {
			throw new JMetalException(problemName);
		}catch (IOException e) {
			return false;
		}
	}

	/**
	 * Builds algorithms for the type Double
	 * 
	 * @param problemList
	 * @param algorithmsSelected
	 * @param maxEvaluations
	 * @return
	 */
	private static List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> configureDoubleAlgorithmList(
			List<ExperimentProblem<DoubleSolution>> problemList, List<String> algorithmsSelected, int maxEvaluations) {
		List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithms = new ArrayList<>();

		if (algorithmsSelected.contains("MOEAD")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<DoubleSolution>> algorithm = new MOEADBuilder(problemList.get(i).getProblem(),
						Variant.MOEAD).setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm, "MOEAD", problemList.get(i).getTag()));
			}
		}

		if(algorithmsSelected.contains("MOEADDRA")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<DoubleSolution>> algorithm = new MOEADBuilder(problemList.get(i).getProblem(),
						Variant.MOEADDRA).setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm, "MOEADDRA", problemList.get(i).getTag()));
			}
		}

		if(algorithmsSelected.contains("MOEADSTM")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<DoubleSolution>> algorithm = new MOEADBuilder(problemList.get(i).getProblem(),
						Variant.MOEADSTM).setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm, "MOEADSTM", problemList.get(i).getTag()));
			}
		}

		if(algorithmsSelected.contains("MOEADD")) {
			for (int i = 0; i < problemList.size(); i++) {
				Problem<DoubleSolution> problem = problemList.get(i).getProblem();
			    MutationOperator<DoubleSolution> mutation = new PolynomialMutation(1.0 / problem.getNumberOfVariables(), 20.0);
			    MOEADBuilder builder =  new MOEADBuilder(problem, MOEADBuilder.Variant.MOEADD);
			    builder.setCrossover(new SBXCrossover(1.0, 30.0))
			            .setMutation(mutation)
			            .setMaxEvaluations(maxEvaluations)
			            .setPopulationSize(100)
			            .setResultPopulationSize(300)
			            .setNeighborhoodSelectionProbability(0.9)
			            .setMaximumNumberOfReplacedSolutions(1)
			            .setNeighborSize(20)
			            .setFunctionType(AbstractMOEAD.FunctionType.PBI)
			            .setDataDirectory("MOEAD_Weights");
			    Algorithm<List<DoubleSolution>> algorithm = builder.build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm, "MOEADD", problemList.get(i).getTag()));
			}
		}

		if (algorithmsSelected.contains("GDE3")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<DoubleSolution>> algorithm3 = new GDE3Builder(
						(DoubleProblem) problemList.get(i).getProblem()).setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm3, "GDE3", problemList.get(i).getTag()));
			}
		}

		if (algorithmsSelected.contains("ABYSS")) {
			CrowdingDistanceArchive<DoubleSolution> archive = new CrowdingDistanceArchive<>(100);
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<DoubleSolution>> algorithm1 = new ABYSSBuilder(
						(DoubleProblem) problemList.get(i).getProblem(), archive).setMaxEvaluations(maxEvaluations)
								.build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm1, problemList.get(i).getTag()));
			}

		}

		if (algorithmsSelected.contains("OMOPSO")) {
			SequentialSolutionListEvaluator<DoubleSolution> evaluator = new SequentialSolutionListEvaluator<>();
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<DoubleSolution>> algorithm1 = new OMOPSOBuilder(
						(DoubleProblem) problemList.get(i).getProblem(), evaluator).setMaxIterations(maxEvaluations)
								.setSwarmSize(1)
								.setUniformMutation(new UniformMutation(
										1.0 / problemList.get(i).getProblem().getNumberOfVariables(), 0.5))
								.setNonUniformMutation(new NonUniformMutation(
										1.0 / problemList.get(i).getProblem().getNumberOfVariables(), 0.5, 250))
								.build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm1, "OMOPSO", problemList.get(i).getTag()));
			}
		}

		if (algorithmsSelected.contains("MOCell")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<DoubleSolution>> algorithm5 = new MOCellBuilder<>(problemList.get(i).getProblem(),
						new SBXCrossover(1.0, 5),
						new PolynomialMutation(1.0 / problemList.get(i).getProblem().getNumberOfVariables(), 10.0))
								.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm5, "MOCell", problemList.get(i).getTag()));
			}
		}

		if (algorithmsSelected.contains("IBEA")) {
			for (int i = 0; i < problemList.size(); i++) {
				if(maxEvaluations<505) {
					maxEvaluations=505;
				}
				Algorithm<List<DoubleSolution>> algorithm4 = new IBEABuilder(problemList.get(i).getProblem())
						.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm4, "IBEA", problemList.get(i).getTag()));
			}
		}

		if (algorithmsSelected.contains("SMSEMOA")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<DoubleSolution>> algorithm2 = new SMSEMOABuilder<>(problemList.get(i).getProblem(),
						new SBXCrossover(1.0, 5),
						new PolynomialMutation(1.0 / problemList.get(i).getProblem().getNumberOfVariables(), 10.0))
								.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm2, "SMSEMOA", problemList.get(i).getTag()));
			}
		}

		if (algorithmsSelected.contains("SPEA2")) {
			for (int i = 0; i < problemList.size(); i++) {
				SelectionOperator<List<DoubleSolution>, DoubleSolution> selection = new BinaryTournamentSelection<DoubleSolution>(
						new RankingAndCrowdingDistanceComparator<DoubleSolution>());
				Algorithm<List<DoubleSolution>> algorithm2 = new SPEA2Builder<>(problemList.get(i).getProblem(),
						new SBXCrossover(1.0, 5),
						new PolynomialMutation(1.0 / problemList.get(i).getProblem().getNumberOfVariables(), 10.0))
								.setSelectionOperator(selection).setMaxIterations(maxEvaluations/2 + 1).setPopulationSize(2)
								.build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm2, "SPEA2", problemList.get(i).getTag()));
			}

		}

		if (algorithmsSelected.contains("NSGAII")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<DoubleSolution>> algorithm = new NSGAIIBuilder<>(problemList.get(i).getProblem(),
						new SBXCrossover(1.0, 5),
						new PolynomialMutation(1.0 / problemList.get(i).getProblem().getNumberOfVariables(), 10.0))
								.setMaxEvaluations(maxEvaluations).setPopulationSize(100).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm, "NSGAII", problemList.get(i).getTag()));
			}
		}

		if (algorithmsSelected.contains("PAES")) {
			for (int i = 0; i < problemList.size(); i++) {
				MutationOperator<DoubleSolution> mutation = new PolynomialMutation(
						1.0 / problemList.get(i).getProblem().getNumberOfVariables(), 20.0);
				Algorithm<List<DoubleSolution>> algorithm = new PAESBuilder<DoubleSolution>(
						problemList.get(i).getProblem()).setMutationOperator(mutation).setMaxEvaluations(maxEvaluations)
								.setArchiveSize(100).setBiSections(5).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm, "PAES", problemList.get(i).getTag()));
			}
		}


		if (algorithmsSelected.contains("RandomSearch")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<DoubleSolution>> algorithm1 = new RandomSearchBuilder<>(problemList.get(i).getProblem())
						.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm1, "RandomSearch", problemList.get(i).getTag()));
			}
		}

		if (algorithmsSelected.contains("NSGAIII")) {
			SelectionOperator<List<DoubleSolution>, DoubleSolution> selection = new BinaryTournamentSelection<DoubleSolution>();
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<DoubleSolution>> algorithm1 = new NSGAIIIBuilder<>(problemList.get(i).getProblem())
						.setCrossoverOperator(new SBXCrossover(1.0, 5.0))
						.setMutationOperator(new PolynomialMutation(
								1.0 / problemList.get(i).getProblem().getNumberOfVariables(), 10.0))
						// it seems this algorithm is running 16x more times than we ask
						.setSelectionOperator(selection).setPopulationSize(1).setMaxIterations(maxEvaluations/16 + maxEvaluations % 16).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm1, "NSGAIII", problemList.get(i).getTag()));
			}

		}

		return algorithms;
	}

	/**
	 * Method that instantiates and runs every chosen algorithm from GUI for Integer
	 * Problems
	 * @param optimizationProcess 
	 * 			  the parent Optimization Process reference that will be passed to the problem
	 * @param numberOfVariables
	 *            Int that represents the number of variables for the problem
	 *            instantiated
	 * @param problemName
	 *            String Problem name
	 * @param algorithmsSelected
	 *            List of algorithms select by the user
	 * @param lowerBounds
	 *            List of Integers that represent the minimum value for each problem
	 *            variable
	 * @param upperBounds
	 *            List of Integers that represent the maximum value for each problem
	 *            variable
	 * @param jarPaths
	 *            List with paths for each decision variable jar with evaluate
	 *            method
	 * @param maxEvaluations
	 * @return
	 */
	public static boolean IntegerBuilder(OptimizationProcess optimizationProcess, int numberOfVariables, String problemName, List<String> algorithmsSelected,
			List<Integer> lowerBounds, List<Integer> upperBounds, List<String> jarPaths, int maxEvaluations) {
		List<ExperimentProblem<IntegerSolution>> problemList = new ArrayList<>();
		IntegerProblem problem;
		maxEvaluations = maxEvaluations / INDEPENDENT_RUNS;
		try {
			problem = new IntegerProblem(optimizationProcess, numberOfVariables, lowerBounds, upperBounds, problemName, jarPaths);
			problemList.add(new ExperimentProblem<>(problem));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> algorithmList = configureIntegerAlgorithmList(
				problemList, algorithmsSelected, maxEvaluations);

		removeDirectoriesRecursively(new File(BASE_DIRECTORY + problemName));
		Experiment<IntegerSolution, List<IntegerSolution>> experiment = new ExperimentBuilder<IntegerSolution, List<IntegerSolution>>(
				"ExperimentsInteger").setAlgorithmList(algorithmList).setProblemList(problemList)
						.setExperimentBaseDirectory(BASE_DIRECTORY + problemName).setOutputParetoFrontFileName("FUN")
						.setOutputParetoSetFileName("VAR")
						.setReferenceFrontDirectory(BASE_DIRECTORY + problemName + "/referenceFronts")
						.setIndicatorList(Arrays.asList(new PISAHypervolume<IntegerSolution>()))
						.setIndependentRuns(INDEPENDENT_RUNS).setNumberOfCores(8).build();

		new ExecuteAlgorithms<>(experiment).run();
		try {
			new GenerateReferenceParetoFront(experiment).run();
			new ComputeQualityIndicators<>(experiment).run();
			new GenerateLatexTablesWithStatistics(experiment).run();
			new GenerateBoxplotsWithR<>(experiment).setRows(1).setColumns(1).run();
			return true;
		}  catch (JMetalException e) {
			throw new JMetalException(problemName);
		}catch (IOException e) {
			return false;
		}
	}

	/**
	 * Builds algorithms for the type Integer
	 * 
	 * @param problemList
	 * @param algorithmsSelected
	 * @param maxEvaluations
	 * @return
	 */
	private static List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> configureIntegerAlgorithmList(
			List<ExperimentProblem<IntegerSolution>> problemList, List<String> algorithmsSelected, int maxEvaluations) {

		List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> algorithms = new ArrayList<>();

		if (algorithmsSelected.contains("MOCell")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<IntegerSolution>> algorithm3 = new MOCellBuilder<>(problemList.get(i).getProblem(),
						new IntegerSBXCrossover(0.9, 20.0),
						new IntegerPolynomialMutation(1 / problemList.get(i).getProblem().getNumberOfVariables(), 20.0))
								.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm3, "MOCell", problemList.get(i).getTag()));
			}
		}

		if (algorithmsSelected.contains("SMSEMOA")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<IntegerSolution>> algorithm2 = new SMSEMOABuilder<>(problemList.get(i).getProblem(),
						new IntegerSBXCrossover(0.9, 20.0),
						new IntegerPolynomialMutation(1 / problemList.get(i).getProblem().getNumberOfVariables(), 20.0))
								.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm2, "SMSEMOA", problemList.get(i).getTag()));
			}
		}

		if (algorithmsSelected.contains("SPEA2")) {
			for (int i = 0; i < problemList.size(); i++) {
				SelectionOperator<List<IntegerSolution>, IntegerSolution> selection = new BinaryTournamentSelection<IntegerSolution>(
						new RankingAndCrowdingDistanceComparator<IntegerSolution>());
				Algorithm<List<IntegerSolution>> algorithm2 = new SPEA2Builder<>(
						(IntegerProblem) problemList.get(i).getProblem(), new IntegerSBXCrossover(1.0, 5.0),
						new IntegerPolynomialMutation(1.0 / problemList.get(i).getProblem().getNumberOfVariables(),
								10.0)).setSelectionOperator(selection).setMaxIterations(maxEvaluations/2 + 1)
										.setPopulationSize(2).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm2, "SPEA2", problemList.get(i).getTag()));
			}
		}

		if (algorithmsSelected.contains("NSGAII")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<IntegerSolution>> algorithm = new NSGAIIBuilder<>(problemList.get(i).getProblem(),
						new IntegerSBXCrossover(0.9, 20.0),
						new IntegerPolynomialMutation(1 / problemList.get(i).getProblem().getNumberOfVariables(), 20.0))
								.setMaxEvaluations(maxEvaluations).setPopulationSize(100).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm, "NSGAII", problemList.get(i).getTag()));
			}
		}

		if (algorithmsSelected.contains("PAES")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<IntegerSolution>> algorithm4 = new PAESBuilder<>(problemList.get(i).getProblem())
						.setMaxEvaluations(maxEvaluations).setArchiveSize(100).setBiSections(2)
						.setMutationOperator(new IntegerPolynomialMutation(
								1 / problemList.get(i).getProblem().getNumberOfVariables(), 20.0))
						.build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm4, "PAES", problemList.get(i).getTag()));
			}
		}

		if (algorithmsSelected.contains("RandomSearch")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<IntegerSolution>> algorithm5 = new RandomSearchBuilder<>(problemList.get(i).getProblem())
						.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm5, "RandomSearch", problemList.get(i).getTag()));
			}
		}

		if (algorithmsSelected.contains("NSGAIII")) {
			SelectionOperator<List<IntegerSolution>, IntegerSolution> selection = new BinaryTournamentSelection<IntegerSolution>();
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<IntegerSolution>> algorithm1 = new NSGAIIIBuilder<>(problemList.get(i).getProblem())
						.setCrossoverOperator(new IntegerSBXCrossover(1.0, 5.0))
						.setMutationOperator(new IntegerPolynomialMutation(
								1.0 / problemList.get(i).getProblem().getNumberOfVariables(), 10.0))
						.setSelectionOperator(selection).setMaxIterations(maxEvaluations/16 + maxEvaluations % 16).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm1, "NSGAIII", problemList.get(i).getTag()));
			}
		}

		return algorithms;
	}

	/**
	 * Method that instantiates and runs every chosen algorithm from GUI for Binary
	 * Problems
	 * @param optimizationProcess 
	 * 			  the parent Optimization Process reference that will be passed to the problem
	 * @param numberOfVariables
	 *            Int that represents the number of variables for the problem
	 *            instantiated
	 * @param problemName
	 *            String Problem name
	 * @param algorithmsSelected
	 *            List of algorithms select by the user
	 * @param jarPaths
	 *            List with paths for each decision variable jar with evaluate
	 *            method
	 * @param bitsPerVariable
	 *            Int bits per variable
	 * @param maxEvaluations
	 * @return
	 */
	public static boolean BinaryBuilder(OptimizationProcess optimizationProcess, int numberOfVariables, String problemName, List<String> algorithmsSelected,
			List<String> jarPaths, int bitsPerVariable, int maxEvaluations) {
		List<ExperimentProblem<BinarySolution>> problemList = new ArrayList<>();
		BinaryProblem problem;
		maxEvaluations = maxEvaluations / INDEPENDENT_RUNS;
		try {
			problem = new BinaryProblem(optimizationProcess, numberOfVariables, jarPaths, problemName, bitsPerVariable);
			problemList.add(new ExperimentProblem<>(problem));
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<ExperimentAlgorithm<BinarySolution, List<BinarySolution>>> algorithmList = configureBinaryAlgorithmList(
				problemList, algorithmsSelected, maxEvaluations);

		removeDirectoriesRecursively(new File(BASE_DIRECTORY + problemName));
		Experiment<BinarySolution, List<BinarySolution>> experiment = new ExperimentBuilder<BinarySolution, List<BinarySolution>>(
				"ExperimentsBinary").setAlgorithmList(algorithmList).setProblemList(problemList)
						.setExperimentBaseDirectory(BASE_DIRECTORY + problemName).setOutputParetoFrontFileName("FUN")
						.setOutputParetoSetFileName("VAR")
						.setReferenceFrontDirectory(BASE_DIRECTORY + problemName + "/referenceFronts")
						.setIndicatorList(Arrays.asList(new PISAHypervolume<BinarySolution>()))
						.setIndependentRuns(INDEPENDENT_RUNS).setNumberOfCores(8).build();

		new ExecuteAlgorithms<>(experiment).run();
		try {
			new GenerateReferenceParetoFront(experiment).run();
			new ComputeQualityIndicators<>(experiment).run();
			new GenerateLatexTablesWithStatistics(experiment).run();
			new GenerateBoxplotsWithR<>(experiment).setRows(1).setColumns(1).run();
			return true;
		} catch (JMetalException e) {
			throw new JMetalException(problemName);
		}catch (IOException e) {
			return false;
		}
	}

	/**
	 * Builds algorithms for the type Binary
	 * 
	 * @param problemList
	 * @param algorithmsSelected
	 * @param maxEvaluations
	 * @return
	 */
	private static List<ExperimentAlgorithm<BinarySolution, List<BinarySolution>>> configureBinaryAlgorithmList(
			List<ExperimentProblem<BinarySolution>> problemList, List<String> algorithmsSelected, int maxEvaluations) {

		List<ExperimentAlgorithm<BinarySolution, List<BinarySolution>>> algorithms = new ArrayList<>();

		if (algorithmsSelected.contains("MOCHC")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<BinarySolution>> algorithm = new MOCHCBuilder(
						(BinaryProblem) problemList.get(i).getProblem()).setMaxEvaluations(maxEvaluations)
								.setCrossover(new HUXCrossover(1.0))
								.setNewGenerationSelection(new RankingAndCrowdingSelection<BinarySolution>(100))
								.setCataclysmicMutation(new BitFlipMutation(0.35))
								.setParentSelection(new RandomSelection<BinarySolution>())
								.setEvaluator(new SequentialSolutionListEvaluator<BinarySolution>()).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm, "MOCH", problemList.get(i).getTag()));
			}
		}

		if (algorithmsSelected.contains("MOCell")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<BinarySolution>> algorithm3 = new MOCellBuilder<>(problemList.get(i).getProblem(),
						new SinglePointCrossover(1.0),
						new BitFlipMutation(1.0 / ((BinaryProblem) problemList.get(i).getProblem()).getNumberOfBits(0)))
								.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm3, "MOCell", problemList.get(i).getTag()));
			}
		}

		if (algorithmsSelected.contains("SMSEMOA")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<BinarySolution>> algorithm2 = new SMSEMOABuilder<>(problemList.get(i).getProblem(),
						new SinglePointCrossover(1.0),
						new BitFlipMutation(1.0 / ((BinaryProblem) problemList.get(i).getProblem()).getNumberOfBits(0)))
								.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm2, "SMSEMOA", problemList.get(i).getTag()));
			}
		}

		if (algorithmsSelected.contains("SPEA2")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<BinarySolution>> algorithm7 = new SPEA2Builder<>(problemList.get(i).getProblem(),
						new SinglePointCrossover(1.0),
						new BitFlipMutation(1.0 / ((BinaryProblem) problemList.get(i).getProblem()).getNumberOfBits(0)))
								.setPopulationSize(2).setMaxIterations(maxEvaluations/2 + 1).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm7, "SPEA2", problemList.get(i).getTag()));
			}
		}

		if (algorithmsSelected.contains("NSGAII")) {
			for (int i = 0; i < problemList.size(); i++) {
				double crossoverProbability = 0.9;
				SinglePointCrossover crossover = new SinglePointCrossover(crossoverProbability);

				double mutationProbability = 1.0 / ((BinaryProblem) problemList.get(i).getProblem()).getNumberOfBits(0);
				BitFlipMutation mutation = new BitFlipMutation(mutationProbability);

				BinaryTournamentSelection<BinarySolution> selection = new BinaryTournamentSelection<BinarySolution>();

				Algorithm<List<BinarySolution>> algorithm = new NSGAIIBuilder<BinarySolution>(
						problemList.get(i).getProblem(), crossover, mutation).setSelectionOperator(selection)
								.setMaxEvaluations(maxEvaluations).setPopulationSize(100).build();

				algorithms.add(new ExperimentAlgorithm<>(algorithm, "NSGAII", problemList.get(i).getTag()));
			}
		}

		if (algorithmsSelected.contains("PAES")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<BinarySolution>> algorithm5 = new PAESBuilder<>(problemList.get(i).getProblem())
						.setMaxEvaluations(maxEvaluations).setArchiveSize(100).setBiSections(2)
						.setMutationOperator(new BitFlipMutation(
								1.0 / ((BinaryProblem) problemList.get(i).getProblem()).getNumberOfBits(0)))
						.build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm5, "PAES", problemList.get(i).getTag()));
			}
		}

		if (algorithmsSelected.contains("WASFGA")) {
			for (int i = 0; i < problemList.size(); i++) {
				List<Double> referencePoint = new ArrayList<>();
				referencePoint.add(10.0);
				referencePoint.add(4.0);

				double crossoverProbability = 0.9;
				SinglePointCrossover crossover = new SinglePointCrossover(crossoverProbability);

				double mutationProbability = 1.0 / ((BinaryProblem) problemList.get(i).getProblem()).getNumberOfBits(0);
				BitFlipMutation mutation = new BitFlipMutation(mutationProbability);

				BinaryTournamentSelection<BinarySolution> selection = new BinaryTournamentSelection<BinarySolution>();

				Algorithm<List<BinarySolution>> algorithm = new WASFGA<BinarySolution>(problemList.get(i).getProblem(),
						100, maxEvaluations, crossover, mutation, selection, new SequentialSolutionListEvaluator<BinarySolution>(),
						mutationProbability, referencePoint);
				algorithms.add(new ExperimentAlgorithm<>(algorithm, "WASFGA", problemList.get(i).getTag()));
			}
		}

		if (algorithmsSelected.contains("RandomSearch")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<BinarySolution>> algorithm = new RandomSearchBuilder<>(problemList.get(i).getProblem())
						.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm, "RandomSearch", problemList.get(i).getTag()));
			}
		}

		if (algorithmsSelected.contains("NSGAIII")) {
			for (int i = 0; i < problemList.size(); i++) {
				double crossoverProbability = 0.9;
				CrossoverOperator<BinarySolution> crossover = new SinglePointCrossover(crossoverProbability);
			
				double mutationProbability = 1.0 / ((BinaryProblem) problemList.get(i).getProblem()).getNumberOfBits(0);
				BitFlipMutation mutation = new BitFlipMutation(mutationProbability);
				
				BinaryTournamentSelection<BinarySolution> selection = new BinaryTournamentSelection<BinarySolution>();

				Algorithm<List<BinarySolution>> algorithm = new NSGAIIIBuilder<BinarySolution>(
						problemList.get(i).getProblem())
						.setMutationOperator(mutation)
						.setPopulationSize(1)
						.setCrossoverOperator(crossover)
						.setSelectionOperator(selection)
						.setMaxIterations(maxEvaluations/16 + maxEvaluations % 16)
						.build();

				algorithms.add(new ExperimentAlgorithm<>(algorithm, "NSGAIII", problemList.get(i).getTag()));
			}
		}

		return algorithms;
	}

	private static boolean removeDirectoriesRecursively(File directory) {
		File[] allContents = directory.listFiles();
		if (allContents != null) {
			for (File f : allContents) {
				removeDirectoriesRecursively(f);
			}
		}
		return directory.delete();
	}
}