package com.basket.myshop;

import java.util.ArrayList;

import com.application.settings.Products;

public class MyShop {
	public ArrayList<Products> productsinbid, productsinsell;
	private static MyShop sessionUserStore;
	private MyShop(){
		productsinbid = new ArrayList<Products>();
		productsinsell = new ArrayList<Products>();
		productsinbid.add(new Products());
		productsinbid.add(new Products());
		productsinbid.add(new Products());
		productsinbid.add(new Products());
		productsinbid.add(new Products());
		productsinsell.add(new Products());
		productsinsell.add(new Products());
		productsinsell.add(new Products());
		//Load from server etc...
	}
	public static MyShop getShopInUserSession(){
		if (sessionUserStore==null){
			sessionUserStore = new MyShop();
		}
		
		return sessionUserStore;
	}
}
