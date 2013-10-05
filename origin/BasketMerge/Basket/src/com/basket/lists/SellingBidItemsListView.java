package com.basket.lists;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.basket.activities.BidsOnProductActivity;
import com.basket.adapters.ProductInMyShopBidAdapter;
import com.basket.containers.BasketSession;
import com.basket.general.BidEvent;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.restrequest.UpdateBidRequest;
import com.example.basket.BidWinActivity;
import com.example.basket.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class SellingBidItemsListView extends android.app.ListFragment{
	private SpiceManager spiceManager= new SpiceManager(CarJsonSpringAndroidSpiceService.class);
	private ArrayList<BidEvent> sellingItems;
	private  PullToRefreshListView listView;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.myshop_bids, container, false);
			sellingItems = BasketSession.getUser().getCurrentlySellingOnBid();
			setListAdapter(new ProductInMyShopBidAdapter(getActivity(), sellingItems));
		
			
		 listView = (PullToRefreshListView) rootView.findViewById(R.id.pull_to_refresh_listview);
			listView.setShowLastUpdatedText(true);
			listView.setOnRefreshListener(new OnRefreshListener() 
			{

			    @Override
			    public void onRefresh() 
			    {
			    	if(!spiceManager.isStarted()){
						spiceManager.start(getActivity());
						
						 UpdateBidRequest JsonSpringAndroidRequest = new UpdateBidRequest();
						spiceManager.execute(JsonSpringAndroidRequest, "", DurationInMillis.ALWAYS_EXPIRED, new UpdateBidSellerListener());
			    	}

			      
			    }
			});

		
		return rootView;
	}
	public void onListItemClick(ListView l, View v, int pos ,  long id ){
		BidEvent e =BasketSession.getUser().getCurrentlySellingOnBid().get(pos);
		Intent i;
		if (e.isFinalized())
			i = new Intent(this.getActivity(),BidWinActivity.class);
		else
		i = new Intent(this.getActivity(),BidsOnProductActivity.class);
		i.putExtra("itemClicked", pos);
		startActivity(i);
	}

	private class UpdateBidSellerListener implements RequestListener<BidList>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				Toast.makeText(getActivity(), "No connection to server", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(BidList User) 
		{
			spiceManager.shouldStop();
			for (BidEvent b :BasketSession.getUser().getCurrentlySellingOnBid())
			{
				b.setFinalized(true);
			}
			
			listView.refreshDrawableState();
			listView.onRefreshComplete();
		
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}


	}
}
