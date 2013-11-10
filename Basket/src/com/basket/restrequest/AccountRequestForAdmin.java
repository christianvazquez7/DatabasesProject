package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.UserList;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class AccountRequestForAdmin extends SpringAndroidSpiceRequest<UserList> {
	
	private String un;
	public AccountRequestForAdmin(String username) {
		super(UserList.class);
		un=username;
		Log.d("Sent string for admin search", un);
		
	}

	@Override
	public UserList loadDataFromNetwork() throws Exception {
		
		String url = BasketConstants.externalIp+"/Basket.js/AdminSearch/";
		url+=un;
		return getRestTemplate().getForObject(url, UserList.class);
	}

}
