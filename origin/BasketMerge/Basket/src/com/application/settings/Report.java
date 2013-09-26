package com.application.settings;

public class Report
{
	private int day;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(double totalSales) {
		this.totalSales = totalSales;
	}
	public double getTotalGross() {
		return totalGross;
	}
	public void setTotalGross(double totalGross) {
		this.totalGross = totalGross;
	}
	private int month;
	private int year;
	private String type;
	private double totalSales;
	private double totalGross;
}
