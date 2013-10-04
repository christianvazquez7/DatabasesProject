package com.basket.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.basket.general.BidEvent;
import com.basket.general.Products;
import com.example.basket.R;

public class ProductInMyShopAdapter2 extends ArrayAdapter<BidEvent>
{
	private Context context;
	public ProductInMyShopAdapter2(Context activity,ArrayList<BidEvent> products)
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
		BidEvent currentProduct = this.getItem(pos);	
	
		((TextView)convertView.findViewById(R.id.product)).setText(currentProduct.getProduct().getName());
		//((TextView)convertView.findViewById(R.id.bid)).setText("$"+Double.toString(currentProduct.getWinning().getAmmount()));
		return convertView;
		
	}
}
