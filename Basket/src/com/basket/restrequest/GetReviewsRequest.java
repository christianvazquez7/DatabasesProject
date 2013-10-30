package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.ReviewList;
import com.basket.lists.BidList;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class GetReviewsRequest extends SpringAndroidSpiceRequest<ReviewList> {
	
	private String url;
	
	public GetReviewsRequest(int reviewEventId,int type) 
	{
		super(ReviewList.class);
		if (type ==0)
		{
			 url=BasketConstants.externalIp+"/Basket.js/GetBidReviews/";
		}else
			 url=BasketConstants.externalIp+"/Basket.js/GetBuyReviews/";

		url+=reviewEventId;
	}

	@Override
	public ReviewList loadDataFromNetwork() throws Exception 
	{	
		Log.d( "request", "loading from network" );
		return getRestTemplate().getForObject( url, ReviewList.class);
	}
}
