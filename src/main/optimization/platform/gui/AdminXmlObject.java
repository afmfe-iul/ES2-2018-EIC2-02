package main.optimization.platform.gui;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *Class that represents the Admin xml object
 * @author Daniel Caldeira
 * 
 *
 */
@XmlRootElement
public class AdminXmlObject {
	String email;
	String pathInput;
	String pathOutput;
	String password;
	
	/** Gets the Admin�s email .
	 * @return A string representing the Admin�s email.
	*/
	public String getEmail() {
		return email;
	}
	
	/** Sets the Admin�s password.
	 * @param passowrd A String containing the Admin�s password.
	*/
	@XmlElement
	public void setPassword(String password) {
		this.password = password;
	}
	
	/** Gets the Admin�s password .
	 * @return A string representing the Admin�s password.
	*/
	public String getPassword() {
		return password;
	}
	
	/** Sets the Admin�s email.
	 * @param email A String containing the Admin�s email.
	*/
	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}
	
	/** Gets the input path.
	 * @return A string representing the Input path that contain jars to be used as evaluate methods.
	*/
	public String getpathInput() {
		return pathInput;
	}
	
	/** Sets Input path.
	 * @param pathInput A String containing Input path.
	*/
	@XmlElement
	public void setpathInput(String pathInput) {
		this.pathInput = pathInput;
	}
	
	/** Gets the output path.
	 * @return A string representing the Output path that contain generated files from JMetal.
	*/
	public String getpathOutput() {
		return pathOutput;
	}
	
	/** Sets output path.
	 * @param pathOutput A String containing Outupt path.
	*/
	@XmlElement
	public void setpathOutput(String pathOutput) {
		this.pathOutput = pathOutput;
	}

}
