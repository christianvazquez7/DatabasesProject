package com.basket.general;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)

public class Order 
{
	@Override
	public String toString() {
		return "Order [creditCard=" + creditCard + ", bidEvent=" + bidEvent
				+ ", shipAdress=" + shipAdress + ", buyEvents=" + buyEvents
				+ ", day=" + day + ", month=" + month + ", year=" + year
				+ ", account=" + account + "]";
	}
	public CreditCard creditCard;
	public BidEvent getBidEvent() {
		return bidEvent;
	}
	public void setBidEvent(BidEvent bidEvent) {
		this.bidEvent = bidEvent;
	}
	public String sDate;
	public String getsDate() {
		return sDate;
	}
	public void setsDate(String sDate) {
		this.sDate = sDate;
	}
	public BidEvent bidEvent;
	public CreditCard getCreditCard() 
	{
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	public Adress getShipAdress() {
		return shipAdress;
	}
	public void setShipAdress(Adress shipAdress) {
		this.shipAdress = shipAdress;
	}
	public ArrayList<BuyEvent> getBuyEvents() {
		return buyEvents;
	}
	public void setBuyEvents(ArrayList<BuyEvent> buyEvents) {
		this.buyEvents = buyEvents;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) 
	{
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getAccount() {
		return account;
	}
	public void setAccount(int account) {
		this.account = account;
	}
	public Adress shipAdress;
	public ArrayList<BuyEvent> buyEvents;
	public int day,month,year;
	public int account;
	

	
}
