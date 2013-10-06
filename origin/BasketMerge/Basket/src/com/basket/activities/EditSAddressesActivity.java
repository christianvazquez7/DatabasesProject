package com.basket.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.basket.containers.AdminSession;
import com.basket.containers.BasketSession;
import com.basket.general.Adress;
import com.basket.general.CreditCard;
import com.basket.general.User;
import com.basket.lists.EditShippingAddressListFragment;
import com.basket.lists.ShippingAddressListFragment;
import com.example.basket.R;

public class EditSAddressesActivity extends FragmentActivity {

	private EditShippingAddressListFragment lf;
	private Button mSASaveButton;
	private User theUser;
	private ArrayList<Adress> shipAddresses;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_saddresses);

		theUser = BasketSession.getUser();
		shipAddresses = (theUser.getShippingAdress() == null) ? new ArrayList<Adress>(): theUser.getShippingAdress();

		FragmentManager fm = this.getSupportFragmentManager();
		lf = (EditShippingAddressListFragment) fm.findFragmentById(R.id.lvSAEditList);
		if(lf == null){
			lf = new EditShippingAddressListFragment();
			fm.beginTransaction().add(R.id.lvSAEditList, lf).commit();
		}


		mSASaveButton = (Button) findViewById(R.id.addShippingAddressButton);
		mSASaveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				shipAddresses.add(new Adress());
				Intent newIntent = new Intent(EditSAddressesActivity.this, EditSingleSAActivity.class);
				newIntent.putExtra("selectedUser", EditSAddressesActivity.this.getIntent().getIntExtra("selectedUser", 0));
				newIntent.putExtra("selectedShipAdd", shipAddresses.size()-1);
				newIntent.putExtra("createdNewAddress", true);
				startActivity(newIntent);
			}
		});
	}

	@Override
	protected void onResume(){
		super.onResume();
		if(lf!=null){
			((ArrayAdapter<Adress>)lf.getListAdapter()).notifyDataSetChanged();
		}
	}
}