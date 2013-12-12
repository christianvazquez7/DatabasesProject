package com.basket.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.basket.general.Product;
import com.basket.icom.dbclass.R;

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
		if(convertView == null)
			convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.admin_product_view, null);

		Product currentProduct = (Product) this.getItem(pos);	
		((TextView)convertView.findViewById(R.id.theProductb)).setText(currentProduct.getName());
		((TextView)convertView.findViewById(R.id.supplier)).setText(currentProduct.getManufacturer());
		return convertView;

	}
}
