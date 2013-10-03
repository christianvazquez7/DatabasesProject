package com.basket.restrequest;

import com.basket.general.Order;
import com.basket.general.User;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class PlaceOrderRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	private Order newO;
	private User theUser;
	private String url="http://10.0.2.2:3412/Basket.js/PlaceOrder/temp";
	public PlaceOrderRequest(Order o, User user) 
	{
		super(Boolean.class);
		newO=o;
		theUser=user;
		
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception 
	{
		
		
		getRestTemplate().postForObject(url, newO, Boolean.class);
		return true;
	}

}
