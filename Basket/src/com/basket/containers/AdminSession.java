package com.basket.containers;

import java.util.ArrayList;
import java.util.List;

import com.basket.general.Event;
import com.basket.general.Product;
import com.basket.general.User;

public class AdminSession
{
	 
	private static List<User> editUsers;
	private static List<Product> searchProduct;
	private static List<Event> eventsList;
	
	public static List<User> getEditUsers() 
	{
		if (editUsers==null)
		{
			editUsers=new ArrayList<User>();
		}
		return editUsers;
	}
	public static void startSession(){
		editUsers = new ArrayList<User>();
		searchProduct = new ArrayList<Product>();
		eventsList = new ArrayList<Event>();
	}
	public static void setEditUsers(List<User> editUser) 
	{
		if (editUsers!=null) editUsers.clear();
		editUsers = editUser;
	}
	public static void setProducts(List<Product> productList)
	{
		if (searchProduct !=null) searchProduct.clear();
		searchProduct=productList;
		
	}
	public static List<Product> getProducts()
	{
		return searchProduct;
		
	}
	public static List<Event> getEventsList() {
		return eventsList;
	}
	public static void setEventsList(List<Event> eventsList) {
		AdminSession.eventsList = eventsList;
	}

	
}


