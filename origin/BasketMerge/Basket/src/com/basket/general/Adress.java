package com.basket.general;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


public class Adress
{

	private String line1,line2, country, state, city;
	private int zipCode;
	
	
	public Adress(String line1, String line2, String city, 
			String state,int zipCode, String country) {
		super();
		this.line1 = line1;
		this.line2 = line2;
		this.country = country;
		this.state = state;
		this.city = city;
		this.zipCode = zipCode;
	}
	

	public Adress() {
		// TODO Auto-generated constructor stub
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
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Adress [line1=" + line1 + ", line2=" + line2 + ", country="
				+ country + ", state=" + state + ", city=" + city
				+ ", zipCode=" + zipCode + "]";
	}
	
	
}
