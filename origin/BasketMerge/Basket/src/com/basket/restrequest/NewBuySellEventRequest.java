package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.BidEvent;
import com.basket.general.BuyEvent;
import com.basket.general.ProductBasket;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class NewBuySellEventRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	
	private BuyEvent buySellProduct;
	public NewBuySellEventRequest(BuyEvent b) 
	{
		super(Boolean.class);
		buySellProduct=b;
	
	
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception 
	{
		
		String url = BasketConstants.externalIp+"/Basket.js/NewBuySell";
		
		Log.d( "request", "loading from network" );	
		getRestTemplate().postForObject(url,buySellProduct,Boolean.class);
		return true;
	}

}
