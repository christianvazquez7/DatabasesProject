package com.basket.restrequest;

import com.basket.general.BasketConstants;
import com.basket.general.Category;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class AdminCreateCatReq extends SpringAndroidSpiceRequest<Boolean> {
	
	private String un;
	private Category newCat;
	public AdminCreateCatReq( Category category) {
		super(Boolean.class);
		this.newCat=category;

		
	}

	@Override
	public Boolean loadDataFromNetwork() throws Exception {
		
		String url = BasketConstants.externalIp+"/Basket.js/AdminCreateCategory/";
		return getRestTemplate().postForObject(url, newCat,Boolean.class);
	}

}
