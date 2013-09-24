package com.application.settings;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.basket.R;

public class BiddingItemsListView extends ListFragment{
	private ArrayList<Products> bidItems;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.myshop_list, container, false);

		bidItems = MyShop.getShopInUserSession().productsinbid;
		setListAdapter(new ProductInMyShopAdapter(getActivity(), bidItems));


		return rootView;
	}

}
