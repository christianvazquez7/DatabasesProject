package com.basket.lists;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.basket.activities.BidsOnProductActivity;
import com.basket.adapters.ProductInMyShopAdapter;
import com.basket.containers.MyShop;
import com.basket.general.Products;
import com.example.basket.R;

public class SellingItemsListView extends ListFragment{
	private ArrayList<Products> sellingItems;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.myshop_list, container, false);
			sellingItems = MyShop.getShopInUserSession().productsinsell;
			setListAdapter(new ProductInMyShopAdapter(getActivity(), sellingItems));
		
		
		return rootView;
	}
	public void onListItemClick(ListView l, View v, int pos ,  long id ){
		Intent i = new Intent(this.getActivity(),BidsOnProductActivity.class);
		i.putExtra("itemClicked", pos);
		startActivity(i);
	}

}
