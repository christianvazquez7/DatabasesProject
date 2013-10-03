package com.basket.general;


import java.util.ArrayList;
import java.util.LinkedList;

import java.util.UUID;

/**
 * @author arios_000
 *
 */
public class Category {
	//Variable Declaration.
	private String name; 

	private UUID mCategoryId;
	private Category parent;
	private ArrayList<Products> currentDeals;
	private ArrayList<Products> popularProducts;
	private boolean mainCategory;

	public Category(String name, Category parent){
		this.name = name;
		this.parent = parent;
		mCategoryId = UUID.randomUUID();
		if(parent == null) this.mainCategory = true;
	}
	public Category(){
		this.name = "Generic Category";
		this.parent = null;
		mCategoryId = UUID.randomUUID();
		if(parent == null) this.mainCategory = true;
	}

	public UUID getCategoryId() {
		return mCategoryId;
	}

	public ArrayList<Products> getCurrentDeals() {
		return currentDeals;
	}

	public void addCurrentDeals(Products currentDeal) {
		this.currentDeals.add(currentDeal);
	}

	public ArrayList<Products> getPopularProducts() {
		return popularProducts;
	}

	public void addPopularProductsAt(int x, Products popularProduct) {
		this.popularProducts.add(x, popularProduct);
	}

	public String getName() {
		return name;
	}

	public Category getParent() {
		return parent;
	}

	public boolean isMainCategory(){
		return this.mainCategory;
	}
}
