package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.User;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class SendForgotFieldEmailRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	private String em;
	public SendForgotFieldEmailRequest(String email) {
		super(Boolean.class);
		em=email;
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception {
		
		String url = BasketConstants.externalIp+"/Basket.js/ForgetCreds/";
		url+=em;
		
		Log.d( "request", "loading from network" );
		return getRestTemplate().getForObject( url, Boolean.class);
	}

}
