package main.optimization.platform.gui;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *Class that represents the Problem xml object
 * @author Daniel Caldeira
 * 
 *
 */
@XmlRootElement
public class LayoutProblem {
	private int maxWaitingTime;
	private int numberVariables;
	private int numberCriteria;
	private int bitsPerVariable;
	private String variablesName;
	private String problemTitle;
	private String problemDescription;
	private String email;
	private boolean automatic;
	private int solutionKnown;
	private String type;
	private List<String> listAlgorithms;
	private List<TableRowVariable> listVariable;
	private List<TableRowCriteria> listCriteria;

	/** Gets the saved problem list of algorithms.
	 * @return List of Strings with the name of each algorithm.
	*/
	@XmlElementWrapper
	@XmlElement(name = "listAlgorithms")
	public List<String> getListAlgorithms() {
		return listAlgorithms;
	}
	
	/** Sets the list of algorithms.
	 * @param listAlgorithms  List of Strings with the name of each algorithm.
	*/
	public void setListAlgorithms(List<String> listAlgorithms) {
		this.listAlgorithms = listAlgorithms;
	}
	
	/** Gets the List of variables.
	 * @return List of objects TableRowVariable.
	*/
	@XmlElementWrapper
	@XmlElement(name = "listVariable")
	public List<TableRowVariable> getListVariable() {
		return listVariable;
	}
	
	/** Sets the list of table variables.
	 * @param listVariable  List of objects TableRowVariable.
	*/
	public void setListVariable(List<TableRowVariable> listVariable) {
		this.listVariable = listVariable;
	}
	
	/** Gets the List of decision variables.
	 * @return List of objects TableRowCriteria.
	*/
	@XmlElementWrapper
	@XmlElement(name = "listCriteria")
	public List<TableRowCriteria> getListCriteria() {
		return listCriteria;
	}

	/** Sets the list of table decision variables.
	 * @param listCriteria  List of objects TableRowCriteria.
	*/
	public void setListCriteria(List<TableRowCriteria> listCriteria) {
		this.listCriteria = listCriteria;
	}

	/** Gets user큦 email.
	 * @return String representing user큦 email.
	*/
	public String getEmail() {
		return email;
	}

	/** Sets user큦 email.
	 * @param email String representing user큦 email.
	*/
	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public boolean isAutomatic() {
		return automatic;
	}
	@XmlElement
	public void setAutomatic(boolean automatic) {
		this.automatic=automatic;
	}
	
	/** Gets problem type.
	 * @return String representing problem type.
	*/
	public String getType() {
		return type;
	}
	
	/** Sets problem type.
	 * @param type String representing problem type.
	*/
	@XmlElement
	public void setType(String type) {
		this.type = type;
	}

	/** Gets problem title.
	 * @return String representing problem title.
	*/
	public String getProblemTitle() {
		return problemTitle;
	}
	
	/** Sets problem title.
	 * @param problemTitle String representing problem title.
	*/
	@XmlElement
	public void setProblemTitle(String problemTitle) {
		this.problemTitle = problemTitle;
	}

	/** Gets problem description.
	 * @return String  Problem description.
	*/
	public String getProblemDescription() {
		return problemDescription;
	}
	
	/** Sets problem description.
	 * @param problemDescription String Problem description.
	*/
	@XmlElement
	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}

	/** Gets number of iterations that the user is willing to wait.
	 * @return Int number of iterations .
	*/
	public int getMaxWaitingTime() {
		return maxWaitingTime;
	}
	
	/** Sets number of iterations that the user is willing to wait.
	 * @param maxWaitingTime Int number of iterations.
	*/
	@XmlElement
	public void setMaxWaitingTime(int maxWaitingTime) {
		this.maxWaitingTime = maxWaitingTime;
	}

	/** Gets solution known by the user.
	 * @return solution known by the user.
	*/
	public int getSolutionKnown() {
		return solutionKnown;
	}
	
	/** Sets the value of the solution known by the user.
	 * @param solutionKnown Int solution known by the user.
	*/
	@XmlElement
	public void setSolutionKnown(int solutionKnown) {
		this.solutionKnown = solutionKnown;
	}

	/** Gets number of variables of the problem inputed by the user.
	 * @return Int number of variables of the problem inputed by the user.
	*/
	public int getNumberVariables() {
		return numberVariables;
	}
	
	/** Sets number of variables of the problem.
	 * @param numberVariables Int number of variables of the problem.
	*/
	@XmlElement
	public void setNumberVariables(int numberVariables) {
		this.numberVariables = numberVariables;
	}
	
	/** Gets number of decision variables of the problem inputed by the user.
	 * @return Int number of decision variables of the problem inputed by the user.
	*/
	public int getNumberCriteria() {
		return numberCriteria;
	}
	
	/**Sets number of decision variables of the problem inputed by the user.
	 * @param numberCriteria Int number of decision variables of the problem.
	*/
	@XmlElement
	public void setNumberCriteria(int numberCriteria) {
		this.numberCriteria = numberCriteria;
	}

	/** Gets variables name from the problem inputed by the user.
	 * @return String variables name from the problem inputed by the user.
	*/
	@XmlElement
	public String getVariablesName() {
		return variablesName;
	}

	/**Sets problem큦 variables name field.
	 * @param variablesName String representing variables name that you want to attribute to the problem.
	*/
	public void setVariablesName(String variablesName) {
		this.variablesName = variablesName;
	}

	/** Gets BitsPervariable from the problem inputed by the user.
	 * @return Int BitsPervariable from the problem inputed by the user.
	*/
	@XmlElement
	public int getBitsPerVariable() {
		return bitsPerVariable;
	}
	
	/**Sets problem큦 BitsPervariable field.
	 * @param bitsPerVariable Int representing BitsPervariable that you want to attribute to the problem.
	*/
	public void setBitsPerVariable(int bitsPerVariable) {
		this.bitsPerVariable = bitsPerVariable;
	}
}