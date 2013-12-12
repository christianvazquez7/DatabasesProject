package com.basket.lists;


import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.basket.adapters.BidsInSellingProductAdapter;
import com.basket.containers.BasketSession;
import com.basket.general.Bid;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.icom.dbclass.R;
import com.basket.restrequest.BidOnEventList;
import com.basket.restrequest.BidOnEventRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;


public class BidsListView extends ListFragment{
	//private String[] list_items;
	private SpiceManager spiceManager= new SpiceManager(CarJsonSpringAndroidSpiceService.class);
	private int itemClicked;
	private  PullToRefreshListView listView;
	private ArrayList<Bid> bidsOnProduct;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.bids_list, container, false);
		bidsOnProduct = BasketSession.getBidSearch();
		 itemClicked = (Integer) this.getActivity().getIntent().getExtras().get("itemClicked");
		setListAdapter(new BidsInSellingProductAdapter(getActivity(), bidsOnProduct));
		
		listView = (PullToRefreshListView) rootView.findViewById(R.id.pull_to_refresh_listview);
		listView.setShowLastUpdatedText(true);
		listView.setOnRefreshListener(new OnRefreshListener() 
		{

		    @Override
		    public void onRefresh() 
		    {
		    	if(!spiceManager.isStarted())
		    	{
		    	spiceManager.start(getActivity());
				BidOnEventRequest JsonSpringAndroidRequest = new BidOnEventRequest(BasketSession.getUser().getCurrentlySellingOnBid().get(itemClicked).getId());
				spiceManager.execute(JsonSpringAndroidRequest, "", DurationInMillis.ALWAYS_EXPIRED, new GetBidsListener());
		    	}
		    }
		});



		return rootView;
	}
	
	private class GetBidsListener implements RequestListener<BidOnEventList>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				Toast.makeText(getActivity(), "No connection to server", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
			listView.onRefreshComplete();
		}

		@Override
		public void onRequestSuccess(BidOnEventList bids) 
		{
			spiceManager.shouldStop();
			BasketSession.setBids(bids.getBids());
			bidsOnProduct.clear();
			for (Bid b:bids.getBids())
			{
				bidsOnProduct.add(b);
			}
			listView.onRefreshComplete();
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}


	}


}
