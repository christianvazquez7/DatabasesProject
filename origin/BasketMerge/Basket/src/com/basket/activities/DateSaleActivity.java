package com.basket.activities;

import com.example.basket.R;
import com.example.basket.R.layout;
import com.example.basket.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class DateSaleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_date_sales);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.date_sale, menu);
		return true;
	}

}
