package com.example.basket;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class AdminProductActivity extends SlidingFragmentActivity {

	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.setBehindContentView(R.layout.menu_layout);
		SlidingMenu sm = this.getSlidingMenu();
		sm.setBehindOffset(90);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setSlidingEnabled(true);
		setContentView(R.layout.activity_product_fragment);
		android.app.FragmentManager fm = this.getFragmentManager();
		android.app.Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		
		if (fragment == null)
		{
			
			fragment = new AdminProductList();
			fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
			
		}
		this.getActionBar().setDisplayShowTitleEnabled(false);
		this.getActionBar().setDisplayShowHomeEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product, menu);
		return true;
	}

}