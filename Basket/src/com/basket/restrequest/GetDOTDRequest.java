package com.basket.restrequest;

import android.util.Log;

import com.basket.containers.DealList;
import com.basket.containers.EventList;
import com.basket.general.BasketConstants;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class GetDOTDRequest extends SpringAndroidSpiceRequest<DealList> {
	
	private String url;
	
	public GetDOTDRequest() 
	{
		super(DealList.class);
		
			 url=BasketConstants.externalIp+"/Basket.js/GetDeals";

	}

	@Override
	public DealList loadDataFromNetwork() throws Exception 
	{	
		Log.d( "request", "loading from network" );
		return getRestTemplate().getForObject( url, DealList.class);
	}
}
