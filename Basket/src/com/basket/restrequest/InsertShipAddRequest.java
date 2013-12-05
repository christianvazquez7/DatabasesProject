package com.basket.restrequest;

import com.basket.general.Adress;
import com.basket.general.BasketConstants;
import com.basket.general.User;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class InsertShipAddRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	private Adress newShippAdd;
	private User user;
	public InsertShipAddRequest(Adress newShipAdd, User user) 
	{
		super(Boolean.class);
		this.newShippAdd=newShipAdd;
		this.user = user;
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception 
	{
		getRestTemplate().postForObject(BasketConstants.externalIp+"/Basket.js/insertShippingAddress/"+user.getEmail()+"/"+user.getUsername(), newShippAdd, Boolean.class);
		return true;
	}

}
