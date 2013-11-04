package com.basket.restrequest;

import android.util.Log;

import com.basket.containers.EventList;
import com.basket.general.BasketConstants;
import com.basket.general.BuyEvent;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class ProductSearchRequest extends SpringAndroidSpiceRequest<EventList> {
	
	private String query;
	public ProductSearchRequest(String searchQuery) {
		super(EventList.class);
		query=searchQuery;
	}

	@Override
	public EventList loadDataFromNetwork() throws Exception {
		
		String url = BasketConstants.externalIp+"/Basket.js/search/";
		url+=query;
		Log.d( "request", "loading from network" );
		return getRestTemplate().getForObject( url, EventList.class);
	}

}
