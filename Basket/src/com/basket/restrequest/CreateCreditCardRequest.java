package com.basket.restrequest;

import com.basket.general.BasketConstants;
import com.basket.general.CreditCard;
import com.basket.general.User;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class CreateCreditCardRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	private CreditCard newCC;
	private User u;
	public CreateCreditCardRequest(CreditCard newCreditCard, User u) 
	{
		super(Boolean.class);
		newCC=newCreditCard;
		this.u = u;
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception 
	{
		getRestTemplate().postForObject(BasketConstants.externalIp+"/Basket.js/createCreditCard/", newCC, Boolean.class);
		return true;
	}

}
