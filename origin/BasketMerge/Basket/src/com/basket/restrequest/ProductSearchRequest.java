package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BuyEvent;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class ProductSearchRequest extends SpringAndroidSpiceRequest<BuyEvent> {
	
	private String query;
	public ProductSearchRequest(String searchQuery) {
		super(BuyEvent.class);
		query=searchQuery;
	}

	@Override
	public BuyEvent loadDataFromNetwork() throws Exception {
		
		String url = "http://10.0.2.2:3412/Basket.js/search/foo";
		//url+=query;
		Log.d( "request", "loading from network" );
		return getRestTemplate().getForObject( url, BuyEvent.class);
	}

}
