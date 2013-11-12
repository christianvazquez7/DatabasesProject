package com.basket.lists;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basket.adapters.ProductInCheckoutAdapter;
import com.basket.containers.BasketSession;
import com.basket.general.BuyEvent;
import com.example.basket.R;

public class Products_In_Checkout_List_View extends ListFragment{
	//private String[] list_items;
	private ArrayList<BuyEvent> products2;
	public ArrayList<BuyEvent> getProducts2() {
		return products2;
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.orders_list, container, false);
		//products = OrderContainer.getOrdersInUserSession().userOrders.get(1).productsinorder;
		int number = this.getActivity().getIntent().getIntExtra("basketNum", -1);
		products2 = BasketSession.getUser().getBaskets().get(number).getBuyEvents();
		setListAdapter(new ProductInCheckoutAdapter(getActivity(), products2));
		return rootView;
	}

}
