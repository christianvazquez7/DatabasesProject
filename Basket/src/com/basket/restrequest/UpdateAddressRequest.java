package com.basket.restrequest;

import java.util.ArrayList;

import com.basket.general.Adress;
import com.basket.general.BasketConstants;
import com.basket.general.CreditCard;
import com.basket.general.User;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class UpdateAddressRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	private Adress newAdress, oldAdress;
	private User u;
	private ArrayList<Adress> ccList;
	public UpdateAddressRequest(Adress theAddress, User u,Adress oldAddress) 
	{
		super(Boolean.class);
		newAdress=theAddress;
		oldAdress = oldAddress;
		this.u = u;
		ccList = new ArrayList<Adress>();
		ccList.add(newAdress);
		ccList.add(oldAddress);
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception 
	{
		getRestTemplate().postForObject(BasketConstants.externalIp+"/Basket.js/updateAddress/"+u.getEmail()+"/"+u.getUsername(), ccList, Boolean.class);
		return true;
	}

}
