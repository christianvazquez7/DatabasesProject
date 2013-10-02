package com.basket.adapters;

import java.util.ArrayList;

import com.basket.general.BuyEvent;
import com.example.basket.R;
import com.example.basket.R.id;
import com.example.basket.R.layout;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ProductAdapter extends ArrayAdapter<BuyEvent>
{
	private Context context;
	public ProductAdapter(Context activity,ArrayList<BuyEvent> products)
	{
		super(activity,0,products);
		context=activity;
	}
	public View getView(int pos,View convertView, ViewGroup parent)
	{
		if (convertView==null)
		{
			convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.product_view, null);
			
		}
		BuyEvent currentProduct = this.getItem(pos);	
		
		((TextView)convertView.findViewById(R.id.product)).setText(currentProduct.getProduct().getName());
		((TextView)convertView.findViewById(R.id.price)).setText("$"+Double.toString(currentProduct.getPrice()));
		((TextView)convertView.findViewById(R.id.supplier)).setText(currentProduct.getProduct().getManufacturer());

		
		

		//((TextView)convertView.findViewById(R.id.product))
		return convertView;
		
	}
}