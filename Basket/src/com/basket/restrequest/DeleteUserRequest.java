package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.User;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class DeleteUserRequest extends SpringAndroidSpiceRequest<Boolean> {

	private User u;

	public DeleteUserRequest(User u) 
	{
		super(Boolean.class);
		this.u = u;
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception {

		String url = BasketConstants.externalIp+"/Basket.js/UserDelete/";
		
		Log.d( "request", "loading from network" );
		
		return getRestTemplate().postForObject( url,u, Boolean.class);
	}

}
