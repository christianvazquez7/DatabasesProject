package com.example.basket;

import android.util.Log;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class DeleteUserRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	private int loc;
	
	public DeleteUserRequest(int id) 
	{
		super(Boolean.class);
		loc = id;
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception {
		
		String url = "http://10.0.2.2:3412/Basket.js/UserDelete/";
		url+=loc;
		
		Log.d( "request", "loading from network" );
		getRestTemplate().delete(url);
		return true;
	}

}
