package com.example.basket;

import java.util.ArrayList;

public class BasketSession 
{
	private static User loggedUser;
	private static ArrayList<BuyEvent> currentSearchProductList;
	
	public static void beginSession(User user)
	{
		loggedUser=user;
		if(currentSearchProductList !=null)
		{
			currentSearchProductList.clear();
		}else
		currentSearchProductList = new ArrayList<BuyEvent>();
	}
	public static User getUser()
	{
		return loggedUser;
	}
	public static ArrayList<BuyEvent> getProductSearch()
	{
		return currentSearchProductList;
	}
	public static void setProducts(ArrayList<BuyEvent> list)
	{
		currentSearchProductList.clear();
		currentSearchProductList=list;
	}
	
	
	
}
