package com.application.settings;

import java.util.ArrayList;

import com.example.basket.R;
import com.example.basket.R.layout;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BasketAdapter extends ArrayAdapter<BasketContainer>
{
	private Context context;
	public BasketAdapter(Context activity,ArrayList<BasketContainer> baskets)
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
		BasketContainer currentBasket = this.getItem(pos);

		return convertView;
		
	}
}
