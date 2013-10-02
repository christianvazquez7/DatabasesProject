package com.basket.general;

import java.util.ArrayList;

public class Basket {
	private String name;
	public ArrayList<Event> productsinbasket;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Basket(String name){
		this.name = name;
		this.productsinbasket = new ArrayList<Event>();
	}
}
