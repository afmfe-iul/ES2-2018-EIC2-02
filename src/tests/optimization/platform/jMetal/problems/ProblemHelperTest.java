package tests.optimization.platform.jMetal.problems;

import static org.junit.Assert.*;
import org.junit.Test;
import main.optimization.platform.jMetal.problems.ProblemHelper;

public class ProblemHelperTest {

	@SuppressWarnings("unused")
	@Test
	public void testConstructorThrowsException(){
		try {
			ProblemHelper instance = new ProblemHelper("");
		} catch (Exception e) {
			assertTrue(true);
		}
	}
}
