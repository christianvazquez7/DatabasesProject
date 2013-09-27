package com.application.settings;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.basket.R;

public class ShippingAddressListFragment extends ListFragment {
	private ArrayList<Address> availableaddresses;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.shippingaddresslist_list, container, false);
		availableaddresses = AddressContainer.userAddresses;
		setListAdapter(new ShippingAddressListAdapter(getActivity(), availableaddresses));


		return rootView;
	}
	public void onListItemClick(ListView l, View v, int pos ,  long id ){
		AddressContainer.shippingSelection = availableaddresses.get(pos);
		CheckoutActivity.changeShippingAddressCard = true;
		this.getActivity().finish();
		
	}
}
