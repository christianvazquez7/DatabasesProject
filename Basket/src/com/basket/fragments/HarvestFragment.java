package com.basket.fragments;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.basket.containers.BasketSession;
import com.basket.general.Bid;
import com.basket.general.BidEvent;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.restrequest.BidRequest;
import com.example.basket.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class HarvestFragment extends Fragment
{
	private SpiceManager spiceManager = new SpiceManager(CarJsonSpringAndroidSpiceService.class);
	private View view;
	Bid newBid;
	private BidEvent event;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.harvest_layout,
				container, false);


		event = (BidEvent) BasketSession.getProductSearch().get(this.getActivity().getIntent().getIntExtra("selectedEvent", 0));
		TextView seller =(TextView)view.findViewById(R.id.Seller);
		seller.setText(event.getCreator());

		TextView winningBid =(TextView)view.findViewById(R.id.tvCatTitle);
		if(event.getWinningBid()!=null)
		winningBid.setText(Double.toString(event.getWinningBid().getAmmount()));
		else
			winningBid.setText(Double.toString(event.getMinBid()));


		TextView nextBid =(TextView)view.findViewById(R.id.TextView02);
		TextView nextTime =(TextView)view.findViewById(R.id.pricemybasket);
		if(event.getWinningBid()!=null){
		nextBid.setText(Double.toString(event.getWinningBid().getAmmount()+event.getMinBid()));
		nextTime.setText(event.getWinningBid().getBidTime());

		}
		else{
			nextBid.setText(Double.toString(event.getMinBid()+event.getMinBid()));
			nextTime.setText("");

		}
		




		Button harvest = (Button) view.findViewById(R.id.harvest);
		harvest.setOnClickListener(new OnClickListener()
		{


			public void onClick(View arg0) 
			{
				try 
				{
					TextView ammountT = (TextView)view.findViewById(R.id.etCategoryPage);
					double ammount =Double.parseDouble(ammountT.getText().toString());
					Calendar c = Calendar.getInstance(); 
					int minute = c.get(Calendar.MINUTE);
					int hour = c.get(Calendar.HOUR);
					int day = c.get(Calendar.DAY_OF_MONTH);
					int month= c.get(Calendar.MONTH);
					int year = c.get(Calendar.YEAR);
					if(ammount<=event.getMinBid())
					{
						Toast.makeText(HarvestFragment.this.getActivity(), "Bid needs to be higher than minumum or current", Toast.LENGTH_LONG).show();
					}
					else{
						newBid = new Bid(ammount, day, month, year, hour, minute, BasketSession.getUser().getUsername());

						if (!spiceManager.isStarted())
						{
							spiceManager.start(getActivity());
							BidRequest JsonSpringAndroidRequest = new BidRequest(newBid,event);
							spiceManager.execute(JsonSpringAndroidRequest, "", DurationInMillis.ALWAYS_EXPIRED, new BidListener());
						}
					}
				}
				catch( NumberFormatException e)
				{
					Toast.makeText(getActivity(), "Error in Bid", Toast.LENGTH_SHORT).show();
					if (spiceManager.isStarted())
						spiceManager.shouldStop();
				}

			}


		});
		return view;
	}
	private class BidListener implements RequestListener<Boolean>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				Toast.makeText(getActivity(), "Error in Bid", Toast.LENGTH_SHORT).show();
				spiceManager.shouldStop();
			}
			Toast.makeText(getActivity(), "Error in Bid", Toast.LENGTH_SHORT).show();
			if(spiceManager.isStarted())
				spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(Boolean bool) 
		{
			spiceManager.shouldStop();
			//event.getBids().add(newBid);
			BasketSession.getUser().getCurrentlyBiddingOn().add(event);
			Toast.makeText(getActivity(), "Bid Posted", Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}
	}
}
