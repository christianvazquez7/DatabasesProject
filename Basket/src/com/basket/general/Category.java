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
	
	
	


	