package com.basket.general;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductBasket
{
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<BuyEvent> getBuyEvents() {
		return buyEvents;
	}
	public void setBuyEvents(ArrayList<BuyEvent> buyEvents) {
		this.buyEvents = buyEvents;
	}
	public ArrayList<BidEvent> getBidEvents() {
		return bidEvents;
	}
	public void setBidEvents(ArrayList<BidEvent> bidEvents) {
		this.bidEvents = bidEvents;
	}
	String name;
	ArrayList<BuyEvent> buyEvents;
	ArrayList<BidEvent> bidEvents;
	
}
