package com.basket.containers;

import java.util.ArrayList;

import com.basket.general.Bid;
import com.basket.general.Category;
import com.basket.general.Event;
import com.basket.general.Review;
import com.basket.general.User;
import com.basket.general.UserRating;

public class BasketSession 
{
	private static User loggedUser;
	private static ArrayList<Event> currentSearchBuyList;
	private static ArrayList<Bid> currentBids;
	private static ArrayList<Review> currentReviews;
	private static ArrayList<UserRating>  ratings;
	private static ArrayList<Category> categories;
	private static ArrayList<Deal>deals;
	
	
	
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
	public static ArrayList<UserRating> getUserRatings() {
		
		return ratings;
	}
	public static void setRatings(ArrayList<UserRating> r) {
		
		if (ratings!=null)
			ratings.clear();
			ratings=r;
	}
	public static void setCategories(ArrayList<Category> cat) {
	
		if(categories!=null)
			categories.clear();
		categories=cat;
	}
	public static ArrayList<Category> getCategory() {
		// TODO Auto-generated method stub
		return categories;
	}
	public static void setDeals(ArrayList<Deal> events) {
		if(deals!=null)
			deals.clear();
		deals=events;
		
	}
	public static ArrayList<Deal> getDeals() {
		// TODO Auto-generated method stub
		return deals;
	}
	
	
	
}
