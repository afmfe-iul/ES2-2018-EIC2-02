package tests.optimization.platform.gui;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import main.optimization.platform.gui.LayoutProblem;
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
		instance.setTipo(tipo);
		assertEquals("The getter for the field 'tipo' is not returning the same value that was setted.", tipo, instance.getTipo());
	}

	@Test
	public void testGetterAndSetterForInstance(){
		LayoutProblem instance = new LayoutProblem();
		List<TableRowVariable> list = new ArrayList<>();
		instance.setListVariable(list);
		assertNotNull("The getter for the field 'list' is returning null, when it shouldn't.", instance.getListVariable());
	}
}