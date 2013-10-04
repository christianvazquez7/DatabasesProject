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

	private UUID categoryId;
	private Category parent;
	private ArrayList<Products> currentDeals;
	private ArrayList<Products> popularProducts;
	private ArrayList<Category> children;
	private boolean mainCategory;
	
	public ArrayList<Category> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<Category> children) {
		this.children = children;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setParent(Category parent) {
		this.parent = parent;
	}
	public void setCurrentDeals(ArrayList<Products> currentDeals) {
		this.currentDeals = currentDeals;
	}
	public void setPopularProducts(ArrayList<Products> popularProducts) {
		this.popularProducts = popularProducts;
	}
	public void setMainCategory(boolean mainCategory) {
		this.mainCategory = mainCategory;
	}
	public Category(String name, Category parent){
		this.name = name;
		this.parent = parent;
		categoryId = UUID.randomUUID();
		if(parent == null) this.mainCategory = true;
	}
	public Category(){
		this.name = "Generic Category";
		this.parent = null;
		categoryId = UUID.randomUUID();
		if(parent == null) this.mainCategory = true;
	}

	public UUID getCategoryId() {
		return categoryId;
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

	@Override
	public String toString() {
		return name;
	}
	public Category getParent() {
		return parent;
	}

	public boolean isMainCategory(){
		return this.mainCategory;
	}
}
