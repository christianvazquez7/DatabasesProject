package com.example.basket;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


public class BuyEvent 
{
	Product product;
	double price;
	int day;
	int year;
	int month;
	int hour;
	int minute;
	boolean finalized;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	@Override
	public String toString() {
		return "BuyEvent [product=" + product + ", price=" + price + ", day="
				+ day + ", year=" + year + ", month=" + month + ", hour="
				+ hour + ", minute=" + minute + ", finalized=" + finalized
				+ "]";
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
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
	public boolean isFinalized() {
		return finalized;
	}
	public void setFinalized(boolean finalized) {
		this.finalized = finalized;
	}

}
