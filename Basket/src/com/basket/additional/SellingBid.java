package com.basket.additional;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.basket.icom.dbclass.R;

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
