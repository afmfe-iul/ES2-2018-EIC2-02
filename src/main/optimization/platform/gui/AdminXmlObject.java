package main.optimization.platform.gui;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AdminXmlObject {
	String email;
	String pathInput;
	String pathOutput;
	
	public String getEmail() {
		return email;
	}
	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}
	public String getpathInput() {
		return pathInput;
	}
	@XmlElement
	public void setpathInput(String pathInput) {
		this.pathInput = pathInput;
	}
	public String getpathOutput() {
		return pathOutput;
	}
	@XmlElement
	public void setpathOutput(String pathOutput) {
		this.pathOutput = pathOutput;
	}

}
