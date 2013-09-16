package com.example.basket;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author arios_000
 *
 */
public class Category {
	//Variable Declaration.
	private String name; 
	private Category parent;
	private LinkedList<Products> currentDeals;
	private ArrayList<Products> popularProducts;
	private boolean mainCategory;
	
	
	public Category(String name, Category parent){
		this.name = name;
		this.parent = parent;
		if(parent == null) this.mainCategory = true;
	}

	public LinkedList<Products> getCurrentDeals() {
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
