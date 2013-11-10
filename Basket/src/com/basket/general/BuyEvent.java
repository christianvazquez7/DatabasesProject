package com.basket.general;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BuyEvent implements Event
{
	float rating;
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	String creator;
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	Product product;
	String features;
	ArrayList<Review>reviews;
	
	double price;
	int day;
	int year;
	int month;
	int hour;
	int minute;
	int item_quantity =1;
	boolean finalized;
	String description;
	public int getitem_quantity() {
		return item_quantity;
	}
	public void setitem_quantity(int item_quantity) {
		this.item_quantity = item_quantity;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getFeatures() {
		return features;
	}
	public void setFeatures(String features) {
		this.features = features;
	}
	public ArrayList<Review> getReviews() {
		return reviews;
	}
	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}

	String btitle;
	public String getBtitle() {
		return btitle;
	}
	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}

	byte[] pic;
	public byte[] getPic() {
		return pic;
	}
	public void setPic(byte[] pic) {
		this.pic = pic;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private int id;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	@Override
	public String toString() {
		return "BuyEvent [rating=" + rating + ", creator=" + creator
				+ ", product=" + product + ", description=" + description
				+ ", features=" + features + ", reviews=" + reviews
				+ ", price=" + price + ", day=" + day + ", year=" + year
				+ ", month=" + month + ", hour=" + hour + ", minute=" + minute
				+ ", finalized=" + finalized + ", id=" + id + "]";
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
	@Override
	public boolean isBid() {
		return false;
	}
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return this.getBtitle();
	}
	@Override
	@JsonIgnore
	public Double getAmount() {
		// TODO Auto-generated method stub
		return this.getPrice();
	}
	@Override
	@JsonIgnore
	public String brand() {
		// TODO Auto-generated method stub
		return this.getProduct().getManufacturer();
	}
	@Override
	public String getSeller() {
		// TODO Auto-generated method stub
		return this.getCreator();
	}

}
