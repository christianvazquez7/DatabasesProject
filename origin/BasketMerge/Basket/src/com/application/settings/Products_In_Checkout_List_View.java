package com.application.settings;

import java.util.ArrayList;

import com.example.basket.R;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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
