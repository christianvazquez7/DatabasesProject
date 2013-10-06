package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BidEvent;
import com.basket.general.ProductBasket;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class NewBidSellEventRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	
	private BidEvent bidSellProduct;
	public NewBidSellEventRequest(BidEvent b) 
	{
		super(Boolean.class);
		bidSellProduct=b;
	
	
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception 
	{
		
		String url = "http://10.0.2.2:3412/Basket.js/NewBidSell";
		
		Log.d( "request", "loading from network" );	
		getRestTemplate().postForObject(url,bidSellProduct,Boolean.class);
		return true;
	}

}
