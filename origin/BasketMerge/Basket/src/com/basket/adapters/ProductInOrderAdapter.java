package com.basket.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.basket.general.BuyEvent;
import com.basket.general.Event;
import com.example.basket.R;


public class ProductInOrderAdapter extends ArrayAdapter<BuyEvent>
{
	private Context context;
	public ProductInOrderAdapter(Context activity,ArrayList<BuyEvent> products)
	{
		super(activity,0,products);
		context=activity;
	}
	public View getView(int pos,View convertView, ViewGroup parent)
	{
		if (convertView==null)
		{
			convertView=((Activity)context).getLayoutInflater().inflate(R.layout.product_view_orders, null);
			
		}
		BuyEvent currentProduct = this.getItem(pos);	
		
		((TextView)convertView.findViewById(R.id.product)).setText(currentProduct.getProduct().getName());
		((TextView)convertView.findViewById(R.id.pricemybasket)).setText("$"+Double.toString(currentProduct.getPrice()));
		((TextView)convertView.findViewById(R.id.supplier)).setText(currentProduct.getProduct().getManufacturer());

		return convertView;
		
	}
}
