package com.basket.lists;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basket.adapters.BidProductInOrderAdapter;
import com.basket.adapters.ProductInCheckoutAdapter;
import com.basket.containers.BasketSession;
import com.basket.general.BidEvent;
import com.example.basket.R;

public class Bid_Order_List extends ListFragment{
	//private String[] list_items;
	private ArrayList<BidEvent> products2;
	public ArrayList<BidEvent> getProducts2() {
		return products2;
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.orders_list, container, false);
		//products = OrderContainer.getOrdersInUserSession().userOrders.get(1).productsinorder;
		products2=new ArrayList<BidEvent>();
		products2.add(BasketSession.getBidCheckout());
		setListAdapter(new BidProductInOrderAdapter(getActivity(), products2));
		return rootView;
	}

}
