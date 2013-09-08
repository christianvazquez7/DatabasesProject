package com.example.basket;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class PicknickActivity extends Activity {
	private Button mProfile;
	private Button mBasketList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picknick);
		mProfile = (Button) findViewById(R.id.Button01);
		mProfile.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(PicknickActivity.this,SettingPopupProfileActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.enter_animation, R.anim.exit_animation);
			}
		});
		
		mBasketList = (Button) findViewById(R.id.basketsButton);
		mBasketList.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent pl = new Intent(PicknickActivity.this,BasketFragmentActivity.class);
				startActivity(pl);
				overridePendingTransition(R.anim.enter_animation, R.anim.exit_animation);
			}
		});
	}



}
