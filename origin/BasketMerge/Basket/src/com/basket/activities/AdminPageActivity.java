package com.basket.activities;

import com.example.basket.R;
import com.example.basket.R.id;
import com.example.basket.R.layout;
import com.example.basket.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class AdminPageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_page);
		this.findViewById(R.id.create_account).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				Intent accountCreate = new Intent(arg0.getContext(),AdminSignUp.class);
				startActivity(accountCreate);
			}
			
		});
		
		this.findViewById(R.id.account_manage).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				Intent accountManage = new Intent(arg0.getContext(),SearchAccountActivity.class);
				startActivity(accountManage);
			}
			
		});
		this.findViewById(R.id.total_sales).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				Intent accountManage = new Intent(arg0.getContext(),DateSaleActivity.class);
				startActivity(accountManage);
			}
			
		});
		this.findViewById(R.id.salesbyproduct).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				Intent accountManage = new Intent(arg0.getContext(),AdminProductActivity.class);
				startActivity(accountManage);
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_page, menu);
		return true;
	}

}
