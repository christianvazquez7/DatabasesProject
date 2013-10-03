package com.basket.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.basket.general.Bid;
import com.basket.general.Products;
import com.example.basket.R;

public class BidsInSellingProductAdapter extends ArrayAdapter<Bid>
{
	private Context context;
	public BidsInSellingProductAdapter(Context activity,ArrayList<Bid> bids)
	{
		super(activity,0,bids);
		context=activity;
	}
	public View getView(int pos,View convertView, ViewGroup parent)
	{
		if (convertView==null)
		{
			convertView=((Activity)context).getLayoutInflater().inflate(R.layout.bid_item_view, null);
			
		}
		//Products currentProduct = this.getItem(pos);	
		return convertView;
		
	}
}
