package main.optimization.platform.jMetal;

import java.io.FileInputStream;
import java.io.IOException;
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

	/**
	 * Goes to the zip folder on the dependencies, scans the entire folder for all
	 * files filters the files and checks each one to see which type of solution is
	 * implemented in the algorithm
	 */
	@SuppressWarnings({ "resource", "unused" })
	public List<String> getAlgorithmsFor(String dataType) throws Exception {

		URL[] classLoaderUrls = new URL[] { new URL("file:///Users/" + System.getProperty("user.name")
				+ "/.m2/repository/org/uma/jmetal/jmetal-algorithm/5.5.1/jmetal-algorithm-5.5.1-sources.jar") };
		URLClassLoader classLoader = new URLClassLoader(classLoaderUrls);
		ArrayList<String> classNames = new ArrayList<String>();
		ZipInputStream zip;
		zip = new ZipInputStream(new FileInputStream("C:/Users/" + System.getProperty("user.name")
				+ "/.m2/repository/org/uma/jmetal/jmetal-algorithm/5.5.1/jmetal-algorithm-5.5.1-sources.jar"));

		if (dataType.equals("Double")) {
			ArrayList<String> doubleAlgorithms = new ArrayList<String>();

			for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
				if (!entry.isDirectory() && entry.getName().endsWith(".java")
						&& entry.getName().contains("multiobjective") && !entry.getName().contains("util")
						&& !entry.getName().contains("Builder") && !entry.getName().contains("Measures")) {
					String className = entry.getName().replace('/', '.'); // including ".class"
					classNames.add(className.substring(0, className.length() - ".class".length() + 1));

					Scanner sc = new Scanner(zip);
					while (sc.hasNextLine()) {
						String line = sc.nextLine();
						if (line.contains("public class") && !line.startsWith(" ") && !line.contains("CellDE ")
								&& line.contains("Double")) {
							String[] array = className.split("\\.");
							doubleAlgorithms.add(array[array.length - 2]);
						}
					}
				}
			}
			return doubleAlgorithms;
		}

		if (dataType.equals("Binary")) {
			ArrayList<String> binaryAlgorithms = new ArrayList<String>();

			for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
				if (!entry.isDirectory() && entry.getName().endsWith(".java")
						&& entry.getName().contains("multiobjective") && !entry.getName().contains("util")
						&& !entry.getName().contains("Builder") && !entry.getName().contains("Measures")) {
					String className = entry.getName().replace('/', '.'); // including ".class"
					classNames.add(className.substring(0, className.length() - ".class".length() + 1));

					Scanner sc = new Scanner(zip);
					while (sc.hasNextLine()) {
						String line = sc.nextLine();
						if (line.contains("public class") && !line.startsWith(" ") && !line.contains("CellDE ")
								&& line.contains("Binary")) {
							String[] array = className.split("\\.");
							binaryAlgorithms.add(array[array.length - 2]);
						}
					}
				}
			}
			return binaryAlgorithms;
		}
		if (!dataType.equals("Double") && !dataType.equals("Binary")) {
			ArrayList<String> otherAlgorithms = new ArrayList<String>();

			for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
				if (!entry.isDirectory() && entry.getName().endsWith(".java")
						&& entry.getName().contains("multiobjective") && !entry.getName().contains("util")
						&& !entry.getName().contains("Builder") && !entry.getName().contains("Measures")) {
					String className = entry.getName().replace('/', '.'); // including ".class"
					classNames.add(className.substring(0, className.length() - ".class".length() + 1));

					Scanner sc = new Scanner(zip);
					while (sc.hasNextLine()) {
						String line = sc.nextLine();
						if (line.contains("public class") && !line.startsWith(" ") && !line.contains("CellDE ")
								&& !line.contains("Double") && !line.contains("Binary")) {
							String[] array = className.split("\\.");
							otherAlgorithms.add(array[array.length - 2]);
						}
					}
				}
			}
			return otherAlgorithms;
		}
		zip.close();
		return classNames;
	}

	// TODO do the UPPER and LOWER Bounds on constructor of the problem?
	// TODO name of problem from GUI input??
	public boolean run(List<String> decisionVariables, List<String> jarPaths, String dataType, String algorithm,
			String problemName) throws Exception {
		if (decisionVariables == null || jarPaths == null || decisionVariables.size() == 0 || jarPaths.size() == 0) {
			return false;
		}

		if (dataType.equals("Double")) {
			List<ExperimentProblem<DoubleSolution>> problemList = new ArrayList<>();
			// Constructor was updated
			// TODO trocar as listas bounds pelas recebidas pelo interface gráfico
			List<Double> bounds = new ArrayList<Double>();
			DoubleProblem problem = new DoubleProblem(decisionVariables, bounds, bounds, jarPaths, "DoubleProblem");
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

	// TODO Main method for testing purposes
	// TODO remove this after the UI is done
	public static void main(String[] args) throws Exception {

	}
}