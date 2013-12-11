package com.basket.restrequest;

import com.basket.general.BasketConstants;
import com.basket.general.CreditCard;
import com.basket.general.User;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class InsertCreditCardRequest extends SpringAndroidSpiceRequest<String> {
	
	private CreditCard newCreditCard;
	private User user;
	public InsertCreditCardRequest(CreditCard newCreditCard, User user) 
	{
		super(String.class);
		this.newCreditCard=newCreditCard;
		this.user = user;
	}

	@Override
	public String loadDataFromNetwork() throws Exception 
	{
		return getRestTemplate().postForObject(BasketConstants.externalIp+"/Basket.js/insertCreditCard/"+user.getEmail()+"/"+user.getUsername(), newCreditCard, String.class);
		
	}

}
