package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.ProductBasket;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class QUpdateBasketRequest extends SpringAndroidSpiceRequest<Boolean> {
	
	private int basketId;
	private int amount;
	private int buyEventId;
	private ProductBasket productBasket;
	public QUpdateBasketRequest(ProductBasket b,int bi,int q,int be) 
	{
		super(Boolean.class);
		this.basketId=bi;
		amount=q;
		this.buyEventId=be;
		productBasket=b;
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception {
		String url = BasketConstants.externalIp+"/Basket.js/QUpdateBasket/";
		url+=basketId+"/"+amount+"/"+buyEventId;
		Log.d( "request", url );	
		getRestTemplate().put(url, productBasket);
		return true;
	}

}
