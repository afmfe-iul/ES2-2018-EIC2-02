package main.optimization.platform.gui;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *  Represents a row on the table for problem variables
 * 
 *
 */
@XmlRootElement
public class TableRowVariable {
	private String name;
	private String minimo;
	private String maximo;
	private String forbidden;

	/** Gets the name of the variable.
	 * @return  String representing the variable's name.
	*/
	public String getName() {
		return name;
	}

	/** Sets the name of the variable.
	 * @param name A String containing the variable's name .
	*/
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	
	/** Gets the minimum value for the problem variable.
	 * @return  String representing minimum value for the problem variable.
	*/
	public String getMinimo() {
		return minimo;
	}
	
	/** Sets the minimum value for a problem variable.
	 * @param minimo A String containing the minimum value possible for a problem variable .
	*/
	@XmlElement
	public void setMinimo(String minimo) {
		this.minimo = minimo;
	}
	
	/** Gets the maximum value for the problem variable.
	 * @return  String representing maximum value for the problem variable.
	*/
	public String getMaximo() {
		return maximo;
	}
	
	/** Sets the maximum value for a problem variable.
	 * @param maximo A String containing the maximum value possible for a problem variable .
	*/
	@XmlElement
	public void setMaximo(String maximo) {
		this.maximo = maximo;
	}
	
	/** Gets the forbidden value for the problem variable.
	 * @return  String representing forbidden value for the problem variable.
	*/
	public String getForbidden() {
		return forbidden;
	}
	
	/** Sets the forbidden value for a problem variable.
	 * @param forbidden A String containing the forbidden value possible for a problem variable .
	*/
	@XmlElement
	public void setForbidden(String forbidden) {
		this.forbidden = forbidden;
	}
}