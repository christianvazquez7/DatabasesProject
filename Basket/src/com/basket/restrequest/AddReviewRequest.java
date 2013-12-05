package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.Bid;
import com.basket.general.BidEvent;
import com.basket.general.Review;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;


public class AddReviewRequest extends SpringAndroidSpiceRequest<Boolean> 
{
	
	
	private Review newReview;
	private int id;
	private boolean isBid;
	private String username;
	private int pid;
	
	public AddReviewRequest(Review newReview,String username, int eventId, boolean bid, int pid) 
	{
		super(Boolean.class);
		this.newReview=newReview;
		this.id=eventId;
		isBid=bid;
		this.username=username;
		this.pid=pid;
	
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception 
	{
		
		String url = BasketConstants.externalIp+"/Basket.js/addReview/";
		url+=id+"/"+username+"/"+isBid+"/"+pid;
		Log.d( "request", "loading from network" );	
		getRestTemplate().put(url, newReview);
		return true;
	}

}
