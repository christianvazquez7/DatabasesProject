package com.basket.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.basket.containers.BasketSession;
import com.basket.general.BidEvent;
import com.example.basket.R;

public class HarvestFragment extends Fragment
{
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) {
		    View view = inflater.inflate(R.layout.harvest_layout,
		        container, false);
		    
		    BidEvent event = (BidEvent) BasketSession.getProductSearch().get(this.getActivity().getIntent().getIntExtra("selectedEvent", 0));
		    TextView seller =(TextView)view.findViewById(R.id.Seller);
		    seller.setText(event.getCreator().getUsername());
		    
		    TextView winningBid =(TextView)view.findViewById(R.id.tvCatTitle);
		    winningBid.setText(Double.toString(event.getWinning().getAmmount()));
		    
		    TextView nextBid =(TextView)view.findViewById(R.id.TextView02);
		    nextBid.setText(Double.toString(event.getWinning().getAmmount()+event.getMinBid()));
		    
		    return view;
		  }
}
