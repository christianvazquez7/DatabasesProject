package com.basket.activities;

import com.basket.containers.CreditCardContainer;
import com.basket.lists.CreditCardListFragment;
import com.example.basket.R;
import com.example.basket.R.layout;
import com.example.basket.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;

public class CreditCardSelectActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credit_card_select);
		CreditCardContainer.getCreditCardContainer();
		
		FragmentManager fragMan = getSupportFragmentManager();
		Fragment creditCardListFragment = fragMan.findFragmentById(R.id.creditCardSelectListContainer);
		if(creditCardListFragment == null){
			creditCardListFragment = new CreditCardListFragment();
			fragMan.beginTransaction().add(R.id.creditCardSelectListContainer, creditCardListFragment).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.credit_card_select, menu);
		return true;
	}

}
