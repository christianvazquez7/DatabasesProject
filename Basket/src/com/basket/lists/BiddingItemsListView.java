package com.basket.lists;

import java.util.ArrayList;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.basket.adapters.ProductInMyShopBidAdapter;
import com.basket.containers.BasketSession;
import com.basket.general.BidEvent;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.restrequest.BidEventList;
import com.basket.restrequest.WinBidRequest;
import com.example.basket.BidCheckoutActivity;
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

public class BiddingItemsListView extends ListFragment{
	private ArrayList<BidEvent> bidItems;
	private PullToRefreshListView listView;
	private SpiceManager spiceManager= new SpiceManager(CarJsonSpringAndroidSpiceService.class);

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.myshop_list, container, false);

		bidItems = BasketSession.getUser().getCurrentlyBiddingOn();
		setListAdapter(new ProductInMyShopBidAdapter(getActivity(), bidItems));
		
		 listView = (PullToRefreshListView) rootView.findViewById(R.id.pull_to_refresh_listview);
			listView.setShowLastUpdatedText(true);
			listView.setOnRefreshListener(new OnRefreshListener() 
			{

			    @Override
			    public void onRefresh() 
			    {
			    	if(!spiceManager.isStarted()){
						spiceManager.start(getActivity());
						
						WinBidRequest JsonSpringAndroidRequest = new WinBidRequest(BasketSession.getUser().getUserId());
						spiceManager.execute(JsonSpringAndroidRequest, "", DurationInMillis.ALWAYS_EXPIRED, new UpdateBidSellerListener());
			    	}

			      
			    }
			});




		return rootView;
	}
	
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		if(BasketSession.getUser().getCurrentlyBiddingOn().get(position).isAccepted()){
//		Toast.makeText(getActivity(), "Order Placed", Toast.LENGTH_SHORT).show();
//		Order orderToPlace = new Order();
//		orderToPlace.setAccount(1);
//		orderToPlace.setBidEvent(BasketSession.getUser().getCurrentlySellingOnBid().get(position));
//		orderToPlace.setDay(1);
//		orderToPlace.setMonth(5);
//		orderToPlace.setYear(2005);
//		BasketSession.getUser().getUserOrders().add(orderToPlace);
//		BasketSession.getUser().getCurrentlyBiddingOn().remove(position);
		BasketSession.setBidCheckout((BasketSession.getUser().getCurrentlyBiddingOn().get(position)));
		Intent checkOut = new Intent(this.getActivity(),BidCheckoutActivity.class);
		startActivity(checkOut);
		
		}
		


	}
	private class UpdateBidSellerListener implements RequestListener<BidEventList>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				Toast.makeText(getActivity(), "No connection to server", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
			listView.refreshDrawableState();
			listView.onRefreshComplete();
		}

		@Override
		public void onRequestSuccess(BidEventList User) 
		{
			spiceManager.shouldStop();
			BasketSession.getUser().setCurrentlyBiddingOn(User.getEvents());
			listView.refreshDrawableState();
			listView.onRefreshComplete();
		
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}


	}

}
