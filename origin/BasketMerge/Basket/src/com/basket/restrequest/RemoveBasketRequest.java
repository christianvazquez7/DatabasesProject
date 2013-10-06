package com.basket.restrequest;

import android.util.Log;

import com.basket.general.ProductBasket;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class RemoveBasketRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	
	private ProductBasket basket;
	public RemoveBasketRequest(ProductBasket b) 
	{
		super(Boolean.class);
		basket=b;
	
	
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception 
	{
		
		String url = "http://10.0.2.2:3412/Basket.js/RemoveBasket";
		
		Log.d( "request", "loading from network" );	
		getRestTemplate().postForObject(url,basket,Boolean.class);
		return true;
	}

}
