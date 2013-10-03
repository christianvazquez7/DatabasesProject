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

public class ProductDetailFragment extends Fragment 
{
	private Event theEvent;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{

		View view = inflater.inflate(R.layout.detail_layout,
				container, false);
		if(theEvent.isBid()){
			((TextView)view.findViewById(R.id.description)).setText(((BidEvent) theEvent).getDescription());
			((TextView)view.findViewById(R.id.feats)).setText(((BidEvent) theEvent).getFeatures());
			((TextView)view.findViewById(R.id.dim)).setText("Product Dimensions: "+((BidEvent) theEvent).getProduct().getWidth()+" inches x "+((BidEvent) theEvent).getProduct().getHeight()+" inches x "+((BidEvent) theEvent).getProduct().getDepth()+ " inches");
		
		}
		else{
			//((TextView)view.findViewById(R.id.bin)).setText(theEvent.getProduct().getISBN());
			((TextView)view.findViewById(R.id.description)).setText(((BuyEvent) theEvent).getDescription());
			((TextView)view.findViewById(R.id.feats)).setText(((BuyEvent) theEvent).getFeatures());
			((TextView)view.findViewById(R.id.dim)).setText("Product Dimensions: "+((BuyEvent) theEvent).getProduct().getWidth()+" inches x "+((BuyEvent) theEvent).getProduct().getHeight()+" inches x "+((BuyEvent) theEvent).getProduct().getDepth()+ " inches");
		}
		return view;
	}

	public void setEvent(Event currentEvent) 
	{
		// TODO Auto-generated method stub
		this.theEvent=currentEvent;
	}
}
