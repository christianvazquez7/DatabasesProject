package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.lists.BidList;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class WinBidRequest extends SpringAndroidSpiceRequest<BidEventList> {
	
	private String url=BasketConstants.externalIp+"/Basket.js/WinBid/";
	
	public WinBidRequest(int id) {
		super(BidEventList.class);
		url+=id;
		
	}

	@Override
	public BidEventList loadDataFromNetwork() throws Exception 
	{	
		Log.d( "request", "loading from network" );
		return getRestTemplate().getForObject( url, BidEventList.class);
	}

}
