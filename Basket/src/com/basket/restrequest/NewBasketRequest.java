package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.ProductBasket;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class NewBasketRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	
	private ProductBasket basket;
	private String username;
	public NewBasketRequest(ProductBasket b,String username) 
	{
		super(Boolean.class);
		basket=b;
		this.username=username;
	
	
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception 
	{	
		String url = BasketConstants.externalIp+"/Basket.js/NewBasket/"+username;	
		Log.d( "request", "loading from network" );	
		getRestTemplate().postForObject(url,basket,Boolean.class);
		return true;
	}

}
