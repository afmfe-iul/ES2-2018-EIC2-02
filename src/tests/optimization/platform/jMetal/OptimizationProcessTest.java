package tests.optimization.platform.jMetal;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;

import main.optimization.platform.gui.MainLayout;
import main.optimization.platform.jMetal.OptimizationProcess;
import main.optimization.platform.jMetal.OptimizationProcess.DATA_TYPES;

public class OptimizationProcessTest {
	
	@Test
	public void getPossibleDataTypesShouldReturnAListWithJMetalTypes(){
		Set<String> testCases = new HashSet<String>();
		testCases.addAll(Arrays.asList(new String[]{"Binary", "Integer", "Double"}));
		Set<String> dataTypes = new HashSet<String>();
		for(DATA_TYPES t : DATA_TYPES.values()) {
			dataTypes.add(t.toString());
		}
		
		for(String testType : testCases) {
			assertTrue("The OptimizationProcess class should contain the " + testType + " data type.", dataTypes.contains(testType));
		}
	} 
	
	@Test
	public void getAlgorithmsForShouldReturnANonEmptyListWithTheApllicableAlgorithmsForAGivenDataType() throws Exception{
		OptimizationProcess instance = new OptimizationProcess(new MainLayout());
		String dataType = "Double";
		
		List<String> results = instance.getAlgorithmsFor(dataType);
		assertNotEquals("The method getAlgorithmsFor(String) should return a non empty List of Strings",
				0, results.size());
		
		dataType = "Binary";
		results = instance.getAlgorithmsFor(dataType);
		assertNotEquals("The method getAlgorithmsFor(String) should return a non empty List of Strings",
				0, results.size());
		
		dataType = "IntegerDouble";
		results = instance.getAlgorithmsFor(dataType);
		assertNotEquals("The method getAlgorithmsFor(String) should return a non empty List of Strings",
				0, results.size());
		
		dataType = "Integer";
		results = instance.getAlgorithmsFor(dataType);
		assertNotEquals("The method getAlgorithmsFor(String) should return a non empty List of Strings",
				0, results.size());
		
	}

//	@Test
//	public void runShouldInstantiateAProblemObjectAndRunAJMetalExperimentAndReturnFalseIfItFails() throws Exception{
//		OptimizationProcess instance = new OptimizationProcess();
//		List<String> decisionVariables = new ArrayList<String>();
//		List<String> jarPaths = new ArrayList<String>();
//		String algorithm = "NSGAII";
//		String problemName = "TestProblem";
//
//		String dataType = "Double";
//		
//		assertFalse("OptimizationProcess run method didn't fail when it should.",
//				instance.run(decisionVariables, jarPaths, dataType, algorithm, problemName));
//	}
	
}
