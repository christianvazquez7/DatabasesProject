package com.application.settings;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basket.adapters.ProductInMyShopAdapter;
import com.basket.myshop.MyShop;
import com.example.basket.R;

public class SellingItemsListView extends ListFragment{
	private ArrayList<Products> sellingItems;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.myshop_list, container, false);
			sellingItems = MyShop.getShopInUserSession().productsinsell;
			setListAdapter(new ProductInMyShopAdapter(getActivity(), sellingItems));
		
		
		return rootView;
	}

}
