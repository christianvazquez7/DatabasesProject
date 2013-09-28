package com.basket.lists;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basket.adapters.ProductInOrderAdapter;
import com.basket.containers.OrderContainer;
import com.basket.general.Products;
import com.example.basket.R;

public class Products_In_Checkout_List_View extends ListFragment{
	//private String[] list_items;
	private ArrayList<Products> products;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.orders_list, container, false);
			products = OrderContainer.getOrdersInUserSession().userOrders.get(1).productsinorder;
			setListAdapter(new ProductInOrderAdapter(getActivity(), products));
		
		
		
		return rootView;
	}

}
