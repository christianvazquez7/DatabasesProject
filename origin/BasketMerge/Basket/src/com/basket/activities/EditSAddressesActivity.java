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
import com.basket.general.User;
import com.basket.lists.EditShippingAddressListFragment;
import com.basket.lists.ShippingAddressListFragment;
import com.example.basket.R;

public class EditSAddressesActivity extends FragmentActivity {

	private ListView mSAListView;

	private Button mSASaveButton;
	private User theUser;

	private ArrayList<Adress> shipAddresses;

	private int user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_saddresses);

		user = this.getIntent().getIntExtra("selectedUser", 0);
		theUser = BasketSession.getUser();
		
		
		shipAddresses = (theUser.getShippingAdress() == null) ? new ArrayList<Adress>(): theUser.getShippingAdress();

		FragmentManager fm = this.getSupportFragmentManager();
		EditShippingAddressListFragment lf = (EditShippingAddressListFragment) fm.findFragmentById(R.id.lvSAEditList);
		if(lf == null){
			lf = new EditShippingAddressListFragment();
			fm.beginTransaction().add(R.id.lvSAEditList, lf).commit();
		}
		
		
		mSASaveButton = (Button) findViewById(R.id.bSAEditSave);
		mSASaveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				theUser.setShippingAdress(shipAddresses);
			}
		});
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.add, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.add_icon:
	            this.shipAddresses.add(new Adress());
	            return true;
	        case R.id.action_settings:
	            
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
}