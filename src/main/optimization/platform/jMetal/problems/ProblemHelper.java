package main.optimization.platform.jMetal.problems;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import org.uma.jmetal.solution.Solution;

/**
 * Class responsible for instantiating and invoking the evaluated method inside each jar provided by the user 
 * @author Tiago Feliciano
 *
 */
public class ProblemHelper {

	private Object instance;
	private Method method;

	/**
	 * Instantiates the class where the evaluate method provided is and invokes
	 * it.
	 * @param jarpath String where the jar with evaluate method is located
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ProblemHelper(String jarPath) throws Exception {
		File file = new File(jarPath);
		URL[] urls;
		try {
			urls = new URL[] { file.toURI().toURL() };
			URLClassLoader child = new URLClassLoader(urls);
			Class classToLoad = Class.forName("optimization.Evaluate", true, child);
			Constructor c = classToLoad.getConstructor();
			method = classToLoad.getDeclaredMethod("evaluate", Solution.class);
			instance = c.newInstance();
			Solution dummySolution = null;
			method.invoke(instance, dummySolution);
		} catch (MalformedURLException e) {
			throw new MalformedURLException("Jar path is not valid");
		} catch (IllegalAccessException e) {
			throw new IllegalAccessException("Do not have permission to acess this files");
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("The arguments passed are illegal or inappropriate");
		} catch (InvocationTargetException e) {
			throw new Exception("Evaluate method from the jar threw an exception");
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

	
	/**
	 * Invokes evaluate method present inside the jar 
	 * @param solution Solution passed as a parameter to the jar evaluate method
	 */
	@SuppressWarnings("rawtypes")
	public void evaluate(Solution solution) {
		try {
			method.invoke(instance, solution);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}