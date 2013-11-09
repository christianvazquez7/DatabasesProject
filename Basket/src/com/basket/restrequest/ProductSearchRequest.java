package com.basket.restrequest;

import android.util.Log;

import com.basket.containers.EventList;
import com.basket.general.BasketConstants;
import com.basket.general.BuyEvent;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class ProductSearchRequest extends SpringAndroidSpiceRequest<EventList> {
	
	private String query;
	private String cat;
	public ProductSearchRequest(String searchQuery) {
		super(EventList.class);
		query=searchQuery;
		cat="";
	}

	public ProductSearchRequest(String searchQuery,String stringExtra) {
		super(EventList.class);
		query=searchQuery;
		cat=stringExtra;
		
	}

	@Override
	public EventList loadDataFromNetwork() throws Exception {
		
		if(cat.equals("")){
		String url = BasketConstants.externalIp+"/Basket.js/search/";
		url+=query;
		Log.d( "request", "loading from network" );
		return getRestTemplate().getForObject( url, EventList.class);
		}
		else
		{
			String url = BasketConstants.externalIp+"/Basket.js/search/";
			url+=query+"/"+cat;
			Log.d( "request", "loading from network" );
			return getRestTemplate().getForObject( url, EventList.class);
		}
			
	}

}
