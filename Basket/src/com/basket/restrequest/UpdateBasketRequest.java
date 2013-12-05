package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.ProductBasket;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class UpdateBasketRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	private int bid;
	private int eid;
	private ProductBasket basket;
	public UpdateBasketRequest(int basketId,int eventId,ProductBasket basket) 
	{
		super(Boolean.class);
		bid=basketId;
		eid = eventId;
		this.basket=basket;
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception {
		String url = BasketConstants.externalIp+"/Basket.js/UpdateBasket/";
		url+=bid+"/"+eid;
		Log.d( "request", "loading from network" );	
		getRestTemplate().put(url, basket);
		return true;
	}

}
