package com.application.settings;

import java.util.ArrayList;

import com.example.basket.R;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class Products_List_View extends ListFragment{
	//private String[] list_items;
	public static int currentLoadedTab =0;
	private ArrayList<Products> products;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.orders_list, container, false);
		if(OrderContainer.getOrdersInUserSession().userOrders.containsKey(currentLoadedTab+1)){
			products = OrderContainer.getOrdersInUserSession().userOrders.get(currentLoadedTab+1).productsinorder;
			setListAdapter(new ProductInOrderAdapter(getActivity(), products));
			currentLoadedTab++;
		}
		
		
		return rootView;
	}

}
