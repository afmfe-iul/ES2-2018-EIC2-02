package main.optimization.platform.gui;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a row on the table for the decision variables 
 * @author Daniel Caldeira
 *
 */
@XmlRootElement
public class TableRowCriteria {
	private String name;
	private String Path;
	
	/** Gets the name of the decision variable.
	 * @return  String representing the decision variable's name.
	*/
	public String getName() {
		return name;
	}
	
	/** Sets the name of the decision variable.
	 * @param name A String containing the decision variable's name .
	*/
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	
	/** Gets the path of a jar with the evaluate method corresponding to the decision variable.
	 * @return  String corresponding to the jar path with evaluate method for the decision variable.
	*/
	public String getPath() {
		return Path;
	}
	/** Sets the path of a jar with the evaluate method corresponding to the decision variable.
	 * @param path A String corresponding to the jar path with evaluate method for the decision variable.
	*/
	@XmlElement
	public void setPath(String path) {
		Path = path;
	}
	
}
