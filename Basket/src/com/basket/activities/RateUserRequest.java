package com.basket.activities;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.ProductBasket;
import com.basket.general.User;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class RateUserRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	private String rater;
	private String ratee;
	private float rating;
	private User b;
	public RateUserRequest(User a,String ra,String re, float ri) 
	{
		super(Boolean.class);
		rater=ra;
		ratee=re;
		rating=ri;
		b=a;
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception {
		String url = BasketConstants.externalIp+"/Basket.js/RateUser/";
		url+=rater+"/"+ratee+"/"+rating;
		Log.d( "request", "loading from network" );	
		getRestTemplate().put(url, b);
		return true;
	}

}
