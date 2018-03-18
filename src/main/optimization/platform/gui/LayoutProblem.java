package main.optimization.platform.gui;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LayoutProblem {
	int maxWaitingTime;
	int variablesNumber;
	String variablesName;
	String problemTitle;
	String problemDescription;
	String email;
	// Integer/Double/Boolean
	String tipo;
	List<TableRow> list;

	@XmlElementWrapper
	@XmlElement(name = "listaString")
	public List<TableRow> getList() {
		return list;
	}

	public void setList(List<TableRow> lista) {
		this.list = lista;
	}

	public String getEmail() {
		return email;
	}

	@XmlElement
	public void setEmail(String email) {
		this.email = email;
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

	public int getVariablesNumber() {
		return variablesNumber;
	}

	@XmlElement
	public void setVariablesNumber(int variablesNumber) {
		this.variablesNumber = variablesNumber;
	}

	public String getVariablesName() {
		return variablesName;
	}

	@XmlElement
	public void setVariablesName(String variablesName) {
		this.variablesName = variablesName;
	}
}