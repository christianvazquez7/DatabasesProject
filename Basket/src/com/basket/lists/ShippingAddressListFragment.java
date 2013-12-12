package com.basket.lists;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.basket.activities.CheckoutActivity;
import com.basket.adapters.ShippingAddressListAdapter;
import com.basket.additional.BidCheckoutActivity;
import com.basket.containers.AddressContainer;
import com.basket.containers.BasketSession;
import com.basket.general.Adress;
import com.basket.icom.dbclass.R;

public class ShippingAddressListFragment extends ListFragment {
	private ArrayList<Adress> availableaddresses;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.shippingaddresslist_list, container, false);

		setListAdapter(new ShippingAddressListAdapter(getActivity(), BasketSession.getUser().getShippingAdress()));


		return rootView;
	}
	public void onListItemClick(ListView l, View v, int pos ,  long id ){
		AddressContainer.shippingSelection = BasketSession.getUser().getShippingAdress().get(pos);
		CheckoutActivity.changeShippingAddressCard = true;
		BidCheckoutActivity.changeShippingAddressCard = true;
		this.getActivity().finish();
		
	}
}
