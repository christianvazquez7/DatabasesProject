package com.basket.restrequest;

import com.basket.general.Adress;
import com.basket.general.BasketConstants;
import com.basket.general.User;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class InsertShipAddRequest extends SpringAndroidSpiceRequest<String> {
	
	private Adress newShippAdd;
	private User user;
	public InsertShipAddRequest(Adress newShipAdd, User user) 
	{
		super(String.class);
		this.newShippAdd=newShipAdd;
		this.user = user;
	}

	@Override
	public String loadDataFromNetwork() throws Exception 
	{
		
		return getRestTemplate().postForObject(BasketConstants.externalIp+"/Basket.js/insertShippingAddress/"+user.getEmail()+"/"+user.getUsername(), newShippAdd, String.class);
	}

}
