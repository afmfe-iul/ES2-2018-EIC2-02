package main.optimization.platform.gui;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TableRowCriteria {
	private String name;
	private String Path;
	

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
