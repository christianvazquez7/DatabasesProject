package com.basket.general;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bid {
	private double ammount;
	private String	date;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	private String bidder;
	
	public String getBidTime() {
		return bidTime;
	}
	public void setBidTime(String bidTime) {
		this.bidTime = bidTime;
	}
	private String bidTime;
	
	public Bid(){};
	public Bid(double amount, int day,int month,int year,int hour,int minute, String user) {
		this.ammount = amount;
		this.bidder = user; 
	}
	public double getAmmount() {
		return ammount;
	}
	public void setAmmount(double ammount) {
		this.ammount = ammount;
	}
	@Override
	public String toString() {
		return "Bid [ammount=" + ammount + ", bidder=" + bidder + ", bidTime="
				+ bidTime + "]";
	}
	public String getBidder() {
		return bidder;
	}
	public void setBidder(String bidder) {
		this.bidder = bidder;
	}
	
}
