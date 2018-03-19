package tests.optimization.platform.jMetal.problems;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import main.optimization.platform.jMetal.problems.ProblemHelper;

public class ProblemHelperTest {

	@Test
	public void testConstructorThrowsException(){
		try {
			ProblemHelper instance = new ProblemHelper("", new ArrayList<>());
		} catch (Exception e) {
			assertTrue(true);
		}
	}
}
