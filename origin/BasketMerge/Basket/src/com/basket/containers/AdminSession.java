package com.basket.containers;

import java.util.ArrayList;
import java.util.List;

import com.basket.general.Product;
import com.basket.general.User;

public class AdminSession
{
	 
	private static List<User> editUsers;
	private static List<Product> searchProduct;

	public static List<User> getEditUsers() 
	{
		if (editUsers==null)
		{
			editUsers=new ArrayList<User>();
		}
		return editUsers;
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

	
}
