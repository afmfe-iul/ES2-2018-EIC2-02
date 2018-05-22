package tests.optimization.platform.gui;

import static org.junit.Assert.*;

import org.junit.Test;

import main.optimization.platform.gui.TableRowCriteria;

public class TableRowCriteriaTest {

	@Test
	public void testGetterAndSetterName() {
		TableRowCriteria instance = new TableRowCriteria();
		String name = "criteria";
		instance.setName(name);
		assertEquals("The getter for the field 'name' is not returning the same value that was setted.", instance.getName(), name);

	}

	@Test
	public void testGetterAndSetterPath() {
		TableRowCriteria instance = new TableRowCriteria();
		String Path = "path";
		instance.setPath(Path);
		assertEquals("The getter for the field 'Path' is not returning the same value that was setted.", instance.getPath(), Path);

	}


}
