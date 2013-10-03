package com.basket.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.basket.R;
import com.example.basket.R.id;
import com.example.basket.R.layout;

public class FrontPageActivity extends Activity {

	private Button mPicknickButton, mProductList, mBasketList,admin, mHomePageButton, mOrderListButton, mCheckoutButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_front_page);

		mPicknickButton = (Button) findViewById(R.id.bEditCreditCards);
		mPicknickButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myPicknickIntent = new Intent(FrontPageActivity.this,PicknickActivity.class);
				startActivity(myPicknickIntent);
			}
		});
		
		mCheckoutButton = (Button) findViewById(R.id.checkoutButton);
		mCheckoutButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myPicknickIntent = new Intent(FrontPageActivity.this,CheckoutActivity.class);
				startActivity(myPicknickIntent);
			}
		});
		
		mProductList = (Button) findViewById(R.id.productListButton);
		mProductList.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent pl = new Intent(FrontPageActivity.this,ProductFragmentActivity.class);
				startActivity(pl);

			}
		});

		mHomePageButton = (Button) findViewById(R.id.bHomePage);
		mHomePageButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent hpIntent = new Intent(FrontPageActivity.this,HomePageActivity.class);
				startActivity(hpIntent);



			}
		});
		admin=(Button) findViewById(R.id.AdminButton);
		admin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent pl = new Intent(FrontPageActivity.this,AdminPageActivity.class);
				startActivity(pl);

			}
		});

	}
}
