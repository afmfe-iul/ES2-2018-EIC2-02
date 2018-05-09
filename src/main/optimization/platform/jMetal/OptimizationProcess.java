package main.optimization.platform.jMetal;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.util.experiment.Experiment;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.component.ComputeQualityIndicators;
import org.uma.jmetal.util.experiment.component.ExecuteAlgorithms;
import org.uma.jmetal.util.experiment.component.GenerateBoxplotsWithR;
import org.uma.jmetal.util.experiment.component.GenerateLatexTablesWithStatistics;
import org.uma.jmetal.util.experiment.component.GenerateReferenceParetoSetAndFrontFromDoubleSolutions;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

import main.optimization.platform.jMetal.problems.BinaryProblem;
import main.optimization.platform.jMetal.problems.DoubleProblem;
import main.optimization.platform.jMetal.problems.IntegerDoubleProblem;
import main.optimization.platform.jMetal.problems.IntegerPermutationProblem;
import main.optimization.platform.jMetal.problems.IntegerProblem;

public class OptimizationProcess {

	public static final int INDEPENDENT_RUNS = 5;
	public static final String EXPERIMENT_BASE_DIRECTORY = "experimentsBaseDirectory";

	// TODO Do you need to find the Data types dynamicaly or hardcoded?
	public Set<String> getPossibleDataTypes() {
		Set<String> result = new HashSet<String>();
		result.add("Binary");
		result.add("Integer");
		result.add("Double");

		return result;
	}

	/**
	 * Goes to the zip folder on the dependencies, scans the entire folder for all
	 * files filters the files and checks each one to see which type of solution is
	 * implemented in the algorithm
	 */

	@SuppressWarnings({ "resource" })
	public List<String> getAlgorithmsFor(String dataType) throws Exception {

		ArrayList<String> classNames = new ArrayList<String>();
		ZipInputStream zip;
		zip = new ZipInputStream(new FileInputStream("jmetal-algorithm-5.5.1-sources.jar"));

		ArrayList<String> totalAlgo = new ArrayList<String>();
		getGenericAlgo(totalAlgo);
		if (dataType.equals("Double") || dataType.equals("IntegerDouble")) {
			for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
				if (!entry.isDirectory() && entry.getName().endsWith(".java")
						&& entry.getName().contains("multiobjective") && !entry.getName().contains("util")
						&& !entry.getName().contains("Builder") && !entry.getName().contains("Measures")
						&& !entry.getName().contains("45") && !entry.getName().contains("Steady")
						&& !entry.getName().contains("Stopping")) {
					String className = entry.getName().replace('/', '.'); // including ".class"
					classNames.add(className.substring(0, className.length() - ".class".length() + 1));

					Scanner sc = new Scanner(zip);
					while (sc.hasNextLine()) {
						String line = sc.nextLine();
						if (line.contains("public class") && !line.startsWith(" ") && !line.contains("CellDE ")
								&& line.contains("Double")) {
							String[] array = className.split("\\.");
							totalAlgo.add(array[array.length - 2]);
						}
					}
				}
			}
			zip.close();
			return totalAlgo;
		}

		else if (dataType.equals("Binary")) {
			for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
				if (!entry.isDirectory() && entry.getName().endsWith(".java")
						&& entry.getName().contains("multiobjective") && !entry.getName().contains("util")
						&& !entry.getName().contains("Builder") && !entry.getName().contains("Measures")
						&& !entry.getName().contains("45") && !entry.getName().contains("Steady")
						&& !entry.getName().contains("Stopping")) {
					String className = entry.getName().replace('/', '.'); // including ".class"
					classNames.add(className.substring(0, className.length() - ".class".length() + 1));

					Scanner sc = new Scanner(zip);
					while (sc.hasNextLine()) {
						String line = sc.nextLine();
						if (line.contains("public class") && !line.startsWith(" ") && !line.contains("CellDE ")
								&& line.contains("Binary")) {
							String[] array = className.split("\\.");
							totalAlgo.add(array[array.length - 2]);
						}
					}
				}
			}
			zip.close();
			return totalAlgo;
		}

		zip.close();
		return totalAlgo;
	}

	public boolean run(List<String> decisionVariables, List<String> jarPaths, String dataType, String algorithm,
			String problemName) throws Exception {

		if (dataType.equals("Integer")) {
			// TODO trocar as listas bounds pelas recebidas pelo interface gráfico
			List<ExperimentProblem<IntegerSolution>> problemList = new ArrayList<>();
			List<Integer> bounds = new ArrayList<Integer>();
			IntegerProblem problem = new IntegerProblem(decisionVariables, bounds, bounds, "DoubleProblem", jarPaths);
			problemList.add(new ExperimentProblem<>(problem));
		
		}

		else if (dataType.equals("Binary")) {
			// TODO constructor bit?
			List<ExperimentProblem<BinarySolution>> problemList = new ArrayList<>();
			BinaryProblem problem = new BinaryProblem(decisionVariables, jarPaths, "BinaryProblem", 0);
			problemList.add(new ExperimentProblem<>(problem));
		}

		else if (dataType.equals("Double")) {

			List<ExperimentProblem<DoubleSolution>> problemList = new ArrayList<>();
			List<Double> lowerBounds = new ArrayList<Double>();
			List<Double> upperBounds = new ArrayList<Double>();
			for (int i = 0; i < decisionVariables.size(); i++) {
				lowerBounds.add(-5.0);
				upperBounds.add(5.0);
			}

			DoubleProblem problem = new DoubleProblem(decisionVariables, lowerBounds, upperBounds, jarPaths,
					"DoubleProblem");
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
				return true;
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
		// TODO o tempo é igual ao numero de iteraçoes (setMaxEvaluations(2500))
		for (int i = 0; i < problemList.size(); i++) {
			Algorithm<List<DoubleSolution>> algorithm = new NSGAIIBuilder<>(problemList.get(i).getProblem(),
					new SBXCrossover(1.0, 5),
					new PolynomialMutation(1.0 / problemList.get(i).getProblem().getNumberOfVariables(), 10.0))
							.setMaxEvaluations(2500).setPopulationSize(100).build();
			algorithms.add(new ExperimentAlgorithm<>(algorithm, problemList.get(i).getTag()));
		}
		return algorithms;
	}

	private static void getGenericAlgo(ArrayList<String> list) throws IOException {

		ArrayList<String> classNames = new ArrayList<String>();
		ZipInputStream zip;
		zip = new ZipInputStream(new FileInputStream("C:/Users/" + System.getProperty("user.name")
				+ "/.m2/repository/org/uma/jmetal/jmetal-algorithm/5.5.1/jmetal-algorithm-5.5.1-sources.jar"));

		for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {

			if (!entry.isDirectory() && entry.getName().endsWith(".java") && entry.getName().contains("multiobjective")
					&& !entry.getName().contains("CellDE") && !entry.getName().contains("GWASFGA")
					&& !entry.getName().contains("MOMBI") && !entry.getName().contains("util")
					&& !entry.getName().contains("Builder") && !entry.getName().contains("Measures")
					&& !entry.getName().contains("45") && !entry.getName().contains("Steady")
					&& !entry.getName().contains("Stopping")) {
				String className = entry.getName().replace('/', '.'); // including ".class"
				classNames.add(className.substring(0, className.length() - ".class".length() + 1));

				Scanner sc = new Scanner(zip);
				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					if (line.contains("public class") && !line.startsWith(" ") && !line.contains("CellDE ")
							&& !line.contains("Double") && !line.contains("Binary")) {
						String[] array = className.split("\\.");
						list.add(array[array.length - 2]);
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {

		OptimizationProcess p = new OptimizationProcess();
		for (String s : p.getAlgorithmsFor("Double")) {
			System.out.println(s);
		}
	}

}