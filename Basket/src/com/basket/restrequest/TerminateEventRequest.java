package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.BidEvent;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class TerminateEventRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	private int id;
	private BidEvent e;
	public TerminateEventRequest(BidEvent a, int eventId) {
		super(Boolean.class);
		id=eventId;
	
	
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception {
		
		String url = BasketConstants.externalIp+"/Basket.js/TerminateEvent/";
		url+=id;
		Log.d( "request", "loading from network" );
		getRestTemplate().postForObject(url, e,Boolean.class);
		return true;
	}

}
