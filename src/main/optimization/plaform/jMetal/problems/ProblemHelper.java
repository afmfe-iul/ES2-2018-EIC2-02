package main.optimization.plaform.jMetal.problems;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import org.uma.jmetal.solution.Solution;

public class ProblemHelper {

	private Object instance;
	private Method method;

	/**
	 * Instantiates the class where the evaluate method provided is and invokes
	 * it.
	 * 
	 * @throws Exception
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ProblemHelper(String jarPath, List<String> decisionVariables) throws Exception {
		File file = new File(jarPath);
		URL[] urls;
		try {

			urls = new URL[] { file.toURI().toURL() };
			URLClassLoader child = new URLClassLoader(urls);
			Constructor c = Class.forName("optimization.Evaluate").getConstructor(List.class);
			Class classToLoad = Class.forName("optimization.Evaluate", true, child);
			method = classToLoad.getDeclaredMethod("evaluate");
			instance = c.newInstance(decisionVariables);
			Solution dummySolution = null;
			method.invoke(instance, dummySolution);

		} catch (MalformedURLException e) {
			throw new MalformedURLException("Jar path is not valid");
		} catch (IllegalAccessException e) {
			throw new IllegalAccessException("Do not have permission to acess this files");
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("The arguments passed are illegal or inappropriate");
		} catch (InvocationTargetException e) {
			throw new Exception("Method threw an exception");
		} catch (NoSuchMethodException e) {
			throw new NoSuchMethodException("Method not found");
		} catch (SecurityException e) {
			throw new SecurityException("Security violation");
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException("The class couldn´t be found");
		} catch (InstantiationException e) {
			throw new InstantiationException("Something went wrong with the class instantiation");
		}
	}

	@SuppressWarnings("rawtypes")
	public void evaluate(Solution solution) {
		try {
			method.invoke(instance, solution);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}