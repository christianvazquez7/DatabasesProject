package com.application.settings;

import java.util.ArrayList;

import com.example.basket.R;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ProductInCheckoutAdapter extends ArrayAdapter<Products>
{
	private Context context;
	public ProductInCheckoutAdapter(Context activity,ArrayList<Products> products)
	{
		super(activity,0,products);
		context=activity;
	}
	public View getView(int pos,View convertView, ViewGroup parent)
	{
		if (convertView==null)
		{
			convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.product_view_orders, null);
			
		}
		Products currentProduct = this.getItem(pos);	
		return convertView;
		
	}
}
