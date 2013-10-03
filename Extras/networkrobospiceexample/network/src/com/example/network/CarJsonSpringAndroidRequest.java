package com.example.network;

import android.util.Log;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class CarJsonSpringAndroidRequest extends SpringAndroidSpiceRequest<ListCars> {

	public CarJsonSpringAndroidRequest() {
		super(ListCars.class);
	}

	@Override
	public ListCars loadDataFromNetwork() throws Exception {
		Log.d( "request", "loading from network" );
		return getRestTemplate().getForObject( "http://10.0.2.2:3412/ClassDemo3Srv/cars", ListCars.class);
	}

}
