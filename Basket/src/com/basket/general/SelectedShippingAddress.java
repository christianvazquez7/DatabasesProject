package com.basket.general;

import com.basket.containers.AddressContainer;
import com.example.basket.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.TextView;

public class SelectedShippingAddress extends Fragment {
//	private TextView billingAddress, ccNumber, secNum, expMonth, expYear, ccName;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.shippingaddress_view,container, false);
		((TextView)view.findViewById(R.id.shippingAddressLine1)).setText(AddressContainer.shippingSelection.getLine1());
		((TextView)view.findViewById(R.id.shippingAddressLine1)).setText(AddressContainer.shippingSelection.getLine1());
		((TextView)view.findViewById(R.id.line2)).setText(AddressContainer.shippingSelection.getLine2());
		((TextView)view.findViewById(R.id.city)).setText(AddressContainer.shippingSelection.getCity());
		((TextView)view.findViewById(R.id.country)).setText(AddressContainer.shippingSelection.getCountry());
		((TextView)view.findViewById(R.id.state)).setText(AddressContainer.shippingSelection.getState());



		

		((TextView)view.findViewById(R.id.shippingAddressZipCode)).setText(Integer.toString(AddressContainer.shippingSelection.getZipCode()));
		
		
		
//		((TextView)view.findViewById(R.id.shippingAddressZipCode)).setText(AddressContainer.shippingSelection.getZipCode());
		return view;
	}
}
