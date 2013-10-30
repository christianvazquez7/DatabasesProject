package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.lists.BidList;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class BidOnEventRequest extends SpringAndroidSpiceRequest<BidOnEventList> {
	
	private String url=BasketConstants.externalIp+"/Basket.js/GetBids/";
	
	public BidOnEventRequest(int bidEventId) 
	{
		super(BidOnEventList.class);
		url+=bidEventId;
	}

	@Override
	public BidOnEventList loadDataFromNetwork() throws Exception 
	{	
		Log.d( "request", "loading from network" );
		return getRestTemplate().getForObject( url, BidOnEventList.class);
	}
}
