package com.basket.containers;

import java.util.ArrayList;

import com.basket.general.Products;

public class BasketContainer {
	private ArrayList<Products> mBasketItems;
	private String mBasketName;
	public BasketContainer(String name){
		this.mBasketName = name;
		mBasketItems = new ArrayList<Products>();
	}
	public String getBasketName() {
		return mBasketName;
	}
	public void setBasketName(String basketName) {
		mBasketName = basketName;
	}
	public ArrayList<Products> getBasketItems() {
		return mBasketItems;
	}
	public void setBasketItems(ArrayList<Products> basketItems) {
		mBasketItems = basketItems;
	}
	
	
}