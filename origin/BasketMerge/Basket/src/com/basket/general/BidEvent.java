package com.basket.general;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BidEvent 
{
	public ArrayList<Bid> bids;
	public BidEvent() {
		bids = new ArrayList<Bid>();
	}

	
	
}
