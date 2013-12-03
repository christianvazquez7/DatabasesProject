package com.basket.restrequest;

import com.basket.general.BasketConstants;
import com.basket.general.User;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class UpdateUserRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	private User newU;
	public UpdateUserRequest(User uToBeUpdated) {
		super(Boolean.class);
		newU=uToBeUpdated;
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception {
		String url = BasketConstants.externalIp+"/Basket.js/UpdateUser/";
		getRestTemplate().put(url, newU);
		return true;
	}
}
