package com.example.basket;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ProductFragment extends Fragment
{
	private BuyEvent theEvent;

	public void setEvent(BuyEvent e)
	{
		theEvent=e;
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.product_view2,
				container, false);
		((TextView)view.findViewById(R.id.product)).setText(theEvent.getProduct().getName());
		((TextView)view.findViewById(R.id.price)).setText("$"+Double.toString(theEvent.getPrice()));
		((TextView)view.findViewById(R.id.supplier)).setText(theEvent.getProduct().getManufacturer());

		return view;
	}
}
