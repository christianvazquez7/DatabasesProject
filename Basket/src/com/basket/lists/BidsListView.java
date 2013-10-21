package com.basket.lists;


import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basket.adapters.BidsInSellingProductAdapter;
import com.basket.containers.BasketSession;
import com.basket.general.Bid;
import com.example.basket.R;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;


public class BidsListView extends ListFragment{
	//private String[] list_items;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.bids_list, container, false);
		ArrayList<Bid> bidsOnProduct = new ArrayList<Bid>();
		Bid bid = new Bid ();
		bid.setAmmount(400);
		bid.setBidder(BasketSession.getUser());
		bid.setDay(1);
		bid.setHour(5);
		bid.setMinute(56);
		bid.setMonth(5);
		bid.setYear(2013);
		bidsOnProduct.add(bid);
		bidsOnProduct.add(bid);
		
		
		
		int itemClicked = (Integer) this.getActivity().getIntent().getExtras().get("itemClicked");
		setListAdapter(new BidsInSellingProductAdapter(getActivity(), bidsOnProduct));
		
		final PullToRefreshListView listView = (PullToRefreshListView) rootView.findViewById(R.id.pull_to_refresh_listview);
		listView.setShowLastUpdatedText(true);
		listView.setOnRefreshListener(new OnRefreshListener() 
		{

		    @Override
		    public void onRefresh() 
		    {
		        // Your code to refresh the list contents

		        // ...

		        // Make sure you call listView.onRefreshComplete()
		        // when the loading is done. This can be done from here or any
		        // other place, like on a broadcast receive from your loading
		        // service or the onPostExecute of your AsyncTask.

		        listView.onRefreshComplete();
		    }
		});



		return rootView;
	}


}