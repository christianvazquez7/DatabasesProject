package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.lists.BidList;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class UpdateBidRequest extends SpringAndroidSpiceRequest<BidList> {
	
	private String url=BasketConstants.externalIp+"/Basket.js/UpdateBidSeller";
	
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
