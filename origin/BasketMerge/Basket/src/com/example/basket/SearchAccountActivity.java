package com.example.basket;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class SearchAccountActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_search);
		overridePendingTransition(R.anim.enter, R.anim.leave);
		Fragment userList = this.getSupportFragmentManager().findFragmentById(R.id.accountListContainer);
		
		if (userList==null){
			this.getSupportFragmentManager().beginTransaction().add(R.id.accountListContainer, new UserListFragmet()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_account, menu);
		return true;
	}

}
