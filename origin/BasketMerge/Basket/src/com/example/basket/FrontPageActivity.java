package com.example.basket;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class FrontPageActivity extends Activity {
	Button mPicknickButton, mProductList, mBasketList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_front_page);
		mPicknickButton = (Button) findViewById(R.id.button1);
		mPicknickButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myPicknickIntent = new Intent(FrontPageActivity.this,PicknickActivity.class);
				startActivity(myPicknickIntent);
			}
		});
		mProductList = (Button) findViewById(R.id.button2);
		mProductList.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent pl = new Intent(FrontPageActivity.this,ProductFragmentActivity.class);
				startActivity(pl);
				
			}
		});
		
	}


}
