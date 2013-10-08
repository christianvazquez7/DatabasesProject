package com.basket.activities;

import com.basket.containers.AddressContainer;
import com.basket.lists.ShippingAddressListFragment;
import com.example.basket.R;
import com.example.basket.R.layout;
import com.example.basket.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;

public class ShippingAddressSelectActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shipping_address_select);
		AddressContainer.getAddressContainer();
		FragmentManager fragMan = getSupportFragmentManager();
		Fragment shippingAddressListFragment = fragMan.findFragmentById(R.id.shippingAddressSelectListContainer);
		if(shippingAddressListFragment == null){
			shippingAddressListFragment = new ShippingAddressListFragment();
			fragMan.beginTransaction().add(R.id.shippingAddressSelectListContainer, shippingAddressListFragment).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.credit_card_select, menu);
		return true;
	}

}
