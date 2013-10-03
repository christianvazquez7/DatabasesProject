package com.basket.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.basket.general.BidEvent;
import com.basket.general.BuyEvent;
import com.basket.general.Products;
import com.example.basket.R;

public class ProductsInMyShopBuyAdapter extends ArrayAdapter<BuyEvent>
{
	private Context context;
	public ProductsInMyShopBuyAdapter(Context activity,ArrayList<BuyEvent> products)
	{
		super(activity,0,products);
		context=activity;
	}
	public View getView(int pos,View convertView, ViewGroup parent)
	{
		if (convertView==null)
		{
			convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.product_view_myshop, null);
			
		}
		//Products currentProduct = this.getItem(pos);	
		return convertView;
		
	}
}
