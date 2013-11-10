package com.basket.general;


import java.util.ArrayList;
import java.util.LinkedList;

import java.util.UUID;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author arios_000
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class Category {
	@Override
	public String toString() {
		return "Category [name=" + name + ", child=" + child + ", parent="
				+ parent + "]";
	}
	//Variable Declaration.
	private String name; 
	private ArrayList<Category> child;		
	private Category parent;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Category> getChild() {
		return child;
	private ArrayList<Product> currentDeals;
	private ArrayList<Product> popularProducts;
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

	public ArrayList<Product> getCurrentDeals() {
		return currentDeals;
	}

	public void addCurrentDeals(Product currentDeal) {
		this.currentDeals.add(currentDeal);
	}

	public ArrayList<Product> getPopularProducts() {
		return popularProducts;
	}

	public void addPopularProductsAt(int x, Product popularProduct) {
		this.popularProducts.add(x, popularProduct);
	}
	public void setChild(ArrayList<Category> child) {
		this.child = child;
	}
	public Category getParent() {
		return parent;
	}
	public void setParent(Category parent) {
		this.parent = parent;
	}
	
}
	
	
	


	
