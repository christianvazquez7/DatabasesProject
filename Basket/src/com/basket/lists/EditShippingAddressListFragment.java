package com.basket.lists;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.basket.activities.EditSingleSAActivity;
import com.basket.adapters.ShippingAddressListAdapter;
import com.basket.containers.BasketSession;
import com.basket.general.Adress;
import com.basket.icom.dbclass.R;

public class EditShippingAddressListFragment extends ListFragment {
	private ArrayList<Adress> availableaddresses;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.shippingaddresslist_list, container, false);
		availableaddresses = BasketSession.getUser().getShippingAdress();
		setListAdapter(new ShippingAddressListAdapter(getActivity(), availableaddresses));


		return rootView;
	}
	public void onListItemClick(ListView l, View v, int pos ,  long id ){
		Intent newIntent = new Intent(this.getActivity(), EditSingleSAActivity.class);
		newIntent.putExtra("selectedUser", this.getActivity().getIntent().getIntExtra("selectedUser", 0));
		newIntent.putExtra("selectedShipAdd", pos);
		startActivity(newIntent);
		
	}
}
