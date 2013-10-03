package com.basket.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.basket.general.ProductBasket;
import com.example.basket.R;

public class BasketAdapter extends ArrayAdapter<ProductBasket>
{
	private Context context;
	public BasketAdapter(Context activity,ArrayList<ProductBasket> baskets)
	{
		super(activity,0,baskets);
		context=activity;
	}
	public View getView(int pos,View convertView, ViewGroup parent)
	{
		if (convertView==null)
		{
			convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.basket_overview, null);

		}
		ProductBasket currentBasket = this.getItem(pos);
		TextView name =(TextView) convertView.findViewById(R.id.basketName);
		name.setText(currentBasket.getName());
		return convertView;

	}
}