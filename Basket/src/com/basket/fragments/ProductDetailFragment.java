package com.basket.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.basket.containers.BasketSession;
import com.basket.general.BidEvent;
import com.basket.general.BuyEvent;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.Event;
import com.basket.general.UserRatingList;
import com.basket.restrequest.GetURatingRequest;
import com.basket.restrequest.UpdateBidRequest;
import com.example.basket.R;
import com.example.basket.UserReviewListActivity;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class ProductDetailFragment extends Fragment 
{
	private Event theEvent;
	private SpiceManager spiceManager= new SpiceManager(CarJsonSpringAndroidSpiceService.class);

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{

		View view = inflater.inflate(R.layout.detail_layout,
				container, false);
		if(theEvent.isBid()){
			((TextView)view.findViewById(R.id.description)).setText(((BidEvent) theEvent).getDescription());
			((TextView)view.findViewById(R.id.feats)).setText(((BidEvent) theEvent).getFeatures());
			((TextView)view.findViewById(R.id.dimension)).setText(((BidEvent) theEvent).getProduct().getDimensions());
			//((TextView)view.findViewById(R.id.ISBN2)).setText(((BidEvent) theEvent).getProduct().getProductPId());

		
		}
		else{
			//((TextView)view.findViewById(R.id.bin)).setText(theEvent.getProduct().getISBN());
			((TextView)view.findViewById(R.id.description)).setText(((BuyEvent) theEvent).getDescription());
			((TextView)view.findViewById(R.id.feats)).setText(((BuyEvent) theEvent).getFeatures());
			((TextView)view.findViewById(R.id.dimension)).setText(((BuyEvent) theEvent).getProduct().getDimensions());
			//((TextView)view.findViewById(R.id.ISBN2)).setText(((BuyEvent) theEvent).getProduct().getProductPId());

		}
		
		
		
		View add =(View)view.findViewById(R.id.sellerRatings);
		add.setOnClickListener(new OnClickListener(){

			
			public void onClick(View v) 
			{
				
				if(!spiceManager.isStarted()){
					spiceManager.start(getActivity());
					GetURatingRequest JsonSpringAndroidRequest = new GetURatingRequest(theEvent.getSeller());
					spiceManager.execute(JsonSpringAndroidRequest, "", DurationInMillis.ALWAYS_EXPIRED, new GetRatingsListener());
				}
				
			}
			
		});
		return view;
	}

	public void setEvent(Event currentEvent) 
	{
		// TODO Auto-generated method stub
		this.theEvent=currentEvent;
	}
	private class GetRatingsListener implements RequestListener<UserRatingList>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				Toast.makeText(getActivity(), "No connection to server", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(UserRatingList bids) 
		{
			spiceManager.shouldStop();
			BasketSession.setRatings(bids.getR());
			Intent i;
			i = new Intent(getActivity(),UserReviewListActivity.class);
			startActivity(i);

		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}


	}
}
