package com.basket.restrequest;

import com.basket.general.BasketConstants;
import com.basket.general.BooleanContainer;
import com.basket.general.Order;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class PlaceOrderRequest extends SpringAndroidSpiceRequest<BooleanContainer> {
	
	private Order newO;
	
	private String url=BasketConstants.externalIp+"/Basket.js/PlaceOrder/";
	public PlaceOrderRequest(Order o, int userId,int cardId,int billingId,int shippingId, String df,double total,int basket) 
	{
		super(BooleanContainer.class);
		newO=o;
		url+=userId+"/"+cardId+"/"+basket+"/"+shippingId+"/"+df+"/"+total;
	}

	@Override
	public BooleanContainer loadDataFromNetwork() throws Exception 
	{
		
		
		return getRestTemplate().postForObject(url, newO, BooleanContainer.class);
		
	}

}
