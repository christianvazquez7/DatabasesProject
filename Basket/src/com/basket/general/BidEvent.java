package com.basket.general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BidEvent implements Event
{
	private boolean accepted;
	public boolean isAccepted() {
		return accepted;
	}
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	Product product;
	byte[] picture;

	public byte[] getPicture() {
		return picture;
	}
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	private String bidTitle;
	private String endingTime;
	
	public String getBidTitle() {
		return bidTitle;
	}
	public void setBidTitle(String bidTitle) {
		this.bidTitle = bidTitle;
	}
	public String getEndingTime() {
		return endingTime;
	}
	public void setEndingTime(String endingTime) {
		this.endingTime = endingTime;
	}
	public String getbTitle() {
		return bidTitle;
	}
	public void setbTitle(String bTitle) {
		this.bidTitle = bTitle;
	}
	float rating;
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	String description;

	String features;
	
	
	double startingBid;
	
	int id;
	boolean finished;
	

	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
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

	public double getStartingBid() {
		return startingBid;
	}
	public void setStartingBid(double startingBid) {
		this.startingBid = startingBid;
	}

	@Override
	public String toString() {
		return "BidEvent [product=" + product +  "bidTitle=" + bidTitle
				+ ", endingTime=" + endingTime + ", rating=" + rating
				+ ", description=" + description + ", features=" + features
				+ ", startingBid=" + startingBid + ", id=" + id
				+ ", finalized=" + finished + ", minBid=" + minBid
				+ ", creator=" + creator + ", winningBid=" + winningBid + "]";
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Bid getWinningBid() {
		return winningBid;
	}
	public void setWinningBid(Bid winning) {
		this.winningBid = winning;
	}
	private String creator;
	private Bid winningBid;
	@Override
	
	public boolean isBid() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return bidTitle;
	}
	@Override
	public Double getAmount() {
		// TODO Auto-generated method stub
		if (this.getWinningBid()!=null)
		return this.getWinningBid().getAmmount();
		else return this.getMinBid();
	}
	@Override
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
