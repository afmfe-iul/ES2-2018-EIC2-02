package main.optimization.platform.jMetal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import main.optimization.platform.gui.LayoutProblem;
import main.optimization.platform.gui.TableRowCriteria;
import main.optimization.platform.gui.TableRowVariable;
import main.optimization.platform.utils.Builders;

public class OptimizationProcess {
	public static enum DATA_TYPES {
		BINARY("Binary"), INTEGER("Integer"), DOUBLE("Double");

		String type;

		private DATA_TYPES(String type) {
			this.type = type;
		}

		public String toString() {
			return type;
		}
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
		zip = new ZipInputStream(this.getClass().getResourceAsStream("/jmetal-algorithm-5.5.1-sources.jar"));

		ArrayList<String> totalAlgo = new ArrayList<String>();
		getGenericAlgo(totalAlgo, dataType);
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
								&& !line.contains("DMOPSO") && !line.contains("SMPSO") && line.contains("Double")) {
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
								&& !line.contains("DMOPSO") && !line.contains("SMPSO") && !line.contains("RNSGAII")
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

	/**
	 * Builds different types of problems considering the LayoutProblem passed as an
	 * argument
	 * 
	 * @param currentProblem
	 *            LayoutProblem object that indicates the specification from the
	 *            user input on the GUI about the problem to be optimized
	 */
	public boolean run(LayoutProblem currentProblem) {
		List<TableRowVariable> rows = currentProblem.getListVariable();
		List<TableRowCriteria> jarRows = currentProblem.getListCriteria();
		List<String> jarPaths = new ArrayList<>();
		for (int i = 0; i < jarRows.size(); i++) {
			jarPaths.add(jarRows.get(i).getPath());
		}

		// In case the user did not specified a max running time, we use a default
		if (currentProblem.getMaxWaitingTime() == 0) {
			currentProblem.setMaxWaitingTime(Builders.DEFAULT_ITERATIONS);
		}

		// Build a Double Problem
		if (currentProblem.getType().equals("Double")) {
			List<Double> lowerBounds = new ArrayList<>();
			List<Double> upperBounds = new ArrayList<>();
			for (int i = 0; i < rows.size(); i++) {
				lowerBounds.add(Double.parseDouble(rows.get(i).getMinimo()));
				upperBounds.add(Double.parseDouble(rows.get(i).getMaximo()));
			}
			return Builders.DoubleBuilder(currentProblem.getListVariable().size(), currentProblem.getProblemTitle(),
					currentProblem.getListAlgorithms(), lowerBounds, upperBounds, jarPaths,
					currentProblem.getMaxWaitingTime());

			// Build a Integer Problem
		} else if (currentProblem.getType().equals("Integer")) {
			List<Integer> lowerBounds = new ArrayList<>();
			List<Integer> upperBounds = new ArrayList<>();
			for (int i = 0; i < rows.size(); i++) {
				lowerBounds.add(Integer.valueOf(rows.get(i).getMinimo()));
				upperBounds.add(Integer.valueOf(rows.get(i).getMaximo()));
			}
			return Builders.IntegerBuilder(currentProblem.getListVariable().size(), currentProblem.getProblemTitle(),
					currentProblem.getListAlgorithms(), lowerBounds, upperBounds, jarPaths,
					currentProblem.getMaxWaitingTime());

			// Build a Binary Problem
		} else if (currentProblem.getType().equals("Binary")) {
			return Builders.BinaryBuilder(currentProblem.getListVariable().size(), currentProblem.getProblemTitle(),
					currentProblem.getListAlgorithms(), jarPaths, currentProblem.getBitsPerVariable(),
					currentProblem.getMaxWaitingTime());
		}
		return false;
	}

	/**
	 * 
	 * @param list
	 *            List of Strings that is going to have all the possible algorithms
	 *            for the specified dataType
	 * @param dataType
	 *            String indicates the type of problem
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	private void getGenericAlgo(ArrayList<String> list, String dataType) throws IOException {

		ArrayList<String> classNames = new ArrayList<String>();
		ZipInputStream zip;
		zip = new ZipInputStream(this.getClass().getResourceAsStream("/jmetal-algorithm-5.5.1-sources.jar"));

		for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
			if (!entry.isDirectory() && entry.getName().endsWith(".java") && entry.getName().contains("multiobjective")
					&& !entry.getName().contains("CellDE") && !entry.getName().contains("GWASFGA")
					&& !entry.getName().contains("MOMBI") && !entry.getName().contains("util")
					&& !entry.getName().contains("Builder") && !entry.getName().contains("Measures")
					&& !entry.getName().contains("45") && !entry.getName().contains("Steady")
					&& !entry.getName().contains("Stopping") && !entry.getName().contains("DMOPSO")
					&& !entry.getName().contains("SMPSO") && !entry.getName().contains("RNSGAII")
					&& !entry.getName().contains("WASFGA") && !entry.getName().contains("PESA2")) {
				String className = entry.getName().replace('/', '.'); // including ".class"
				classNames.add(className.substring(0, className.length() - ".class".length() + 1));
				Scanner sc = new Scanner(zip);
				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					if (!dataType.equals("Double")) {
						if (line.contains("public class") && !line.startsWith(" ") && !line.contains("CellDE ")
								&& !line.contains("Double") && !line.contains("Binary") && !line.contains("IBEA")) {
							String[] array = className.split("\\.");
							list.add(array[array.length - 2]);

						}
					} else {
						if (line.contains("public class") && !line.startsWith(" ") && !line.contains("CellDE ")
								&& !line.contains("Double") && !line.contains("Binary")) {
							String[] array = className.split("\\.");
							list.add(array[array.length - 2]);
						}
					}
				}
			}
		}
	}

}