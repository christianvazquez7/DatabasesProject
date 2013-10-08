package com.basket.general;

public class Bid {
	private double ammount;
	public int day,month,year,hour,minute;
	private User bidder;
	
	
	public Bid(){};
	public Bid(double amount, int day,int month,int year,int hour,int minute, User user) {
		this.ammount = amount;
		this.day=day;
		this.month=month;
		this.year=year;
		this.hour=hour;
		this.minute=minute;
		this.bidder = user; 
	}
	public double getAmmount() {
		return ammount;
	}
	public void setAmmount(double ammount) {
		this.ammount = ammount;
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
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public User getBidder() {
		return bidder;
	}
	public void setBidder(User bidder) {
		this.bidder = bidder;
	}
	
}
