package com.basket.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
			View view = inflater.inflate(R.layout.bidproduct_view2,
					container, false);
			((TextView)view.findViewById(R.id.bidproduct)).setText(((BidEvent) theEvent).getProduct().getName());
			((TextView)view.findViewById(R.id.bidprice)).setText("$"+Double.toString(((BidEvent) theEvent).getWinningBid().getAmmount()));
			((TextView)view.findViewById(R.id.bidsupplier)).setText(((BidEvent) theEvent).getProduct().getManufacturer());
			return view;
		}
		else{
			View view = inflater.inflate(R.layout.product_view2,
					container, false);
			((TextView)view.findViewById(R.id.product)).setText(((BuyEvent) theEvent).getProduct().getName());
			((TextView)view.findViewById(R.id.price)).setText("$"+Double.toString(((BuyEvent) theEvent).getPrice()));
			((TextView)view.findViewById(R.id.supplier)).setText(((BuyEvent) theEvent).getProduct().getManufacturer());
			return view;
		}

	}
}
