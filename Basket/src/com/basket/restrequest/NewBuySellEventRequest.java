package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.BuyEvent;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class NewBuySellEventRequest extends SpringAndroidSpiceRequest<ByteContainer> {
	
	
	private BuyEvent buySellProduct;
	private int u,quan;
	private String cat;
	private String m;
	public NewBuySellEventRequest(BuyEvent b,int uId,int q,String c,String im) 
	{
		super(ByteContainer.class);
		buySellProduct=b;
		u=uId;
		quan=q;
		cat=c;
		m=im;
	}

	@Override
	public ByteContainer loadDataFromNetwork() throws Exception 
	{
		
		String url = BasketConstants.externalIp+"/Basket.js/NewBuySell";
		url+="/"+u+"/"+quan+"/"+cat;
		Log.d( "request", "loading from network" );	
		return getRestTemplate().postForObject(url,buySellProduct,ByteContainer.class);
		
	}

}
