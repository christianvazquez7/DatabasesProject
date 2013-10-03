package com.basket.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.basket.general.BuyEvent;
import com.basket.general.Product;
import com.example.basket.R;

public class AdminProductAdapter extends ArrayAdapter<Product>
{
	private Context context;
	
	public AdminProductAdapter(Context activity,ArrayList<Product> products)
	{
		super(activity,0,products);
		context=activity;
	}
	public View getView(int pos,View convertView, ViewGroup parent)
	{
		if (convertView==null)
		{
			convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.product_view_admin, null);
			
		}
		Product currentProduct = this.getItem(pos);	
		
		((TextView)convertView.findViewById(R.id.product)).setText(currentProduct.getName());
		((TextView)convertView.findViewById(R.id.supplier)).setText(currentProduct.getManufacturer());
		return convertView;
		
	}
}
