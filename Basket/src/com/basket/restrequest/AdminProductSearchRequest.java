package com.basket.restrequest;

import android.util.Log;

import com.basket.containers.EventList;
import com.basket.general.BasketConstants;
import com.basket.general.BuyEvent;
import com.basket.general.ProductList;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class AdminProductSearchRequest extends SpringAndroidSpiceRequest<ProductList> {
	
	private String query;
	public AdminProductSearchRequest(String searchQuery) {
		super(ProductList.class);
		query=searchQuery;
	}

	@Override
	public ProductList loadDataFromNetwork() throws Exception {
		
		String url = BasketConstants.externalIp+"/Basket.js/AdminProductSearch/";
		url+=query;
		Log.d( "request", "loading from network" );
		return getRestTemplate().getForObject( url, ProductList.class);
	}

}
