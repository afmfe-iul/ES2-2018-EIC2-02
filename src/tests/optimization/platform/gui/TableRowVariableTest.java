package tests.optimization.platform.gui;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import main.optimization.platform.gui.TableRowVariable;

public class TableRowVariableTest {
	
	
	@Test
	public void testGetterAndSetterForRule(){
		TableRowVariable instance = new TableRowVariable();
		String rule = "test";
		instance.setRule(rule);
		assertEquals("The getter for the field 'rule' is not returning the same value that was setted.", instance.getRule(), rule);
	}
	
	@Test
	public void testGetterAndSetterForMinimo(){
		TableRowVariable instance = new TableRowVariable();
		String minimo = "test";
		instance.setMinimo(minimo);
		assertEquals("The getter for the field 'minimo' is not returning the same value that was setted.", instance.getMinimo(), minimo);
	}
	
	@Test
	public void testGetterAndSetterForMaximo(){
		TableRowVariable instance = new TableRowVariable();
		String maximo = "test";
		instance.setMaximo(maximo);
		assertEquals("The getter for the field 'maximo' is not returning the same value that was setted.", instance.getMaximo(), maximo);
	}
	
	@Test
	public void testGetterAndSetterForForbidden(){
		TableRowVariable instance = new TableRowVariable();
		String forbidden = "test";
		instance.setForbidden(forbidden);
		assertEquals("The getter for the field 'forbidden' is not returning the same value that was setted.", instance.getForbidden(), forbidden);
	}
}
