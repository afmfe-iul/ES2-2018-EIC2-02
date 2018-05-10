package main.optimization.platform.gui;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LayoutProblem {
	private int maxWaitingTime;
	
	private int numberVariables;
	private int numberCriteria;
	private String variablesName;
	private String problemTitle;
	private String problemDescription;
	private String email;
	private boolean automatic;
	private int solutionKnown;
	// Integer/Double/Boolean
	private String tipo;
	private List<String> listAlgorithms;
	private List<TableRowVariable> listVariable;
	private List<TableRowCriteria> listCriteria;


	@XmlElementWrapper
	@XmlElement(name = "listAlgorithms")
	public List<String> getListAlgorithms() {
		return listAlgorithms;
	}

	public void setListAlgorithms(List<String> listAlgorithms) {
		this.listAlgorithms = listAlgorithms;
	}
	
	
	@XmlElementWrapper
	@XmlElement(name = "listVariable")
	public List<TableRowVariable> getListVariable() {
		return listVariable;
	}

	public void setListVariable(List<TableRowVariable> listVariable) {
		this.listVariable = listVariable;
	}
	
	@XmlElementWrapper
	@XmlElement(name = "listCriteria")
	public List<TableRowCriteria> getListCriteria() {
		return listCriteria;
	}

	public void setListCriteria(List<TableRowCriteria> listCriteria) {
		this.listCriteria = listCriteria;
	}


	public String getEmail() {
		return email;
	}

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
	
	public String getTipo() {
		return tipo;
	}

	@XmlElement
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getProblemTitle() {
		return problemTitle;
	}

	@XmlElement
	public void setProblemTitle(String problemTitle) {
		this.problemTitle = problemTitle;
	}

	public String getProblemDescription() {
		return problemDescription;
	}

	@XmlElement
	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}

	public int getMaxWaitingTime() {
		return maxWaitingTime;
	}

	@XmlElement
	public void setMaxWaitingTime(int maxWaitingTime) {
		this.maxWaitingTime = maxWaitingTime;
	}

	public int getSolutionKnown() {
		return solutionKnown;
	}

	@XmlElement
	public void setSolutionKnown(int solutionKnown) {
		this.solutionKnown = solutionKnown;
	}

	
	public int getNumberVariables() {
		return numberVariables;
	}

	@XmlElement
	public void setNumberVariables(int numberVariables) {
		this.numberVariables = numberVariables;
	}
	public int getNumberCriteria() {
		return numberCriteria;
	}

	@XmlElement
	public void setNumberCriteria(int numberCriteria) {
		this.numberCriteria = numberCriteria;
	}

	public String getVariablesName() {
		return variablesName;
	}

	@XmlElement
	public void setVariablesName(String variablesName) {
		this.variablesName = variablesName;
	}
}