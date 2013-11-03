package com.basket.activities;

import android.os.Bundle;
import android.view.Window;

import com.basket.lists.BasketListFragment;
import com.example.basket.R;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class BasketFragmentActivity extends SlidingFragmentActivity {
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.setBehindContentView(R.layout.menu_layout);
		BasketFragmentActivity.this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_basket_fragment);
		android.app.FragmentManager fm = this.getFragmentManager();
		android.app.Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		if (fragment == null)
		{
			fragment = new BasketListFragment();
			fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
		}
	}



	

}
