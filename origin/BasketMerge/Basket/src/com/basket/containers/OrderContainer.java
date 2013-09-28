package com.basket.containers;

import java.util.HashMap;

import com.basket.general.Order;
import com.basket.general.Products;

public class OrderContainer {
	private static OrderContainer sessionUserOrders;
	public static OrderContainer getOrdersInUserSession(){
		if (sessionUserOrders==null){
			sessionUserOrders = new OrderContainer();
		}
		return sessionUserOrders;
	}
	public HashMap<Integer, Order> userOrders;
	private OrderContainer(){
		userOrders = new HashMap<Integer, Order>();
		userOrders.put(1, new Order());
		userOrders.put(2, new Order());
		userOrders.put(3, new Order());
		for(int i=1;i<=userOrders.size();i++){
			for(int j=1;j<=i;j++){
				userOrders.get(i).productsinorder.add(new Products());
			}
		}
	}
}
