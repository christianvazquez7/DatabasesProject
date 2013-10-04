package com.basket.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.basket.general.BidEvent;
import com.basket.general.Products;
import com.example.basket.R;

public class ProductInMyShopBidAdapter extends ArrayAdapter<BidEvent>
{
	private Context context;
	public ProductInMyShopBidAdapter(Context activity,ArrayList<BidEvent> products)
	{
		super(activity,0,products);
		context=activity;
	}
	public View getView(int pos,View convertView, ViewGroup parent)
	{
		if (convertView==null)
		{
			BidEvent currentProduct = this.getItem(pos);	
			
			convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.bidproduct_view3, null);
			if (currentProduct.isFinalized())
				convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.bidproduct_view4, null);			
			
		}
		//Products currentProduct = this.getItem(pos);	
		return convertView;
		
	}
}
