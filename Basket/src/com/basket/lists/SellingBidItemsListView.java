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

import com.basket.activities.BidWinActivity;
import com.basket.activities.BidsOnProductActivity;
import com.basket.adapters.ProductInMyShopBidAdapter;
import com.basket.containers.BasketSession;
import com.basket.general.BidEvent;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.restrequest.BidOnEventList;
import com.basket.restrequest.BidOnEventRequest;
import com.basket.restrequest.UpdateBidRequest;
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
	private int pos;
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
	public void onListItemClick(ListView l, View v, int pos ,  long id )
	{
		if (!spiceManager.isStarted())
		{
			spiceManager.start(getActivity());
			BidOnEventRequest JsonSpringAndroidRequest = new BidOnEventRequest(BasketSession.getUser().getCurrentlySellingOnBid().get(pos).getId());
			spiceManager.execute(JsonSpringAndroidRequest, "", DurationInMillis.ALWAYS_EXPIRED, new GetBidsListener());
		}
		
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
		public void onRequestSuccess(BidList finished) 
		{
			spiceManager.shouldStop();
			for (int i:finished.getToFinish()){
				Log.d("try",Integer.toString(i));
			}
			for (BidEvent b :BasketSession.getUser().getCurrentlySellingOnBid())
			{

				if(finished.getToFinish().contains(b.getId()))
				{
					Log.d("try","donas");
					b.setFinalized(true);
				}
			}

			ArrayAdapter a =((ArrayAdapter)getListAdapter());
			if (a!=null)
				a.notifyDataSetChanged();

			listView.refreshDrawableState();
			listView.onRefreshComplete();

		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}


	}
	private class GetBidsListener implements RequestListener<BidOnEventList>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				Toast.makeText(getActivity(), "No connection to server", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(BidOnEventList bids) 
		{
			spiceManager.shouldStop();
			BasketSession.setBids(bids.getBids());
			BidEvent e =BasketSession.getUser().getCurrentlySellingOnBid().get(pos);
			Intent i=null;
			if (e.isFinalized())
			{
				if(e.getWinningBid()!=null)
					i = new Intent(getActivity(),BidWinActivity.class);
				else
				{
					Toast.makeText(getActivity(), "No Bidder", Toast.LENGTH_SHORT).show();
					BasketSession.getUser().getCurrentlySellingOnBid().remove(pos);
				}
			}
			else
				i = new Intent(getActivity(),BidsOnProductActivity.class);
			if(i!=null)
			{
			i.putExtra("itemClicked", pos);
			startActivity(i);
			}

		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}


	}
	public void onResume()
	{
		super.onResume();


		ArrayAdapter a =((ArrayAdapter)this.getListAdapter());
		if (a!=null)
			a.notifyDataSetChanged();




	}
}
