package com.basket.general;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductBasket
{
	@Override
	public String toString() {
		return "ProductBasket [name=" + bname + ", buyEvents=" + buyEvents
				+ ", bidEvents=" + bidEvents + "]";
	}
	public String bname;
	public ArrayList<BuyEvent> buyEvents;
	public ArrayList<BidEvent> bidEvents;
	
	public ProductBasket() {};

	public ProductBasket(String name) {
		this.bname = name;
		this.buyEvents = new ArrayList<BuyEvent>();
		this.bidEvents = new ArrayList<BidEvent>();
	}
	public String getName() {
		return bname;
	}
	public void setName(String name) {
		this.bname = name;
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
	
	
}
