package com.basket.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.basket.R;

public class PicknickActivity extends Activity {
	private Button mProfile, mBasketList, mFaqButton, mShopButton,mOrders;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picknick);
		mProfile = (Button) findViewById(R.id.realProfileButton);
		mProfile.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(PicknickActivity.this,EditUserActivity.class
						);
				startActivity(i);
				overridePendingTransition(R.anim.enter_animation, R.anim.exit_animation);
			}
		});
		mOrders = (Button) findViewById(R.id.realOrdersButton);
		mOrders.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(PicknickActivity.this,OrdersActivity.class
						);
				startActivity(i);
				overridePendingTransition(R.anim.enter_animation, R.anim.exit_animation);
			}
		});
		mFaqButton = (Button) findViewById(R.id.realFAQButton);
		mFaqButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(PicknickActivity.this,FaqActivity.class
						);
				startActivity(i);
				overridePendingTransition(R.anim.enter_animation, R.anim.exit_animation);
			}
		});
		mShopButton = (Button) findViewById(R.id.realMyShopButton);
		mShopButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(PicknickActivity.this,MyShopActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.enter_animation, R.anim.exit_animation);
			}
		});

		mBasketList = (Button) findViewById(R.id.realBasketsButton);
		mBasketList.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent pl = new Intent(PicknickActivity.this,BasketActivity.class);
				startActivity(pl);
				overridePendingTransition(R.anim.enter_animation, R.anim.exit_animation);
			}
		});
	}



}

