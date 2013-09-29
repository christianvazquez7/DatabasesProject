package com.basket.activities;

import android.app.Activity;
import android.os.Bundle;

import com.basket.lists.BidsListView;
import com.example.basket.R;

public class BidsOnProductActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bidsonproduct);
		android.app.FragmentManager fm = this.getFragmentManager();
		android.app.Fragment fragment = fm.findFragmentById(R.id.bids_list_container);
		
		if (fragment == null)
		{
			
			fragment = new BidsListView();
			fm.beginTransaction().add(R.id.bids_list_container, fragment).commit();
			
		}
	}
}
