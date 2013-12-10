package com.basket.restrequest;

import java.util.ArrayList;

import com.basket.general.BasketConstants;
import com.basket.general.CreditCard;
import com.basket.general.User;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class UpdateCreditCardRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	private CreditCard newCC, oldC;
	private User u;
	private ArrayList<CreditCard> ccList;
	public UpdateCreditCardRequest(CreditCard newCreditCard, User u,CreditCard oldCard) 
	{
		super(Boolean.class);
		newCC=newCreditCard;
		oldC = oldCard;
		this.u = u;
		ccList = new ArrayList<CreditCard>();
		ccList.add(newCC);
		ccList.add(oldCard);
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception 
	{
		getRestTemplate().postForObject(BasketConstants.externalIp+"/Basket.js/updateCreditCard/"+u.getEmail()+"/"+u.getUsername(), ccList, Boolean.class);
		return true;
	}

}
