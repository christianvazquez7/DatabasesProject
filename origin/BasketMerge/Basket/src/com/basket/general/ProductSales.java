package com.basket.general;

import com.example.basket.R;
import com.example.basket.R.layout;
import com.example.basket.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ProductSales extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_sales);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product_sales, menu);
		return true;
	}

}
