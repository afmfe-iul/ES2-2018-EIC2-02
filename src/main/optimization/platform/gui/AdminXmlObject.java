package main.optimization.platform.gui;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AdminXmlObject {
	String email;
	String paths;
	public String getEmail() {
		return email;
	}
	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPaths() {
		return paths;
	}
	@XmlElement
	public void setPaths(String paths) {
		this.paths = paths;
	}

}
