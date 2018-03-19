package tests.optimization.platform.gui;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import main.optimization.platform.gui.TableRow;

public class TableRowTest {
	
	@Test
	public void testGetterAndSetterForName(){
		TableRow instance = new TableRow();
		String name = "test";
		instance.setName(name);
		assertEquals("The getter for the field 'name' is not returning the same value that was setted.", instance.getName(), name);
	}
	
	@Test
	public void testGetterAndSetterForRule(){
		TableRow instance = new TableRow();
		String rule = "test";
		instance.setRule(rule);
		assertEquals("The getter for the field 'rule' is not returning the same value that was setted.", instance.getRule(), rule);
	}
	
	@Test
	public void testGetterAndSetterForMinimo(){
		TableRow instance = new TableRow();
		String minimo = "test";
		instance.setMinimo(minimo);
		assertEquals("The getter for the field 'minimo' is not returning the same value that was setted.", instance.getMinimo(), minimo);
	}
	
	@Test
	public void testGetterAndSetterForMaximo(){
		TableRow instance = new TableRow();
		String maximo = "test";
		instance.setMaximo(maximo);
		assertEquals("The getter for the field 'maximo' is not returning the same value that was setted.", instance.getMaximo(), maximo);
	}
	
	@Test
	public void testGetterAndSetterForForbidden(){
		TableRow instance = new TableRow();
		String forbidden = "test";
		instance.setForbidden(forbidden);
		assertEquals("The getter for the field 'forbidden' is not returning the same value that was setted.", instance.getForbidden(), forbidden);
	}
}
