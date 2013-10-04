package com.basket.restrequest;

import android.util.Log;

import com.basket.general.ProductBasket;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class UpdateBasketRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	private int position;
	private ProductBasket basket;
	public UpdateBasketRequest(int basketPos,ProductBasket b) 
	{
		super(Boolean.class);
		position=basketPos;
		basket=b;
	
	
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception {
		
		String url = "http://10.0.2.2:3412/Basket.js/UpdateBasket/";
		url+=position;
		Log.d( "request", "loading from network" );	
		getRestTemplate().put(url, basket);
		return true;
	}

}
