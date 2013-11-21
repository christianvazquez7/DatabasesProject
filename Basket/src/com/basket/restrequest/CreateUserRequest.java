package com.basket.restrequest;

import com.basket.general.BasketConstants;
import com.basket.general.User;
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
		getRestTemplate().postForObject(BasketConstants.externalIp+"/Basket.js/create/1", newU, Boolean.class);
		return true;
	}

}
