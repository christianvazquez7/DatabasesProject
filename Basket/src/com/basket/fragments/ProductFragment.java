package com.basket.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.basket.general.BidEvent;
import com.basket.general.BuyEvent;
import com.basket.general.Event;
import com.example.basket.R;


public class ProductFragment extends Fragment
{
	private Event theEvent;

	public void setEvent(Event e)
	{
		theEvent=e;
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if(theEvent.isBid()){
			BidEvent e = (BidEvent) theEvent;
			View view = inflater.inflate(R.layout.bidproduct_view2,
					container, false);
			((TextView)view.findViewById(R.id.bidproduct)).setText(((BidEvent) theEvent).getBidTitle());
			if (((BidEvent) theEvent).getWinningBid()!=null)
			((TextView)view.findViewById(R.id.bidprice)).setText("$"+Double.toString(((BidEvent) theEvent).getWinningBid().getAmmount()));
			else
				((TextView)view.findViewById(R.id.bidprice)).setText("$"+Double.toString(((BidEvent) theEvent).getMinBid()));

			((TextView)view.findViewById(R.id.bidsupplier)).setText(((BidEvent) theEvent).getProduct().getManufacturer());
			((TextView)view.findViewById(R.id.textView2)).setText(((BidEvent) theEvent).getProduct().getName());
			 final RatingBar minimumRating = (RatingBar)view.findViewById(R.id.bidratingBar1);
			    minimumRating.setRating(e.getRating());
				((TextView)view.findViewById(R.id.endDate)).setText(((BidEvent) theEvent).getEndingTime());

			return view;
		}
		else{
			View view = inflater.inflate(R.layout.product_view2,
					container, false);
			((TextView)view.findViewById(R.id.product)).setText(((BuyEvent) theEvent).getBtitle());
			((TextView)view.findViewById(R.id.textView1)).setText(((BuyEvent) theEvent).getProduct().getName());
			 final RatingBar minimumRating = (RatingBar)view.findViewById(R.id.ratingBar1);
			    minimumRating.setRating(((BuyEvent) theEvent).getRating());
			((TextView)view.findViewById(R.id.price)).setText("$"+Double.toString(((BuyEvent) theEvent).getPrice()));
			((TextView)view.findViewById(R.id.supplier)).setText(((BuyEvent) theEvent).getProduct().getManufacturer());
			return view;
		}

	}
}
