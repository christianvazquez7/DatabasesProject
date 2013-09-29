package com.basket.activities;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.basket.adapters.TabsAdapter;
import com.basket.containers.BasketSession;
import com.basket.general.ProductBasket;
import com.basket.lists.ProductsInBasketsList;
import com.example.basket.R;

public class BasketActivity extends FragmentActivity {

	private ViewPager pager;
	private TabsAdapter mTabsAdapter;
	private int group1Id = 1;
	public static ViewPager currentPagePager;
	private final int addId = Menu.FIRST;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		pager = new ViewPager(this);
		pager.setId(R.id.basketspager);
		setContentView(pager);
		
		final ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mTabsAdapter = new TabsAdapter(this, pager);
		ArrayList<ProductBasket> listofbaskets = BasketSession.getUser().getBaskets();

		//Set up baskets
		for(int i =0;i<listofbaskets.size();i++){
			ProductsInBasketsList.basketnum = i;
			mTabsAdapter.addTab(bar.newTab().setText(listofbaskets.get(i).getName()), ProductsInBasketsList.class, null);
		}
		currentPagePager = pager;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case addId:


			Builder alert = new Builder(this);
			alert.setTitle("Basket Name");
			alert.setMessage("Please choose a name for your basket");

			// Set an EditText view to get user input 
			final EditText input = new EditText(this);
			alert.setView(input);
			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					String name = input.getText().toString();
					if(name ==""){
						name = "Basket "+mTabsAdapter.getCount();
					}

					final ActionBar bar = getActionBar();
					BasketSession.getUser().getBaskets().add(new ProductBasket(name));
					ProductsInBasketsList.basketnum = ProductsInBasketsList.basketnum+1;
					mTabsAdapter.addTab(bar.newTab().setText(BasketSession.getUser().getBaskets().get(BasketSession.getUser().getBaskets().size()-1).getName()), ProductsInBasketsList.class, null);

				}
			});
			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.cancel();
				}
			});
			//
			alert.show();

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		menu.add(group1Id, addId, addId, "Add Basket").setIcon(R.drawable.plus_sign);
		getMenuInflater().inflate(R.menu.user_baskets, menu);
		return super.onCreateOptionsMenu(menu); 
	}
}
