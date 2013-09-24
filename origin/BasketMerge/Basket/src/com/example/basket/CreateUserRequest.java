package com.example.basket;

import android.util.Log;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class CreateUserRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	private User newU;
	public CreateUserRequest(User newUser) 
	{
		super(Boolean.class);
		newU=newUser;
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception 
	{
		
		
		getRestTemplate().postForObject("http://10.0.2.2:3412/Basket.js/create/1", newU, Boolean.class);
		return true;
	}

}
