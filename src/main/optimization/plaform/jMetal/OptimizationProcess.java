package main.optimization.plaform.jMetal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OptimizationProcess {

	public Set<String> getPossibleDataTypes() {
		Set<String> result = new HashSet<String>();
		result.add("Integer");
		result.add("Binary");
		result.add("Double");
		result.add("Integer/Double");

		return result;
	}

	public List<String> getAlgorithmsFor(String dataType) {
		List<String> algoritms = new ArrayList<>();
		if(dataType.equals("Double")) {
			algoritms.add("NSGAII");
		}
		return algoritms;
	}

	public boolean run(List<String> decisionVariables, List<String> jarPaths, String dataType, String algorithm) {
		//TODO copy paste from last project NSGAII
		return true;
	}

}
