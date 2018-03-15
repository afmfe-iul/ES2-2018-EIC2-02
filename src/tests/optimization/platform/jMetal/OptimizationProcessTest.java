package tests.optimization.platform.jMetal;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;

import main.optimization.platform.jMetal.OptimizationProcess;

public class OptimizationProcessTest {
	
	@Test
	public void getPossibleDataTypesShouldReturnAListWithJMetalTypes(){
		OptimizationProcess instance = new OptimizationProcess();
		Set<String> dataTypes = new HashSet<String>();
		dataTypes.addAll(Arrays.asList(new String[]{"Binary", "Integer", "Double", "Integer/Double"}));
		assertEquals("The method getPossibleDataTypes should return a set with the elements:"
				+ "Binary, Integer, Double and Integer/Double", dataTypes, instance.getPossibleDataTypes());
	}
	
	@Test
	public void getAlgorithmsForShouldReturnANonEmptyListWithTheApllicableAlgorithmsForAGivenDataType(){
		OptimizationProcess instance = new OptimizationProcess();
		String dataType = "Double";
		List<String> results = instance.getAlgorithmsFor(dataType);
		assertNotEquals("The method getAlgorithmsFor(String) should return a non empty List of Strings",
				0, results.size());
	}
	
	@Test
	public void runShouldInstantiateAProblemObjectAndRunAJMetalExperimentAndReturnTrueIfStartedItSuccessfuly() throws Exception{
		OptimizationProcess instance = new OptimizationProcess();
		List<String> decisionVariables = new ArrayList<String>();
		List<String> jarPaths = new ArrayList<String>();
		String dataType = "Double";
		String algorithm = "NSGAII";
		
		assertTrue("OptimizationProcess run method failed to run successfuly a DoubleProblem with the"
				+ "NSGAII algorithm", instance.run(decisionVariables, jarPaths, dataType, algorithm));
	}
}
