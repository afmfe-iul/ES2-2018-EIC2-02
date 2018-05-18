package main.optimization.platform.gui;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TableRowVariable {
	private String rule;
	private String minimo;
	private String maximo;
	private String forbidden;

	public String getRule() {
		return rule;
	}

	@XmlElement
	public void setRule(String rule) {
		this.rule = rule;
	}
	
	public String getMinimo() {
		return minimo;
	}
	@XmlElement
	public void setMinimo(String minimo) {
		this.minimo = minimo;
	}
	public String getMaximo() {
		return maximo;
	}
	@XmlElement
	public void setMaximo(String maximo) {
		this.maximo = maximo;
	}
	
	public String getForbidden() {
		return forbidden;
	}
	@XmlElement
	public void setForbidden(String forbidden) {
		this.forbidden = forbidden;
	}
}