package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.Bid;
import com.basket.lists.BidList;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class CurrentWinningRequest extends SpringAndroidSpiceRequest<Bid> {
	
	private String url=BasketConstants.externalIp+"/Basket.js/CurrentWinning";
	
	public CurrentWinningRequest(int id) 
	{
		super(Bid.class);
		url+="/"+id;
	}

	@Override
	public Bid loadDataFromNetwork() throws Exception 
	{	
		Log.d( "request", "loading from network" );
		return getRestTemplate().getForObject( url, Bid.class);
	}

}
