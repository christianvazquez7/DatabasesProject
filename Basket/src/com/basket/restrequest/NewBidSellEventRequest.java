package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.BidEvent;
import com.basket.general.ProductBasket;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class NewBidSellEventRequest extends SpringAndroidSpiceRequest<Boolean> {


	private BidEvent bidSellProduct;
	private int u;
	private String cat;
	public NewBidSellEventRequest(BidEvent b,int uId,String c) 
	{
		super(Boolean.class);
		bidSellProduct=b;
		u=uId;
		cat=c;


	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception 
	{

		String url = BasketConstants.externalIp+"/Basket.js/NewBidSell";

		url+="/"+u+"/"+cat;
		Log.d( "request", "loading from network" );	
		getRestTemplate().postForObject(url,bidSellProduct,Boolean.class);
		return true;
	}

}
