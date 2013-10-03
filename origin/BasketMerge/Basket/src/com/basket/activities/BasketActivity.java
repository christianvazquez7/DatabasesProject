package com.basket.activities;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.basket.adapters.TabsAdapter;
import com.basket.containers.BasketSession;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.ProductBasket;
import com.basket.lists.ProductsInBuyBasketsList;
import com.basket.restrequest.NewBasketRequest;
import com.basket.restrequest.UpdateBasketRequest;
import com.example.basket.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class BasketActivity extends FragmentActivity {

	private ViewPager pager;
	private TabsAdapter mTabsAdapter;
	private int group1Id = 1;
	public static ViewPager currentPagePager;
	private final int addId = Menu.FIRST;
	private SpiceManager spiceManager = new SpiceManager(CarJsonSpringAndroidSpiceService.class);

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
		for(int i =0;i<listofbaskets.size();i++)
		{
			ProductsInBuyBasketsList.basketnum=i;
			Bundle args = new Bundle();
			args.putInt("pos", i);
			mTabsAdapter.addTab(bar.newTab().setText(listofbaskets.get(i).getName()), ProductsInBuyBasketsList.class, args);
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
					if(name =="")
					{
						name = "Basket "+mTabsAdapter.getCount();
					}
				
					Bundle args = new Bundle();
					ProductsInBuyBasketsList.basketnum++;
					args.putInt("pos", ProductsInBuyBasketsList.basketnum);
					
					final ActionBar bar = getActionBar();
					ProductBasket newBasket=new ProductBasket(name);
					BasketSession.getUser().getBaskets().add(new ProductBasket(name));
					mTabsAdapter.addTab(bar.newTab().setText(BasketSession.getUser().getBaskets().get(BasketSession.getUser().getBaskets().size()-1).getName()), ProductsInBuyBasketsList.class, args);
					spiceManager.start(BasketActivity.this);					
					NewBasketRequest JsonSpringAndroidRequest = new NewBasketRequest(newBasket);
					spiceManager.execute(JsonSpringAndroidRequest, "Basket_Update", DurationInMillis.ALWAYS_EXPIRED, new NewBasketListener());
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
	private class NewBasketListener implements RequestListener<Boolean>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {
			
			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {
				
				Toast.makeText(BasketActivity.this, "Basket could not be created", Toast.LENGTH_SHORT).show();
			}
			BasketSession.getUser().getBaskets().remove(BasketSession.getUser().getBaskets().size()-1);
			spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(Boolean bool) 
		{

			
			spiceManager.shouldStop();
			Toast.makeText(BasketActivity.this, "Basket Created", Toast.LENGTH_SHORT).show();
				
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{
		
		}
	}

}
