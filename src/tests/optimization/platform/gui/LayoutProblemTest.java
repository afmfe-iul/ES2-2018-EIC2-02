package tests.optimization.platform.gui;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import main.optimization.platform.gui.LayoutProblem;
import main.optimization.platform.gui.TableRowCriteria;
import main.optimization.platform.gui.TableRowVariable;

public class LayoutProblemTest {

	@Test
	public void testGetterAndSetterForMaxWaitingTime(){
		LayoutProblem instance = new LayoutProblem();
		int maxWaitingTime = 0;
		instance.setMaxWaitingTime(maxWaitingTime);
		assertEquals("The getter for the field 'maxWaitingTime' is not returning the same value that was setted.", maxWaitingTime, instance.getMaxWaitingTime());
	}

	@Test
	public void testGetterAndSetterForVariablesNumber(){
		LayoutProblem instance = new LayoutProblem();
		int variablesNumber = 0;
		instance.setNumberVariables(variablesNumber);
		assertEquals("The getter for the field 'variablesNumber' is not returning the same value that was setted.", variablesNumber, instance.getNumberVariables());
	}

	@Test
	public void testGetterAndSetterForVariablesName(){
		LayoutProblem instance = new LayoutProblem();
		String variablesName = "variable1";
		instance.setVariablesName(variablesName);
		assertEquals("The getter for the field 'variablesName' is not returning the same value that was setted.", variablesName, instance.getVariablesName());		
	}

	@Test
	public void testGetterAndSetterForEmail(){
		LayoutProblem instance = new LayoutProblem();
		String email = "variable4";
		instance.setEmail(email);
		assertEquals("The getter for the field 'email' is not returning the same value that was setted.", email, instance.getEmail());
	}

	@Test
	public void testGetterAndSetterForProblemTitle(){
		LayoutProblem instance = new LayoutProblem();
		String problemTitle = "variable2";
		instance.setProblemTitle(problemTitle);
		assertEquals("The getter for the field 'problemTitle' is not returning the same value that was setted.", problemTitle, instance.getProblemTitle());
	}

	@Test
	public void testGetterAndSetterForProblemDescription(){
		LayoutProblem instance = new LayoutProblem();
		String problemDescription = "variable3";
		instance.setProblemDescription(problemDescription);
		assertEquals("The getter for the field 'problemDescription' is not returning the same value that was setted.", problemDescription, instance.getProblemDescription());
	}

	@Test
	public void testGetterAndSetterForTipo(){
		LayoutProblem instance = new LayoutProblem();
		String tipo = "variable5";
		instance.setType(tipo);
		assertEquals("The getter for the field 'tipo' is not returning the same value that was setted.", tipo, instance.getType());
	}

	@Test
	public void testGetterAndSetterForInstance(){
		LayoutProblem instance = new LayoutProblem();
		List<TableRowVariable> list = new ArrayList<>();
		instance.setListVariable(list);
		assertNotNull("The getter for the field 'list' is returning null, when it shouldn't.", instance.getListVariable());
	}
	
	@Test
	public void testGetterAndSetterBitsPerVariable(){
		LayoutProblem instance = new LayoutProblem();
		int bitsPerVariable = 10;
		instance.setBitsPerVariable(bitsPerVariable);
		assertEquals("The getter for the field 'bitsPerVariable' is not returning the same value that was setted.", bitsPerVariable, instance.getBitsPerVariable());
	}
	
	@Test
	public void testGetterAndSetterNumberCritiria(){
		LayoutProblem instance = new LayoutProblem();
		int numberCriteria = 10;
		instance.setNumberCriteria(numberCriteria);
		assertEquals("The getter for the field 'numberCritiria' is not returning the same value that was setted.", numberCriteria, instance.getNumberCriteria());
	}
	
	@Test
	public void testGetterAndSetterSolutionKnown(){
		LayoutProblem instance = new LayoutProblem();
		int SolutionKnown = 10;
		instance.setSolutionKnown(SolutionKnown);
		assertEquals("The getter for the field 'solutionKnown' is not returning the same value that was setted.", SolutionKnown, instance.getSolutionKnown());
	}
	
	@Test
	public void testGetterAndSetterIsAutomatic(){
		LayoutProblem instance = new LayoutProblem();
		Boolean isAutomatic = true;
		instance.setAutomatic(isAutomatic);
		assertEquals("The getter for the field 'isAutomatic' is not returning the same value that was setted.", isAutomatic, instance.isAutomatic());
	}
	
	@Test
	public void testGetterAndSetterListCritiria(){
		LayoutProblem instance = new LayoutProblem();
		List<TableRowCriteria> critirias =  new ArrayList<TableRowCriteria>();
		critirias.add(new TableRowCriteria());
		instance.setListCriteria(critirias);
		assertEquals("The getter for the field 'listCriteria' is not returning the same value that was setted.", critirias, instance.getListCriteria());
	}
	
	@Test
	public void testGetterAndSetterListAlgorithms(){
		LayoutProblem instance = new LayoutProblem();
		List<String> algorithms =  new ArrayList<String>();
		algorithms.add("teste");
		instance.setListAlgorithms(algorithms);
		assertEquals("The getter for the field 'listAlgorithms' is not returning the same value that was setted.", algorithms, instance.getListAlgorithms());
	}
}