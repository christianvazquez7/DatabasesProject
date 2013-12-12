package com.basket.lists;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.basket.adapters.ProductsInMyShopBuyAdapter;
import com.basket.containers.BasketSession;
import com.basket.general.BuyEvent;
import com.basket.icom.dbclass.R;

public class SellingBuyItemsListView extends ListFragment{
	private ArrayList<BuyEvent> sellingItems;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.myshop_list2, container, false);
			sellingItems = BasketSession.getUser().getCurrentlySellingOnBuy();
			setListAdapter(new ProductsInMyShopBuyAdapter(getActivity(), sellingItems));
		
		
		return rootView;
	}
	public void onListItemClick(ListView l, View v, int pos ,  long id ){
//		Intent i = new Intent(this.getActivity(),BidsOnProductActivity.class);
//		i.putExtra("itemClicked", pos);
//		startActivity(i);
	}
	public void onResume()
	{
		super.onResume();
		

		ArrayAdapter a =((ArrayAdapter)this.getListAdapter());
		if (a!=null)
		a.notifyDataSetChanged();
		
		
	

	}

}
