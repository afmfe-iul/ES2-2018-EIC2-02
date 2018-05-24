package main.optimization.platform.jMetal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.mail.MessagingException;

import main.optimization.platform.gui.LayoutProblem;
import main.optimization.platform.gui.MainLayout;
import main.optimization.platform.gui.TableRowCriteria;
import main.optimization.platform.gui.TableRowVariable;
import main.optimization.platform.utils.Builders;
import main.optimization.platform.utils.EmailSender;

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
	
	private MainLayout mainLayout;
	private LayoutProblem problem;
	private int optimizationTotalIterations; 
	private int optimizationProgress = 0;
	
	
	public OptimizationProcess(MainLayout mainLayout) {
		this.mainLayout = mainLayout;
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
		if (dataType.equals("Double")) {
			for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
				if (!entry.isDirectory() && entry.getName().endsWith(".java")
						&& entry.getName().contains("multiobjective") && !entry.getName().contains("util")
						&& !entry.getName().contains("Builder") && !entry.getName().contains("Measures")
						&& !entry.getName().contains("45") && !entry.getName().contains("Steady")
						&& !entry.getName().contains("Stopping") && !entry.getName().contains("ConstraintMOEAD")) {
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
		if(currentProblem.isAutomatic()) {
			try {
				currentProblem.setListAlgorithms(getAlgorithmsFor(currentProblem.getType()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.problem = currentProblem;
		List<TableRowVariable> rows = problem.getListVariable();
		List<TableRowCriteria> jarRows = problem.getListCriteria();
		List<String> jarPaths = new ArrayList<>();
		for (int i = 0; i < jarRows.size(); i++) {
			jarPaths.add(jarRows.get(i).getPath());
		}

		// In case the user did not specified a max running time, we use a default
		if (problem.getMaxWaitingTime() <= 0) {
			problem.setMaxWaitingTime(Builders.DEFAULT_ITERATIONS);
		}
		
		sendEmail(0);
		optimizationTotalIterations = (int)(problem.getMaxWaitingTime() * problem.getListAlgorithms().size());
		// Build a Double Problem
		if (problem.getType().equals("Double")) {
			List<Double> lowerBounds = new ArrayList<>();
			List<Double> upperBounds = new ArrayList<>();
			for (int i = 0; i < rows.size(); i++) {
				lowerBounds.add(Double.parseDouble(rows.get(i).getMinimo()));
				upperBounds.add(Double.parseDouble(rows.get(i).getMaximo()));
			}
			return Builders.DoubleBuilder(this, problem.getListVariable().size(), problem.getProblemTitle(),
					problem.getListAlgorithms(), lowerBounds, upperBounds, jarPaths,
					problem.getMaxWaitingTime());

			// Build a Integer Problem
		} else if (problem.getType().equals("Integer")) {
			List<Integer> lowerBounds = new ArrayList<>();
			List<Integer> upperBounds = new ArrayList<>();
			for (int i = 0; i < rows.size(); i++) {
				lowerBounds.add(Integer.valueOf(rows.get(i).getMinimo()));
				upperBounds.add(Integer.valueOf(rows.get(i).getMaximo()));
			}
			return Builders.IntegerBuilder(this, problem.getListVariable().size(), problem.getProblemTitle(),
					problem.getListAlgorithms(), lowerBounds, upperBounds, jarPaths,
					problem.getMaxWaitingTime());

			// Build a Binary Problem
		} else if (problem.getType().equals("Binary")) {
			return Builders.BinaryBuilder(this, problem.getListVariable().size(), problem.getProblemTitle(),
					problem.getListAlgorithms(), jarPaths, problem.getBitsPerVariable(),
					problem.getMaxWaitingTime());
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
			if (isDesiredEntry(entry)) {
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

	
	private boolean isDesiredEntry(ZipEntry entry) {
		return !entry.isDirectory() && entry.getName().endsWith(".java") && entry.getName().contains("multiobjective")
				&& !entry.getName().contains("CellDE") && !entry.getName().contains("GWASFGA")
				&& !entry.getName().contains("MOMBI") && !entry.getName().contains("util")
				&& !entry.getName().contains("Builder") && !entry.getName().contains("Measures")
				&& !entry.getName().contains("45") && !entry.getName().contains("Steady")
				&& !entry.getName().contains("Stopping") && !entry.getName().contains("DMOPSO")
				&& !entry.getName().contains("SMPSO") && !entry.getName().contains("RNSGAII")
				&& !entry.getName().contains("WASFGA") && !entry.getName().contains("PESA2");
	}

	public synchronized void callBack() {
		optimizationProgress++;
		if((double) optimizationProgress / optimizationTotalIterations == 0.25) {
			sendEmail(25);
		}else if((double) optimizationProgress / optimizationTotalIterations == 0.5) {
			sendEmail(50);
		}else if((double) optimizationProgress / optimizationTotalIterations == 0.75) {
			sendEmail(75);
		}else if((double) optimizationProgress / optimizationTotalIterations == 1) {
			sendEmail(100);
		}
	}

	private void sendEmail(int percentageCompleted) {
		List<String> to = new ArrayList<>();
		to.add(problem.getEmail());
		
		String subject = "Optimização em curso: " + problem.getProblemTitle();
		String body;
		if(percentageCompleted == 0) {
			body = "Muito obrigado por usar esta plataforma de otimização. Será informado por email "
					+ "sobre o progresso do processo de otimização, quando o processo de otimização tiver atingido 25%, 50%, "
					+ "75% do total do número de avaliações, e também quando o processo tiver "
					+ "terminado, com sucesso ou devido à ocorrência de erros.";
		}else {
			body = "De momento o processo de otimização encontra-se a " + percentageCompleted + "% do número de avaliações!";
		}
		File attachment = new File("temp.xml");
		mainLayout.writeXmlToFile(attachment, problem);
		
		EmailSender email = new EmailSender(mainLayout.getAdminMail(), mainLayout.getAdminPass(), to, subject, body);
		email.addToCC(mainLayout.getAdminMail());
		email.addAttachment(attachment);
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				
				try {
					email.sendFromGMail();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				attachment.delete();
			}
		});
		t.start();
	}
}