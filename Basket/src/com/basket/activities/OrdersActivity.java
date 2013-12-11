
package com.basket.activities;

import java.util.ArrayList;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.basket.adapters.TabsAdapter;
import com.basket.containers.BasketSession;
import com.basket.fragments.InvoiceFragment;
import com.basket.general.Order;
import com.basket.lists.Products_List_View;
import com.example.basket.R;

public class OrdersActivity extends FragmentActivity {
	
	private ViewPager pager;
	private TabsAdapter mTabsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		pager = new ViewPager(this);
		pager.setId(R.id.pager);
		pager.setBackground(this.getResources().getDrawable(R.drawable.wood_light2));
		setContentView(pager);
		ArrayList<Order> orders = BasketSession.getUser().getUserOrders();
		//orders = OrderContainer.getOrdersInUserSession();
		
		final ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		mTabsAdapter = new TabsAdapter(this, pager);
		for(int i=0;i<orders.size();i++){
			Products_List_View.ordernum=i;
			Bundle args = new Bundle();
			args.putInt("pos", i);
			mTabsAdapter.addTab(bar.newTab().setText("Order "+(i+1)), InvoiceFragment.class, args);

			// mTabsAdapter.addTab(bar.newTab().setText("Order "+(i+1)), Products_List_View.class, args);
		}
		
	}

}
