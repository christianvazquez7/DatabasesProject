package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.Bid;
import com.basket.general.BidEvent;
import com.basket.general.BooleanContainer;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class BidRequest extends SpringAndroidSpiceRequest<BooleanContainer> {
	
	
	private Bid newBid;
	private BidEvent theEvent;
	public BidRequest(Bid newBid,BidEvent theEvent) 
	{
		super(BooleanContainer.class);
		this.newBid= newBid;
		this.theEvent=theEvent;
	
	
	}

	@Override
	public BooleanContainer loadDataFromNetwork() throws Exception 
	{
		
		String url = BasketConstants.externalIp+"/Basket.js/addBid/";
		url+=theEvent.getId();
		Log.d( "request", "loading from network" );	
		return getRestTemplate().postForObject(url, newBid,BooleanContainer.class);
	}

}
