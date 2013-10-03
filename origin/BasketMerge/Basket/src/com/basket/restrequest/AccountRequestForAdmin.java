package com.basket.restrequest;

import java.util.List;

import com.basket.general.UserList;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class AccountRequestForAdmin extends SpringAndroidSpiceRequest<UserList> {
	
	private String un;
	public AccountRequestForAdmin(String username) {
		super(UserList.class);
		un=username;
	}

	@Override
	public UserList loadDataFromNetwork() throws Exception {
		
		String url = "http://10.0.2.2:3412/Basket.js/AdminSearch/beta";
		url+=un;
		return getRestTemplate().getForObject(url, UserList.class);
	}

}
