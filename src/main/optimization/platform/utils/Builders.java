package main.optimization.platform.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.gde3.GDE3Builder;
import org.uma.jmetal.algorithm.multiobjective.ibea.IBEABuilder;
import org.uma.jmetal.algorithm.multiobjective.mocell.MOCellBuilder;
import org.uma.jmetal.algorithm.multiobjective.mochc.MOCHCBuilder;
import org.uma.jmetal.algorithm.multiobjective.moead.MOEADBuilder;
import org.uma.jmetal.algorithm.multiobjective.moead.MOEADBuilder.Variant;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.paes.PAESBuilder;
import org.uma.jmetal.algorithm.multiobjective.randomsearch.RandomSearchBuilder;
import org.uma.jmetal.algorithm.multiobjective.smsemoa.SMSEMOABuilder;
import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2Builder;
import org.uma.jmetal.operator.impl.crossover.HUXCrossover;
import org.uma.jmetal.operator.impl.crossover.IntegerSBXCrossover;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.RandomSelection;
import org.uma.jmetal.operator.impl.selection.RankingAndCrowdingSelection;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.IntegerSolution;
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

import main.optimization.platform.jMetal.problems.BinaryProblem;
import main.optimization.platform.jMetal.problems.DoubleProblem;
import main.optimization.platform.jMetal.problems.IntegerProblem;

public class Builders {

	private static final int INDEPENDENT_RUNS = 5;
	private static final int maxEvaluations = 500;
	private static String experimentBaseDirectory = "experimentBaseDirectory";

	public static boolean DoubleBuilder(String algorithmSelected, List<String> decisionVariables,
			List<Double> lowerBounds, List<Double> upperBounds, List<String> jarPaths) {

		List<ExperimentProblem<DoubleSolution>> problemList = new ArrayList<>();
		DoubleProblem problem;
		try {
			problem = new DoubleProblem(decisionVariables, lowerBounds, upperBounds, jarPaths, "DoubleProblem");
			problemList.add(new ExperimentProblem<>(problem));
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithmList = configureDoubleAlgorithmList(
				problemList, algorithmSelected);

		Experiment<DoubleSolution, List<DoubleSolution>> experiment = new ExperimentBuilder<DoubleSolution, List<DoubleSolution>>(
				"ExperimentsDouble").setAlgorithmList(algorithmList).setProblemList(problemList)
						.setExperimentBaseDirectory(experimentBaseDirectory).setOutputParetoFrontFileName("FUN")
						.setOutputParetoSetFileName("VAR")
						.setReferenceFrontDirectory(experimentBaseDirectory + "/referenceFronts")
						.setIndicatorList(Arrays.asList(new PISAHypervolume<DoubleSolution>()))
						.setIndependentRuns(INDEPENDENT_RUNS).setNumberOfCores(8).build();

		new ExecuteAlgorithms<>(experiment).run();
		try {
			new GenerateReferenceParetoSetAndFrontFromDoubleSolutions(experiment).run();
			new ComputeQualityIndicators<>(experiment).run();
			new GenerateLatexTablesWithStatistics(experiment).run();
			new GenerateBoxplotsWithR<>(experiment).setRows(1).setColumns(1).run();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	private static List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> configureDoubleAlgorithmList(
			List<ExperimentProblem<DoubleSolution>> problemList, String algorithmSelected) {

		List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithms = new ArrayList<>();

		if (algorithmSelected.equals("MOEAD") || algorithmSelected.equals("ConstraintMOEAD")
				|| algorithmSelected.equals("MOEADDRA") || algorithmSelected.equals("MOEADSTM")
				|| algorithmSelected.equals("MOEADD")) {

			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<DoubleSolution>> algorithm6 = new MOEADBuilder(problemList.get(i).getProblem(),
						Variant.MOEAD).setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm6, "MOEAD", problemList.get(i).getTag()));
			}
			return algorithms;
		}

		else if (algorithmSelected.equals("GDE3")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<DoubleSolution>> algorithm3 = new GDE3Builder(
						(DoubleProblem) problemList.get(i).getProblem()).setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm3, "GDE3", problemList.get(i).getTag()));
			}
			return algorithms;
		}

		else if (algorithmSelected.equals("ABYSS")) {

		}

		else if (algorithmSelected.equals("DMOPSO")) {

		}

		else if (algorithmSelected.equals("SMPSO")) {

		}

		else if (algorithmSelected.equals("OMOPSO")) {

		}

		else if (algorithmSelected.equals("MOCell")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<DoubleSolution>> algorithm5 = new MOCellBuilder<>(problemList.get(i).getProblem(),
						new SBXCrossover(1.0, 5),
						new PolynomialMutation(1.0 / problemList.get(i).getProblem().getNumberOfVariables(), 10.0))
								.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm5, "MOCell", problemList.get(i).getTag()));
			}
			return algorithms;
		}

		else if (algorithmSelected.equals("IBEA")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<DoubleSolution>> algorithm4 = new IBEABuilder(problemList.get(i).getProblem())
						.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm4, "IBEA", problemList.get(i).getTag()));
			}
			return algorithms;
		}

		else if (algorithmSelected.equals("SMSEMOA")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<DoubleSolution>> algorithm2 = new SMSEMOABuilder<>(problemList.get(i).getProblem(),
						new SBXCrossover(1.0, 5),
						new PolynomialMutation(1.0 / problemList.get(i).getProblem().getNumberOfVariables(), 10.0))
								.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm2, "SMSEMOA", problemList.get(i).getTag()));
			}
			return algorithms;
		}

		else if (algorithmSelected.equals("SPEA2")) {

		}

		else if (algorithmSelected.equals("NSGAII")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<DoubleSolution>> algorithm = new NSGAIIBuilder<>(problemList.get(i).getProblem(),
						new SBXCrossover(1.0, 5),
						new PolynomialMutation(1.0 / problemList.get(i).getProblem().getNumberOfVariables(), 10.0))
								.setMaxEvaluations(2500).setPopulationSize(100).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm, problemList.get(i).getTag()));
			}
			return algorithms;
		}

		else if (algorithmSelected.equals("RNSGAII")) {

		}

		else if (algorithmSelected.equals("PAES")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<DoubleSolution>> algorithm7 = new PAESBuilder<>(problemList.get(i).getProblem())
						.setMaxEvaluations(maxEvaluations).setArchiveSize(100).setBiSections(2)
						.setMutationOperator(new PolynomialMutation(
								1.0 / problemList.get(i).getProblem().getNumberOfVariables(), 10.0))
						.build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm7, "PAES", problemList.get(i).getTag()));
			}
			return algorithms;
		}

		else if (algorithmSelected.equals("WASFGA")) {

		}

		else if (algorithmSelected.equals("PESA2")) {

		}

		else if (algorithmSelected.equals("RandomSearch")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<DoubleSolution>> algorithm8 = new RandomSearchBuilder<>(problemList.get(i).getProblem())
						.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm8, "RandomSearch", problemList.get(i).getTag()));
			}
			return algorithms;
		}

		else if (algorithmSelected.equals("NSGAIII")) {

		}

		return null;
	}

	public static boolean IntegerBuilder(String algorithmSelected, List<String> decisionVariables,
			List<Integer> lowerBounds, List<Integer> upperBound, String problemName, List<String> jarPaths) {

		List<ExperimentProblem<IntegerSolution>> problemList = new ArrayList<>();
		IntegerProblem problem;
		try {
			problem = new IntegerProblem(decisionVariables, lowerBounds, upperBound, problemName, jarPaths);
			problemList.add(new ExperimentProblem<>(problem));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> algorithmList = configureIntegerAlgorithmList(
				problemList, algorithmSelected);

		Experiment<IntegerSolution, List<IntegerSolution>> experiment = new ExperimentBuilder<IntegerSolution, List<IntegerSolution>>(
				"ExperimentsInteger").setAlgorithmList(algorithmList).setProblemList(problemList)
						.setExperimentBaseDirectory(experimentBaseDirectory).setOutputParetoFrontFileName("FUN")
						.setOutputParetoSetFileName("VAR")
						.setReferenceFrontDirectory(experimentBaseDirectory + "/referenceFronts")
						.setIndicatorList(Arrays.asList(new PISAHypervolume<IntegerSolution>()))
						.setIndependentRuns(INDEPENDENT_RUNS).setNumberOfCores(8).build();

		new ExecuteAlgorithms<>(experiment).run();
		try {
			new GenerateReferenceParetoFront(experiment).run();
			new ComputeQualityIndicators<>(experiment).run();
			new GenerateLatexTablesWithStatistics(experiment).run();
			new GenerateBoxplotsWithR<>(experiment).setRows(1).setColumns(1).run();
			return true;
		} catch (IOException e) {
			return false;
		}

	}

	private static List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> configureIntegerAlgorithmList(
			List<ExperimentProblem<IntegerSolution>> problemList, String algorithmSelected) {

		List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> algorithms = new ArrayList<>();

		if (algorithmSelected.equals("MOCell")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<IntegerSolution>> algorithm3 = new MOCellBuilder<>(problemList.get(i).getProblem(),
						new IntegerSBXCrossover(0.9, 20.0),
						new IntegerPolynomialMutation(1 / problemList.get(i).getProblem().getNumberOfVariables(), 20.0))
								.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm3, "MOCell", problemList.get(i).getTag()));
			}
			return algorithms;
		}

		else if (algorithmSelected.equals("IBEA")) {

		}

		else if (algorithmSelected.equals("SMSEMOA")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<IntegerSolution>> algorithm2 = new SMSEMOABuilder<>(problemList.get(i).getProblem(),
						new IntegerSBXCrossover(0.9, 20.0),
						new IntegerPolynomialMutation(1 / problemList.get(i).getProblem().getNumberOfVariables(), 20.0))
								.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm2, "SMSEMOA", problemList.get(i).getTag()));
			}
			return algorithms;
		}

		else if (algorithmSelected.equals("SPEA2")) {

		}

		else if (algorithmSelected.equals("NSGAII")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<IntegerSolution>> algorithm = new NSGAIIBuilder<>(problemList.get(i).getProblem(),
						new IntegerSBXCrossover(0.9, 20.0),
						new IntegerPolynomialMutation(1 / problemList.get(i).getProblem().getNumberOfVariables(), 20.0))
								.setMaxEvaluations(maxEvaluations).setPopulationSize(100).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm, "NSGAII", problemList.get(i).getTag()));
			}
			return algorithms;
		}

		else if (algorithmSelected.equals("RNSGAII")) {

		}

		else if (algorithmSelected.equals("PAES")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<IntegerSolution>> algorithm4 = new PAESBuilder<>(problemList.get(i).getProblem())
						.setMaxEvaluations(maxEvaluations).setArchiveSize(100).setBiSections(2)
						.setMutationOperator(new IntegerPolynomialMutation(
								1 / problemList.get(i).getProblem().getNumberOfVariables(), 20.0))
						.build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm4, "PAES", problemList.get(i).getTag()));
			}
			return algorithms;
		}

		else if (algorithmSelected.equals("WASFGA")) {

		}

		else if (algorithmSelected.equals("PESA2")) {

		}

		else if (algorithmSelected.equals("RandomSearch")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<IntegerSolution>> algorithm5 = new RandomSearchBuilder<>(problemList.get(i).getProblem())
						.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm5, "RandomSearch", problemList.get(i).getTag()));
			}
			return algorithms;
		}

		else if (algorithmSelected.equals("NSGAIII")) {

		}

		return null;
	}

	public static boolean BinaryBuilder(String algorithmSelected, List<String> decisionVariables, List<String> jarPaths,
			String problemName, int bit) {
		List<ExperimentProblem<BinarySolution>> problemList = new ArrayList<>();
		BinaryProblem problem;
		try {
			problem = new BinaryProblem(decisionVariables, jarPaths, problemName, bit);
			problemList.add(new ExperimentProblem<>(problem));
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<ExperimentAlgorithm<BinarySolution, List<BinarySolution>>> algorithmList = configureBinaryAlgorithmList(
				problemList, algorithmSelected);

		Experiment<BinarySolution, List<BinarySolution>> experiment = new ExperimentBuilder<BinarySolution, List<BinarySolution>>(
				"ExperimentsBinary").setAlgorithmList(algorithmList).setProblemList(problemList)
						.setExperimentBaseDirectory(experimentBaseDirectory).setOutputParetoFrontFileName("FUN")
						.setOutputParetoSetFileName("VAR")
						.setReferenceFrontDirectory(experimentBaseDirectory + "/referenceFronts")
						.setIndicatorList(Arrays.asList(new PISAHypervolume<BinarySolution>()))
						.setIndependentRuns(INDEPENDENT_RUNS).setNumberOfCores(8).build();

		new ExecuteAlgorithms<>(experiment).run();
		try {
			new GenerateReferenceParetoFront(experiment).run();
			new ComputeQualityIndicators<>(experiment).run();
			new GenerateLatexTablesWithStatistics(experiment).run();
			new GenerateBoxplotsWithR<>(experiment).setRows(1).setColumns(1).run();
			return true;
		} catch (IOException e) {
			return false;
		}

	}

	private static List<ExperimentAlgorithm<BinarySolution, List<BinarySolution>>> configureBinaryAlgorithmList(
			List<ExperimentProblem<BinarySolution>> problemList, String algorithmSelected) {

		List<ExperimentAlgorithm<BinarySolution, List<BinarySolution>>> algorithms = new ArrayList<>();

		if (algorithmSelected.equals("MOCHC")) {
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
			return algorithms;
		}

		else if (algorithmSelected.equals("MOCell")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<BinarySolution>> algorithm3 = new MOCellBuilder<>(problemList.get(i).getProblem(),
						new SinglePointCrossover(1.0),
						new BitFlipMutation(1.0 / ((BinaryProblem) problemList.get(i).getProblem()).getNumberOfBits(0)))
								.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm3, "MOCell", problemList.get(i).getTag()));
			}
			return algorithms;
		}

		else if (algorithmSelected.equals("IBEA")) {

		}

		else if (algorithmSelected.equals("SMSEMOA")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<BinarySolution>> algorithm2 = new SMSEMOABuilder<>(problemList.get(i).getProblem(),
						new SinglePointCrossover(1.0),
						new BitFlipMutation(1.0 / ((BinaryProblem) problemList.get(i).getProblem()).getNumberOfBits(0)))
								.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm2, "SMSEMOA", problemList.get(i).getTag()));
			}
			return algorithms;
		}

		else if (algorithmSelected.equals("SPEA2")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<BinarySolution>> algorithm7 = new SPEA2Builder<>(problemList.get(i).getProblem(),
						new SinglePointCrossover(1.0),
						new BitFlipMutation(
								1.0 / ((BinaryProblem) problemList.get(i).getProblem()).getNumberOfBits(0)))
										.setMaxIterations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm7, "SPEA2", problemList.get(i).getTag()));
			}
			return algorithms;
		}

		else if (algorithmSelected.equals("NSGAII")) {

		}

		else if (algorithmSelected.equals("RNSGAII")) {

		}

		else if (algorithmSelected.equals("PAES")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<BinarySolution>> algorithm5 = new PAESBuilder<>(problemList.get(i).getProblem())
						.setMaxEvaluations(maxEvaluations).setArchiveSize(100).setBiSections(2)
						.setMutationOperator(new BitFlipMutation(
								1.0 / ((BinaryProblem) problemList.get(i).getProblem()).getNumberOfBits(0)))
						.build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm5, "PAES", problemList.get(i).getTag()));
			}
			return algorithms;
		}

		else if (algorithmSelected.equals("WASFGA")) {

		}

		else if (algorithmSelected.equals("PESA2")) {

		}

		else if (algorithmSelected.equals("RandomSearch")) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<BinarySolution>> algorithm6 = new RandomSearchBuilder<>(problemList.get(i).getProblem())
						.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm6, "RandomSearch", problemList.get(i).getTag()));
			}
			return algorithms;
		}

		else if (algorithmSelected.equals("NSGAIII")) {

		}

		return null;
	}
}
