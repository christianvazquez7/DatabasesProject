package com.example.basket;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SellingBid extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selling_bid);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.selling_bid, menu);
		return true;
	}

}
