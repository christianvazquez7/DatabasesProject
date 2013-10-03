package com.basket.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.basket.general.Address;
import com.example.basket.R;

public class ShippingAddressListAdapter extends ArrayAdapter<Address>
{
	private Context context;
	public ShippingAddressListAdapter(Context activity,ArrayList<Address> shippingAddresses)
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
		//CreditCard currentCC = this.getItem(pos);	
		return convertView;
		
	}
}
