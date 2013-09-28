package com.basket.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.basket.general.Products;
import com.example.basket.R;

public class ProductInOrderAdapter extends ArrayAdapter<Products>
{
	private Context context;
	public ProductInOrderAdapter(Context activity,ArrayList<Products> products)
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
		Products currentProduct = this.getItem(pos);	
		return convertView;
		
	}
}
