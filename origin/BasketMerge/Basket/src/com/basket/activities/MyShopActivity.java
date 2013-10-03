package com.basket.activities;

import com.basket.adapters.TabsAdapter;
import com.basket.containers.MyShop;
import com.basket.lists.BiddingItemsListView;
import com.basket.lists.SellingItemsListView;
import com.example.basket.R;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class MyShopActivity extends FragmentActivity {
	
	private ViewPager pager;
	private TabsAdapter mTabsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		pager = new ViewPager(this);
		pager.setId(R.id.pager);
		setContentView(pager);
		
		final ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		mTabsAdapter = new TabsAdapter(this, pager);
		mTabsAdapter.addTab(bar.newTab().setText("Bidding Items"), BiddingItemsListView.class, null);
		mTabsAdapter.addTab(bar.newTab().setText("Selling Items"), SellingItemsListView.class, null);
		
	}

}
