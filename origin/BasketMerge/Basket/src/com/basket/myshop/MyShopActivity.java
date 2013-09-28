package com.basket.myshop;

import com.application.settings.BiddingItemsListView;
import com.application.settings.SellingItemsListView;
import com.basket.adapters.TabsAdapter;
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
	private MyShop shopContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		pager = new ViewPager(this);
		pager.setId(R.id.pager);
		setContentView(pager);
		shopContainer = MyShop.getShopInUserSession();
		
		final ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		mTabsAdapter = new TabsAdapter(this, pager);
		mTabsAdapter.addTab(bar.newTab().setText("Bidding Items"), BiddingItemsListView.class, null);
		mTabsAdapter.addTab(bar.newTab().setText("Selling Items"), SellingItemsListView.class, null);
		
	}

}
