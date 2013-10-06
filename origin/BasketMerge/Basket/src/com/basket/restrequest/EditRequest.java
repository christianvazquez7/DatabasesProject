package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.User;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class EditRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	private String usr,pass,em;
	private int loc;
	private User newU;
	public EditRequest(User newUser,int id,String password,String email,String username) {
		super(Boolean.class);
		usr=username;
		pass=password;
		em=email;
		newU=newUser;
		loc = id;
	
	
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception {
		
		String url = BasketConstants.externalIp+"/Basket.js/UserEdit/";
		url+=loc+"/"+usr+"/"+pass+"/"+em;
		
		Log.d( "request", "loading from network" );
		
		getRestTemplate().put(url, newU);
		return true;
	}

}
