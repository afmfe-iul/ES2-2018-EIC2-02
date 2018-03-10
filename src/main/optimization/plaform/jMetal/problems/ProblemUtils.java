package main.optimization.plaform.jMetal.problems;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import org.uma.jmetal.solution.DoubleSolution;

public class ProblemUtils {

	/**
	 * Instantiates the class where the evaluate method provided is and invokes
	 * it.
	 */
	public static void evaluate(String jarPath, DoubleSolution solution, int iteration) {
		File file = new File(jarPath);
		URL[] urls;
		try {
			urls = new URL[] { file.toURI().toURL() };
			URLClassLoader child = new URLClassLoader(urls);
			Class classToLoad = Class.forName("optimization.Evaluate", true, child);
			Method method = classToLoad.getDeclaredMethod("evaluate");
			Object instance = classToLoad.newInstance();
			Object result = method.invoke(instance, solution, iteration);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
