package com.basket.containers;

import java.util.ArrayList;

import com.basket.general.BuyEvent;
import com.basket.general.Event;
import com.basket.general.User;

public class BasketSession 
{
	private static User loggedUser;
	private static ArrayList<Event> currentSearchBuyList;
	
	
	
	public static void beginSession(User user)
	{
		loggedUser=user;
		if(currentSearchBuyList !=null)
		{
			currentSearchBuyList.clear();
		}else
		currentSearchBuyList = new ArrayList<Event>();
	}
	public static User getUser()
	{
		return loggedUser;
	}
	public static ArrayList<Event> getProductSearch()
	{
		return currentSearchBuyList;
	}
	public static void setProducts(ArrayList<Event> list)
	{
		currentSearchBuyList.clear();
		currentSearchBuyList=list;
	}
	
	
	
}
