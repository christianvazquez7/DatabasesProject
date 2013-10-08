package com.basket.containers;

import java.util.List;

import com.basket.general.BidEvent;
import com.basket.general.BuyEvent;

public class EventList 
{
	private List<BuyEvent> buyEvents;
	public List<BuyEvent> getBuyEvents() {
		return buyEvents;
	}
	public void setBuyEvents(List<BuyEvent> buyEvents) {
		this.buyEvents = buyEvents;
	}
	public List<BidEvent> getBidEvents() {
		return bidEvents;
	}
	public void setBidEvents(List<BidEvent> bidEvents) {
		this.bidEvents = bidEvents;
	}
	private List<BidEvent> bidEvents;
	
	
	
}
