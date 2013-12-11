package com.basket.restrequest;

import com.basket.general.BasketConstants;
import com.basket.general.Category;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class GetCatParent extends SpringAndroidSpiceRequest<String> {
	
	private Category qCat;
	public GetCatParent( Category queryCategory) {
		super(String.class);
		this.qCat=queryCategory;		
	}

	@Override
	public String loadDataFromNetwork() throws Exception {
		String url = BasketConstants.externalIp+"/Basket.js/GetCatParent/";
		return getRestTemplate().postForObject(url, qCat,String.class);
	}
}
