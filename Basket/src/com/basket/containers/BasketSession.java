package com.basket.containers;

import java.util.ArrayList;

import com.basket.general.Bid;
import com.basket.general.Event;
import com.basket.general.Review;
import com.basket.general.User;

public class BasketSession 
{
	private static User loggedUser;
	private static ArrayList<Event> currentSearchBuyList;
	private static ArrayList<Bid> currentBids;
	private static ArrayList<Review> currentReviews;
	
	
	
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
		if(list!=null && list.size()>0)
			currentSearchBuyList.clear();
		currentSearchBuyList=list;
	}
	public static ArrayList<Bid> getBidSearch()
	{
		return currentBids;
	}
	public static void setBids(ArrayList<Bid> list)
	{
		if (currentBids!=null)
		currentBids.clear();
		currentBids=list;
	}
	public static ArrayList<Review> getReviewSearch()
	{
		return currentReviews;
	}
	public static void setReviews(ArrayList<Review> list)
	{
		if (currentReviews!=null)
		currentReviews.clear();
		currentReviews=list;
	}
	
	
	
}
