package com.example.basket;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


public class Adress
{
	
	@Override
	public String toString() {
		return "Adress [line1=" + line1 + ", line2=" + line2 + ", country="
				+ country + ", zipCode=" + zipCode + "]";
	}
	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getZipCode() {
		return zipCode;
	}
	public void setZipCode(int zipcode) {
		this.zipCode = zipcode;
	}
	private String line1,line2, country;
	private int zipCode;
	
}
