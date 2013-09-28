package com.basket.fragments;

import com.basket.general.BuyEvent;
import com.example.basket.R;
import com.example.basket.R.id;
import com.example.basket.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProductDetailFragment extends Fragment 
{
	private BuyEvent theEvent;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) 
	{
		    View view = inflater.inflate(R.layout.detail_layout,
		        container, false);
			//((TextView)view.findViewById(R.id.bin)).setText(theEvent.getProduct().getISBN());
		    ((TextView)view.findViewById(R.id.description)).setText(theEvent.getDescription());
			((TextView)view.findViewById(R.id.feats)).setText(theEvent.getFeatures());
			((TextView)view.findViewById(R.id.dim)).setText("Product Dimensions: "+theEvent.getProduct().getWidth()+" inches x "+theEvent.getProduct().getHeight()+" inches x "+theEvent.getProduct().getDepth()+ " inches");
		    return view;
		  }

	public void setEvent(BuyEvent currentEvent) 
	{
		// TODO Auto-generated method stub
		this.theEvent=currentEvent;
	}
}
