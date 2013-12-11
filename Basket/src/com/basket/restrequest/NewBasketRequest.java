package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.ProductBasket;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class NewBasketRequest extends SpringAndroidSpiceRequest<ByteContainer> {
	
	
	private ProductBasket basket;
	private String username;
	public NewBasketRequest(ProductBasket b,String username) 
	{
		super(ByteContainer.class);
		basket=b;
		this.username=username;
	
	
	}

	@Override
	public ByteContainer loadDataFromNetwork() throws Exception 
	{	
		String url = BasketConstants.externalIp+"/Basket.js/NewBasket/"+username;	
		Log.d( "request", "loading from network" );	
		return getRestTemplate().postForObject(url,basket,ByteContainer.class);
	
	}

}
