package com.example.basket;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ReviewAdapter extends ArrayAdapter<Review> {
	private Context context;
	public ReviewAdapter(Context activity,ArrayList<Review> products)
	{
		super(activity,0,products);
		context=activity;
	}
	public View getView(int pos,View convertView, ViewGroup parent)
	{
		if (convertView==null)
		{
			convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.review_view, null);
			
		}
		Review currentProduct = this.getItem(pos);
//		TextView productName = (TextView) convertView.findViewById(R.id.product);
//		productName.setText(currentProduct.getName());
//		
//		TextView supplierName = (TextView) convertView.findViewById(R.id.supplier);
//		productName.setText(currentProduct.getSupplier());
//		
		
		return convertView;
		
	}
}
