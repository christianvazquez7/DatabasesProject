package com.application.settings;

import com.basket.adapters.TabsAdapter;
import com.example.basket.R;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class MainActivity extends FragmentActivity {
	
	private ViewPager pager;
	private TabsAdapter mTabsAdapter;
	private OrderContainer orders;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		pager = new ViewPager(this);
		pager.setId(R.id.pager);
		setContentView(pager);
		orders = OrderContainer.getOrdersInUserSession();
		
		final ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		mTabsAdapter = new TabsAdapter(this, pager);
		mTabsAdapter.addTab(bar.newTab().setText("Order 1"), List_View.class, null);
		mTabsAdapter.addTab(bar.newTab().setText("Order 2"), List_View.class, null);
		mTabsAdapter.addTab(bar.newTab().setText("Order 3"), List_View.class, null);
		mTabsAdapter.addTab(bar.newTab().setText("Order 4"), List_View.class, null);
		
	}

}
