package com.example.basket;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class FrontPageActivity extends Activity {
	Button mPicknickButton, mProductList, mBasketList, mHomePageButton, mCateListButton, mCatePageButton;
	
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
		mHomePageButton = (Button) findViewById(R.id.bHomePage);
		mHomePageButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent hpIntent = new Intent(FrontPageActivity.this,HomePageActivity.class);
				startActivity(hpIntent);
				
			}
		});
//		mCateListButton = (Button) findViewById(R.id.bCateList);
//		mCateListButton.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent clIntent = new Intent(FrontPageActivity.this,CategoryListActivity.class);
//				startActivity(clIntent);
//				
//			}
//		});
//		mCatePageButton = (Button) findViewById(R.id.bCatePage);
//		mCatePageButton.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent clIntent = new Intent(FrontPageActivity.this,CategoryPageActivity.class);
//				startActivity(clIntent);
//				
//			}
//		});
		
	}


}
