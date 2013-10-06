package com.basket.activities;

import java.util.ArrayList;

import android.R.color;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
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
import com.basket.restrequest.RemoveBasketRequest;
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
	private ProductBasket temp;
	private int currentItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		pager = new ViewPager(this);
		pager.setId(R.id.basketspager);
		setContentView(pager);
		pager.setBackground(this.getResources().getDrawable(R.drawable.wood_light2));
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
			
			Tab newTab =bar.newTab().setText(listofbaskets.get(i).getName());
			
			//newTab.getCustomView().setBackgroundColor(Color.BLACK);
			mTabsAdapter.addTab(newTab, ProductsInBuyBasketsList.class, args);
			
			
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
					//bar.setBackgroundDrawable(Resources.getSystem().getDrawable(R.drawable.layer_gradient));
					ProductBasket newBasket=new ProductBasket(name);
					BasketSession.getUser().getBaskets().add(new ProductBasket(name));
					Tab newTab =bar.newTab().setText(BasketSession.getUser().getBaskets().get(BasketSession.getUser().getBaskets().size()-1).getName());
					//newTab.getCustomView().setBackgroundColor(color.black);
					
					mTabsAdapter.addTab(newTab, ProductsInBuyBasketsList.class, args);
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
		case addId+1:
			currentItem = pager.getCurrentItem();
			temp = 	BasketSession.getUser().getBaskets().get(currentItem);
			BasketSession.getUser().getBaskets().remove(currentItem);
			spiceManager.start(BasketActivity.this);					
			RemoveBasketRequest JsonSpringAndroidRequest = new RemoveBasketRequest(temp);
			spiceManager.execute(JsonSpringAndroidRequest, "Basket_Update", DurationInMillis.ALWAYS_EXPIRED, new DeleteBasketListener());
			pager.getAdapter().notifyDataSetChanged();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		menu.add(group1Id, addId, addId, "Add Basket").setIcon(R.drawable.plus_sign);
		menu.add(group1Id, addId+1,addId+1, "Delete Basket").setIcon(R.drawable.red_x_clipped_rev_1);
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
	private class DeleteBasketListener implements RequestListener<Boolean>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {
			
			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {
				
				Toast.makeText(BasketActivity.this, "Basket could not be removed", Toast.LENGTH_SHORT).show();
			}
			BasketSession.getUser().getBaskets().add(currentItem, temp);
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
