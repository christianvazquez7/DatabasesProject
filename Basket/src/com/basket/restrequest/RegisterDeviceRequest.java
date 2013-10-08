package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.ProductBasket;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class RegisterDeviceRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	private String regid;
	private ProductBasket basket;
	public RegisterDeviceRequest(String regid) 
	{
		super(Boolean.class);
		this.regid = regid;
	
	
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception {
		
		String url = BasketConstants.externalIp+"/Basket.js/RegisterDevice/";
		url+=regid;
		Log.d( "request", "loading from network" );	
		getRestTemplate().put(url, null);
		return true;
	}

}
