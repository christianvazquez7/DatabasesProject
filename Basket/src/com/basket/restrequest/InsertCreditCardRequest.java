package com.basket.restrequest;

import com.basket.general.BasketConstants;
import com.basket.general.CreditCard;
import com.basket.general.User;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class InsertCreditCardRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	private CreditCard newCreditCard;
	private User user;
	public InsertCreditCardRequest(CreditCard newCreditCard, User user) 
	{
		super(Boolean.class);
		this.newCreditCard=newCreditCard;
		this.user = user;
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception 
	{
		getRestTemplate().postForObject(BasketConstants.externalIp+"/Basket.js/insertCreditCard/"+user.getEmail()+"/"+user.getUsername(), newCreditCard, Boolean.class);
		return true;
	}

}
