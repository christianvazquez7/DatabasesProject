package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.ReviewList;
import com.basket.general.UserRating;
import com.basket.general.UserRatingList;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class GetURatingRequest extends SpringAndroidSpiceRequest<UserRatingList> {
	
	private String url;
	
	public GetURatingRequest(String userId) 
	{
		super(UserRatingList.class);
		
			 url=BasketConstants.externalIp+"/Basket.js/GetRatings/";

		url+=userId;
	}

	@Override
	public UserRatingList loadDataFromNetwork() throws Exception 
	{	
		Log.d( "request", "loading from network" );
		return getRestTemplate().getForObject( url, UserRatingList.class);
	}
}
