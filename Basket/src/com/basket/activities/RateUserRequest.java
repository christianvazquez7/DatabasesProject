package com.basket.activities;

import android.util.Log;

import com.basket.containers.FloatContainer;
import com.basket.general.BasketConstants;
import com.basket.general.User;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class RateUserRequest extends SpringAndroidSpiceRequest<FloatContainer> {
	
	private String rater;
	private String ratee;
	private float rating;
	private User b;
	public RateUserRequest(User a,String ra,String re, float ri) 
	{
		super(FloatContainer.class);
		rater=ra;
		ratee=re;
		rating=ri;
		b=a;
	}

	@Override
	public FloatContainer loadDataFromNetwork() throws Exception {
		String url = BasketConstants.externalIp+"/Basket.js/RateUser/";
		url+=rater+"/"+ratee+"/"+rating;
		Log.d( "request", "loading from network" );	
		return getRestTemplate().postForObject(url, b,FloatContainer.class);
	
	}

}
