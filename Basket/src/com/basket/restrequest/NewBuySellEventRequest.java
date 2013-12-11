package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.BidEvent;
import com.basket.general.BuyEvent;
import com.basket.general.ProductBasket;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class NewBuySellEventRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	
	private BuyEvent buySellProduct;
	private int u,quan;
	private String cat;
	public NewBuySellEventRequest(BuyEvent b,int uId,int q,String c) 
	{
		super(Boolean.class);
		buySellProduct=b;
		u=uId;
		quan=q;
		cat=c;
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception 
	{
		
		String url = BasketConstants.externalIp+"/Basket.js/NewBuySell";
		url+="/"+u+"/"+quan+"/"+cat;
		Log.d( "request", "loading from network" );	
		getRestTemplate().postForObject(url,buySellProduct,Boolean.class);
		return true;
	}

}
