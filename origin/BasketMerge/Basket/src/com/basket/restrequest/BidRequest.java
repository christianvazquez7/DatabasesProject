package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.Bid;
import com.basket.general.BidEvent;
import com.basket.general.ProductBasket;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class BidRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	
	private Bid newBid;
	private BidEvent theEvent;
	public BidRequest(Bid newBid,BidEvent theEvent) 
	{
		super(Boolean.class);
		this.newBid= newBid;
		this.theEvent=theEvent;
	
	
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception 
	{
		
		String url = BasketConstants.externalIp+"/Basket.js/addBid/";
		url+=theEvent.getId();
		Log.d( "request", "loading from network" );	
		getRestTemplate().put(url, newBid);
		return true;
	}

}
