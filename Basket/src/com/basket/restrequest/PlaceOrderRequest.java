package com.basket.restrequest;

import com.basket.general.Basket;
import com.basket.general.BasketConstants;
import com.basket.general.Order;
import com.basket.general.User;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class PlaceOrderRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	private Order newO;
	private User theUser;
	private String bPos;
	private String url=BasketConstants.externalIp+"/Basket.js/PlaceOrder/temp/";
	public PlaceOrderRequest(Order o, User user,int p) 
	{
		super(Boolean.class);
		newO=o;
		theUser=user;
		bPos=Integer.toString(p);
		url+=bPos;
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception 
	{
		
		
		getRestTemplate().postForObject(url, newO, Boolean.class);
		return true;
	}

}
