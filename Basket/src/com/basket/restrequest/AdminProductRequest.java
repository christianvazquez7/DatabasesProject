package com.basket.restrequest;
import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.ProductList;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class AdminProductRequest extends SpringAndroidSpiceRequest<ProductList> 
{
	
	private String query;
	public AdminProductRequest(String searchQuery) 
	{
		super(ProductList.class);
		query=searchQuery;
	}

	@Override
	public ProductList loadDataFromNetwork() throws Exception 
	{
		
		String url = BasketConstants.externalIp+"/Basket.js/Product/foo";
		//url+=query;
		Log.d( "request", "loading from network" );
		return getRestTemplate().getForObject(url, ProductList.class);
	}

}
