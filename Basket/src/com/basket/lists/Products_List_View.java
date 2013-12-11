package com.basket.lists;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basket.adapters.BidProductInOrderAdapter;
import com.basket.adapters.ProductInOrderAdapter;
import com.basket.containers.BasketSession;
import com.basket.general.BidEvent;
import com.basket.general.BuyEvent;
import com.example.basket.R;

public class Products_List_View extends ListFragment{
	public static int ordernum =0;
	private ArrayList<BuyEvent> products;
	private ArrayList<BidEvent>bproducts;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		Bundle bundle = this.getArguments();
		int myInt = bundle.getInt("pos", -1);
		View rootView = inflater.inflate(R.layout.orders_list, container, false);
		products = BasketSession.getUser().getUserOrders().get(myInt).getBuyEvents();
		if (products==null ||products.isEmpty())
		{
			bproducts =new ArrayList<BidEvent>();
			bproducts.add(BasketSession.getUser().getUserOrders().get(myInt).getBidEvent());
			setListAdapter(new BidProductInOrderAdapter(getActivity(), bproducts));

		}
		else
		setListAdapter(new ProductInOrderAdapter(getActivity(), products));
		return rootView;
	}

}
