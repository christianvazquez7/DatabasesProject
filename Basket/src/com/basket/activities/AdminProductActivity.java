package com.basket.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.basket.containers.AdminSession;
import com.basket.containers.EventList;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.Event;
import com.basket.general.Product;
import com.basket.general.ProductList;
import com.basket.lists.AdminProductListFragment;
import com.basket.restrequest.AdminProductSearchRequest;
import com.basket.restrequest.ProductSearchRequest;
import com.example.basket.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class AdminProductActivity extends SlidingFragmentActivity {

	private SpiceManager spiceManager = new SpiceManager(CarJsonSpringAndroidSpiceService.class);
	private AdminProductListFragment productList;
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
			fragment = new AdminProductListFragment();
			productList= (AdminProductListFragment) fragment;
			fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();	
		}

		Button search = (Button)findViewById(R.id.searchButton);
		search.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				if (!spiceManager.isStarted())
				{
					productList.clear();
					spiceManager.start(AdminProductActivity.this);
					String searchQuery = ((TextView)findViewById(R.id.searchBar)).getText().toString();
					AdminProductSearchRequest JsonSpringAndroidRequest = new AdminProductSearchRequest(searchQuery);
//					ProductSearchRequest JsonSpringAndroidRequest = new ProductSearchRequest(searchQuery);
					spiceManager.execute(JsonSpringAndroidRequest, "product_search", DurationInMillis.ALWAYS_EXPIRED, new ProductSearchListener());
				}

			}

		});

		this.getActionBar().setDisplayShowTitleEnabled(false);
		this.getActionBar().setDisplayShowHomeEnabled(false);


	}

	private class ProductSearchListener implements RequestListener<ProductList>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				Toast.makeText(AdminProductActivity.this, "Search could not be processed", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
		}


		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{


		}

		@Override
		public void onRequestSuccess(ProductList arg0) {
			
			if(AdminSession.getEventsList()!=null && AdminSession.getEventsList().size()>0)
				AdminSession.getEventsList().clear();
			for(Product p : arg0.getResults()){
				AdminSession.getProducts().add(p);
			}
			((ArrayAdapter<Event>)productList.getListAdapter()).notifyDataSetChanged();
			spiceManager.shouldStop();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product, menu);
		return true;
	}
	public boolean isHandlingRequest() {
		// TODO Auto-generated method stub
		return spiceManager.isStarted();
	}

	public void stopSpice()
	{
		// TODO Auto-generated method stub
		spiceManager.cancelAllRequests();
		spiceManager.shouldStop();
	}


}