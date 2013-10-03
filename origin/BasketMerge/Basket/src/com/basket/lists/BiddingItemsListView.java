package com.basket.lists;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basket.adapters.ProductInMyShopBidAdapter;
import com.basket.containers.BasketSession;
import com.basket.general.BidEvent;
import com.example.basket.R;

public class BiddingItemsListView extends ListFragment{
	private ArrayList<BidEvent> bidItems;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.myshop_list, container, false);

		bidItems = BasketSession.getUser().getCurrentlyBiddingOn();
		setListAdapter(new ProductInMyShopBidAdapter(getActivity(), bidItems));


		return rootView;
	}

}
