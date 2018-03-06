package tests.optimization.plaform.gui;

import static org.junit.Assert.*;
import org.junit.Test;

public class MainLayoutTest {
	
	@Test
	public void constructorShouldNotInstantiatedTheJFrame(){
		MainLayout instance = new MainLayout();
		assertEquals("The JFrame should be initialized by the method run() and not in the constructor.", null, instance.frame);
	}
}
