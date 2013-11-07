package com.basket.restrequest;

import android.util.Log;

import com.basket.containers.CategoryList;
import com.basket.general.BasketConstants;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class GetCategoriesRequest extends SpringAndroidSpiceRequest<CategoryList> {
	
	private String url;
	
	public GetCategoriesRequest() 
	{
		super(CategoryList.class);	
		url=BasketConstants.externalIp+"/Basket.js/LoadCategory";
	}

	@Override
	public CategoryList loadDataFromNetwork() throws Exception 
	{	
		Log.d( "request", "loading from network" );
		return getRestTemplate().getForObject( url, CategoryList.class);
	}
}
