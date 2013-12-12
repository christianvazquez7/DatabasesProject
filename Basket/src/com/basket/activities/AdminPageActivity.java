package com.basket.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.basket.containers.AdminSession;
import com.basket.icom.dbclass.R;

public class AdminPageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		AdminSession.startSession();
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
		this.findViewById(R.id.createcategory).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				Intent accountManage = new Intent(arg0.getContext(),AdminCreateCategory.class);
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
