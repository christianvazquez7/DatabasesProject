package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.BidEvent;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class DeleteBidEventRequest extends SpringAndroidSpiceRequest<Boolean> {
	private int pos;
	private BidEvent theEvent;
	public DeleteBidEventRequest(int pos) 
	{
		super(Boolean.class);
		this.pos= pos;	
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception 
	{
		
		String url = BasketConstants.externalIp+"/Basket.js/remBid/";
		url+=pos;
		Log.d( "request", "loading from network" );	
		getRestTemplate().put(url, null);
		return true;
	}
}
