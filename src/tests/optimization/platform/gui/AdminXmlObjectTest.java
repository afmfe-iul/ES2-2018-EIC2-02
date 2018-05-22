package tests.optimization.platform.gui;

import static org.junit.Assert.*;

import org.junit.Test;

import main.optimization.platform.gui.AdminXmlObject;

public class AdminXmlObjectTest {

	@Test
	public void testGeterAndSetterEmail() {
		AdminXmlObject instance = new AdminXmlObject();
		String email = "email";
		instance.setEmail(email);
		assertEquals("The getter for the field 'email' is not returning the same value that was setted.",
				instance.getEmail(), email);
	}

	@Test
	public void testGetterAndSetterPassword() {
		AdminXmlObject instance = new AdminXmlObject();
		String pass = "pass";
		instance.setPassword(pass);
		assertEquals("The getter for the field 'password' is not returning the same value that was setted.",
				instance.getPassword(), pass);
	}

	@Test
	public void testGetterAndSetterpathInput() {
		AdminXmlObject instance = new AdminXmlObject();
		String pathInput = "pathInput";
		instance.setpathInput(pathInput);
		assertEquals("The getter for the field 'pathInput' is not returning the same value that was setted.",
				instance.getpathInput(), pathInput);
	}

	@Test
	public void testGetterAndSetterpathOutput() {
		AdminXmlObject instance = new AdminXmlObject();
		String pathOutput = "pathOutput";
		instance.setpathOutput(pathOutput);
		assertEquals("The getter for the field 'pathOutput' is not returning the same value that was setted.",
				instance.getpathOutput(), pathOutput);
		
	}

}
