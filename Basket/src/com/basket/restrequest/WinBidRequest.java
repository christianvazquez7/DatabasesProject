package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.lists.BidList;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class WinBidRequest extends SpringAndroidSpiceRequest<BidList> {
	
	private String url=BasketConstants.externalIp+"/Basket.js/WinBid/";
	
	public WinBidRequest(String id) {
		super(BidList.class);
		url+=id;
		
	}

	@Override
	public BidList loadDataFromNetwork() throws Exception 
	{	
		Log.d( "request", "loading from network" );
		return getRestTemplate().getForObject( url, BidList.class);
	}

}
