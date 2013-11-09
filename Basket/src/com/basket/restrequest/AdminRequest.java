package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class AdminRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	private String em, pas;
	public AdminRequest(String email, String password) {
		super(Boolean.class);
		em=email;
		pas=password;
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception {
		
		String url = BasketConstants.externalIp+"/Basket.js/Admin/";
		url+=em+"/"+pas;
		
		Log.d( "request", "loading from network" );
		return getRestTemplate().getForObject( url, Boolean.class);
	}

}
