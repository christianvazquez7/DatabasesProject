package com.basket.activities;

import android.util.Log;

import com.basket.containers.EventList;
import com.basket.general.BasketConstants;
import com.basket.general.User;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class GetRecommendationsRequest extends SpringAndroidSpiceRequest<EventList> {
	private String url;

	public GetRecommendationsRequest(User theUser) {
		super(EventList.class);

		url=BasketConstants.externalIp+"/Basket.js/GetRecommendations/"+theUser.getUsername();
		
	}
	@Override
	public EventList loadDataFromNetwork() throws Exception 
	{	
		Log.d( "request", "loading from network" );
		return getRestTemplate().getForObject( url, EventList.class);
	}
}
