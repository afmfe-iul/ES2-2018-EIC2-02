package main.optimization.platform.gui;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TableRowCriteria {
	private int solutionKnown;
	private String name;
	private String Path;
	
	public int getSolutionKnown() {
		return solutionKnown;
	}
	@XmlElement
	public void setSolutionKnown(int solutionKnown) {
		this.solutionKnown = solutionKnown;
	}
	public String getName() {
		return name;
	}
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return Path;
	}
	@XmlElement
	public void setPath(String path) {
		Path = path;
	}
	
}
