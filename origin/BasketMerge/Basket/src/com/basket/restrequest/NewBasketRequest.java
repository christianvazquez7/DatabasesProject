package com.basket.restrequest;

import android.util.Log;

import com.basket.general.ProductBasket;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class NewBasketRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	
	private ProductBasket basket;
	public NewBasketRequest(ProductBasket b) 
	{
		super(Boolean.class);
		basket=b;
	
	
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception 
	{
		
		String url = "http://10.0.2.2:3412/Basket.js/NewBasket";
		
		Log.d( "request", "loading from network" );	
		getRestTemplate().postForObject(url,basket,Boolean.class);
		return true;
	}

}
