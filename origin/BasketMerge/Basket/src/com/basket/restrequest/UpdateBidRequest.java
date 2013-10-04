package com.basket.restrequest;

import android.util.Log;

import com.basket.lists.BidList;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class UpdateBidRequest extends SpringAndroidSpiceRequest<BidList> {
	
	private String url="http://10.0.2.2:3412/Basket.js/UpdateBidSeller";
	
	public UpdateBidRequest() {
		super(BidList.class);
		
	}

	@Override
	public BidList loadDataFromNetwork() throws Exception 
	{	
		Log.d( "request", "loading from network" );
		return getRestTemplate().getForObject( url, BidList.class);
	}

}
