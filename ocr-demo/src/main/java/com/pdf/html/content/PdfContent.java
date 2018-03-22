package com.pdf.html.content;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Class for storing the pdf content.
 * 
 * @author jkolla
 *
 */
public class PdfContent implements Serializable {

	/**
	 * id for identifying the class class object.
	 */
	private static final long serialVersionUID = 2295388714811655073L;
	/**
	 * Storing the top value.
	 */
	private Double top;
	
	/**
	 * The field is for managing the rounded double.
	 */
	private Integer roundedTop;
	/**
	 * Storing the left value.
	 */
	private Double left;
	/**
	 * Storing the widht of value.
	 */
	private Double width;
	/**
	 * Storing the value.
	 */
	private String value;

	

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Double getTop() {
		return top;
	}

	public void setTop(Double top) {
		
		this.top = this.roundValue(top);
	}

	public Double getLeft() {
		return left;
	}

	public void setLeft(Double left) {
		this.left = left;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}
	
	private Double roundValue(Double d) {
		//return d;
		if (d == null) {
			return null;
		} else {
			DecimalFormat df = new DecimalFormat("#");
			df.setRoundingMode(RoundingMode.CEILING);
			return Double.parseDouble(df.format(d));
		}
	}

	public Integer getRoundedTop() {
		return roundedTop;
	}

	public void setRoundedTop(Integer roundedTop) {
		this.roundedTop = roundedTop;
	}

}
