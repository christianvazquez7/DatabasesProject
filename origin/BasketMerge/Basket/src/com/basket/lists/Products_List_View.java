package com.basket.lists;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basket.adapters.ProductInOrderAdapter;
import com.basket.containers.BasketSession;
import com.basket.general.BuyEvent;
import com.basket.general.Event;
import com.example.basket.R;

public class Products_List_View extends ListFragment{
	//private String[] list_items;
	public static int ordernum =0;

	private ArrayList<BuyEvent> products;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.orders_list, container, false);
		products = BasketSession.getUser().getUserOrders().get(ordernum).getBuyEvents();
		setListAdapter(new ProductInOrderAdapter(getActivity(), products));




		return rootView;
	}

}
