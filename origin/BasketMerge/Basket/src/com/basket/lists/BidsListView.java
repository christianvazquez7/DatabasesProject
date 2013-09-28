package com.basket.lists;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basket.adapters.BidsInSellingProductAdapter;
import com.basket.adapters.ProductInOrderAdapter;
import com.basket.containers.OrderContainer;
import com.basket.general.Bid;
import com.example.basket.R;

public class BidsListView extends ListFragment{
	//private String[] list_items;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.bids_list, container, false);
		ArrayList<Bid> bidsOnProduct = new ArrayList<Bid>();
		bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));
		bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));
		bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));
		bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));
		bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));
		bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));
		bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));
		bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));
		bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));
		bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));
		bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));
		bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));
		bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));
		bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));
		bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));
		bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));
		bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));
		bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));
		bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));
		bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));
		bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));
		bidsOnProduct.add(new Bid(12.02f, "12/12/13", "Bob"));
		
		int itemClicked = (Integer) this.getActivity().getIntent().getExtras().get("itemClicked");
		//logica dce buscar producto
		setListAdapter(new BidsInSellingProductAdapter(getActivity(), bidsOnProduct));


		return rootView;
	}


}
