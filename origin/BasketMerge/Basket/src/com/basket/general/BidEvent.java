package com.basket.general;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BidEvent implements Event
{
	Product product;

	String description;

	String features;
	ArrayList<Review>reviews;
	
	
	double startingBid;
	int sday;
	int syear;
	int smonth;
	int shour;
	int sminute;
	int id;
	boolean finalized;
	
	public boolean isFinalized() {
		return finalized;
	}
	public void setFinalized(boolean finalized) {
		this.finalized = finalized;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	int fday;
	int fyear;
	int fmonth;
	int fhour;
	int fminute;
	private List<Bid> bids;
	private double minBid;
	
	public double getMinBid() {
		return minBid;
	}
	public void setMinBid(double minBid) {
		this.minBid = minBid;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
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
	public double getStartingBid() {
		return startingBid;
	}
	public void setStartingBid(double startingBid) {
		this.startingBid = startingBid;
	}
	public int getSday() {
		return sday;
	}
	public void setSday(int sday) {
		this.sday = sday;
	}
	public int getSyear() {
		return syear;
	}
	public void setSyear(int syear) {
		this.syear = syear;
	}
	public int getSmonth() {
		return smonth;
	}
	public void setSmonth(int smonth) {
		this.smonth = smonth;
	}
	public int getShour() {
		return shour;
	}
	public void setShour(int shour) {
		this.shour = shour;
	}
	public int getSminute() {
		return sminute;
	}
	public void setSminute(int sminute) {
		this.sminute = sminute;
	}
	public int getFday() {
		return fday;
	}
	public void setFday(int fday) {
		this.fday = fday;
	}
	public int getFyear() {
		return fyear;
	}
	public void setFyear(int fyear) {
		this.fyear = fyear;
	}
	public int getFmonth() {
		return fmonth;
	}
	public void setFmonth(int fmonth) {
		this.fmonth = fmonth;
	}
	public int getFhour() {
		return fhour;
	}
	public void setFhour(int fhour) {
		this.fhour = fhour;
	}
	public int getFminute() {
		return fminute;
	}
	public void setFminute(int fminute) {
		this.fminute = fminute;
	}
	public List<Bid> getBids() {
		return bids;
	}
	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	public Bid getWinning() {
		return winning;
	}
	public void setWinning(Bid winning) {
		this.winning = winning;
	}
	private User creator;
	private Bid winning;
	@Override
	
	public boolean isBid() {
		// TODO Auto-generated method stub
		return true;
	}





}
