package com.application.settings;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.basket.R;

public class CreditCardListAdapter extends ArrayAdapter<CreditCard>
{
	private Context context;
	public CreditCardListAdapter(Context activity,ArrayList<CreditCard> creditCards)
	{
		super(activity,0,creditCards);
		context=activity;
	}
	public View getView(int pos,View convertView, ViewGroup parent)
	{
		if (convertView==null)
		{
			convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.creditcard_view, null);
			
		}
		CreditCard currentCC = this.getItem(pos);	
		return convertView;
		
	}
}
