package com.basket.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.basket.general.Adress;
import com.basket.general.CreditCard;
import com.example.basket.R;

public class ShippingAddressListAdapter extends ArrayAdapter<Adress>
{
	private Context context;
	public ShippingAddressListAdapter(Context activity,ArrayList<Adress> shippingAddresses)
	{
		super(activity,0,shippingAddresses);
		context=activity;
	}
	public View getView(int pos,View convertView, ViewGroup parent)
	{
		if (convertView==null)
		{
			convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.shippingaddress_view, null);
			
		}
		Adress currentAddress = this.getItem(pos);	
		((TextView)convertView.findViewById(R.id.shippingAddressLine1)).setText(currentAddress.getLine1());
		((TextView)convertView.findViewById(R.id.shippingAddressZipCode)).setText(Integer.toString(currentAddress.getZipCode()));
		return convertView;
		
	}
}
