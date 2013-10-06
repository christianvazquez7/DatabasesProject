package com.basket.activities;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;

import com.basket.adapters.TabsAdapter;
import com.basket.lists.BiddingItemsListView;
import com.basket.lists.SellingBidItemsListView;
import com.basket.lists.SellingBuyItemsListView;
import com.example.basket.R;

public class MyShopActivity extends FragmentActivity {

	private ViewPager pager;
	private TabsAdapter mTabsAdapter;
	private PopupWindow mpopup;
	private Button createBid, createBuy;
	private AlertDialog alert;
	private SellingBuyItemsListView sellingBuy;
	private SellingBidItemsListView sellingBid;
	private BiddingItemsListView bidding;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		pager = new ViewPager(this);
		//FragmentPagerAdapter a = new FragmentPagerAdapter();
	
		
		pager.setId(R.id.pager);
		setContentView(pager);
		pager.setBackground(this.getResources().getDrawable(R.drawable.wood_light2));
		

		final ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mTabsAdapter = new TabsAdapter(this, pager);
		mTabsAdapter.addTab(bar.newTab().setText("Bidding Items"), BiddingItemsListView.class, null);
		mTabsAdapter.addTab(bar.newTab().setText("Selling Bid Items"), SellingBidItemsListView.class, null);
		mTabsAdapter.addTab(bar.newTab().setText("Selling Buy Now Items"), SellingBuyItemsListView.class, null);
		bidding =(BiddingItemsListView) mTabsAdapter.getItem(0);
		sellingBid =(SellingBidItemsListView) mTabsAdapter.getItem(1);
		sellingBuy =(SellingBuyItemsListView) mTabsAdapter.getItem(2);


	}
	@Override
	protected void onResume()
	{
		super.onResume();

		bidding =(BiddingItemsListView) mTabsAdapter.getItem(0);
		sellingBid =(SellingBidItemsListView) mTabsAdapter.getItem(1);
		sellingBuy =(SellingBuyItemsListView) mTabsAdapter.getItem(2);

		ArrayAdapter a =((ArrayAdapter)sellingBid.getListAdapter());
		if (a!=null)
		a.notifyDataSetChanged();
		ArrayAdapter b =((ArrayAdapter)sellingBuy.getListAdapter());
		if (b!=null)
			b.notifyDataSetChanged();
		ArrayAdapter c =((ArrayAdapter)bidding.getListAdapter());
		if (c!=null)
		c.notifyDataSetChanged();
		
		mTabsAdapter.notifyDataSetChanged();

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.my_shop_menubar, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_sell:
			AlertDialog.Builder builder = new AlertDialog.Builder(this); 
			builder.setMessage("Please choose what to do");

			// Set an EditText view to get user input 
			LayoutInflater inflater = getLayoutInflater();
			View popupview = inflater.inflate(R.layout.createevent_popup,null);
			createBid = (Button) popupview.findViewById(R.id.createBidEventButton);
			createBid.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(MyShopActivity.this,CreateBidActivity.class);
					startActivityForResult(i, 1);
					alert.dismiss();
				}
			});
			createBuy = (Button) popupview.findViewById(R.id.createBuyEventButton);
			createBuy.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(MyShopActivity.this,CreateBuyActivity.class);
					startActivityForResult(i, 1);
					ArrayAdapter b =((ArrayAdapter)sellingBuy.getListAdapter());
					if (b!=null)
						b.notifyDataSetChanged();
					alert.dismiss();


				}
			});


			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.cancel();
				}
			});

			builder.setView(popupview);
			alert = builder.create();
			alert.show();

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
