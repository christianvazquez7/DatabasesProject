package com.example.basket;

import android.util.Log;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class UserRequest extends SpringAndroidSpiceRequest<User> {
	
	private String em, pas;
	public UserRequest(String email, String password) {
		super(User.class);
		em=email;
		pas=password;
	}

	@Override
	public User loadDataFromNetwork() throws Exception {
		
		String url = "http://10.0.2.2:3412/Basket.js/User/";
		url+=em+"/"+pas;
		
		Log.d( "request", "loading from network" );
		return getRestTemplate().getForObject( url, User.class);
	}

}
