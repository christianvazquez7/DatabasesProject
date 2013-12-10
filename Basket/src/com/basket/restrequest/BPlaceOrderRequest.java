package com.basket.restrequest;

import com.basket.general.Basket;
import com.basket.general.BasketConstants;
import com.basket.general.Order;
import com.basket.general.User;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class BPlaceOrderRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	private Order newO;
	
	private String url=BasketConstants.externalIp+"/Basket.js/BPlaceOrder/";
	public BPlaceOrderRequest(Order o, int userId,int cardId,int billingId,int shippingId, String df,double total,int basket) 
	{
		super(Boolean.class);
		newO=o;
		url+=userId+"/"+cardId+"/"+basket+"/"+shippingId+"/"+df+"/"+total;
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception 
	{
		
		
		getRestTemplate().postForObject(url, newO, Boolean.class);
		return true;
	}

}
