package com.basket.lists;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.basket.activities.BidsOnProductActivity;
import com.basket.adapters.ProductInMyShopBidAdapter;
import com.basket.containers.BasketSession;
import com.basket.general.BidEvent;
import com.example.basket.R;

public class SellingBidItemsListView extends ListFragment{
	private ArrayList<BidEvent> sellingItems;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.myshop_list, container, false);
			sellingItems = BasketSession.getUser().getCurrentlySellingOnBid();
			setListAdapter(new ProductInMyShopBidAdapter(getActivity(), sellingItems));
		
		
		return rootView;
	}
	public void onListItemClick(ListView l, View v, int pos ,  long id ){
		Intent i = new Intent(this.getActivity(),BidsOnProductActivity.class);
		i.putExtra("itemClicked", pos);
		startActivity(i);
	}

}
