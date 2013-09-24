package com.example.basket;

import java.util.ArrayList;
import java.util.List;

public class AdminSession
{
	 
	private static List<User> editUsers;

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
		editUsers = editUser;
	}

}
