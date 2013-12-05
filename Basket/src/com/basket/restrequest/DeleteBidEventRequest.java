package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.BidEvent;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class DeleteBidEventRequest extends SpringAndroidSpiceRequest<Boolean> {
	private int id;
	private int accepted;
	private BidEvent theEvent;
	private String w;
	public DeleteBidEventRequest(int id,int accepted,String winner) 
	{
		super(Boolean.class);
		this.id=id;
		this.accepted=accepted;
		theEvent=new BidEvent();
		w=winner;
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception 
	{
		String url = BasketConstants.externalIp+"/Basket.js/remBid/";
		url+=Integer.toString(id)+"/"+accepted+"/"+w;
		Log.d( "request", "loading from network" );	
		getRestTemplate().postForEntity(url,theEvent, Boolean.class);
		return true;
	}
}
