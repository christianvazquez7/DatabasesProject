package com.basket.activities;

import java.util.List;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.basket.containers.BasketSession;
import com.basket.containers.EventList;
import com.basket.general.BuyEvent;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.Event;
import com.basket.lists.ProductListFragment;
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

public class ProductFragmentActivity extends SlidingFragmentActivity {

	private SpiceManager spiceManager = new SpiceManager(CarJsonSpringAndroidSpiceService.class);
	private ProductListFragment productList;
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
		
		
		spiceManager.addListenerIfPending(EventList.class, "products_search", new ProductSearchListener());
		
		if (fragment == null)
		{
			productList = new ProductListFragment();
			fragment = productList;

			fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
		}
		
		
		((Button)this.findViewById(R.id.searchButton)).setOnClickListener(new OnClickListener(){

			
			public void onClick(View arg0)
			{
				if (!spiceManager.isStarted()){
				productList.clear();
				spiceManager.start(ProductFragmentActivity.this);
				String searchQuery = ((TextView)findViewById(R.id.searchBar)).getText().toString();
				ProductSearchRequest JsonSpringAndroidRequest = new ProductSearchRequest(searchQuery);
				spiceManager.execute(JsonSpringAndroidRequest, "product_search", DurationInMillis.ALWAYS_EXPIRED, new ProductSearchListener());
			
				}
			}
			
		});
		
		


		this.getActionBar().setDisplayShowTitleEnabled(false);
		this.getActionBar().setDisplayShowHomeEnabled(false);
		
		class MenuPagerAdapter extends PagerAdapter {

	        public Object instantiateItem(View collection, int position) {

	            int resId = 0;
	            switch (position) {
	            case 0:
	                resId = R.id.page_one;
	                break;
	            case 1:
	                resId = R.id.page_two;
	                break;
	            case 2:
	                resId = R.id.page_two;
	                break;
	            case 3:
	                resId = R.id.page_three;
	                break;
	            case 4:
	                resId = R.id.page_four;
	                break;
	            case 5:
	                resId = R.id.page_five;
	                break;
	            case 6:
	                resId = R.id.page_six;
	                break;
	            case 7:
	                resId = R.id.page_seven;
	                break;
	            
	            }
	            return findViewById(resId);
	        }

	        @Override
	        public int getCount() 
	        {
	            return 7;
	        }
	        @Override
	        public void destroyItem(View collection, int position, Object view) {
	            // ((ViewPager) collection).removeView((View) view);
	             notifyDataSetChanged();
	        }
	        @Override
	        public boolean isViewFromObject(View arg0, Object arg1) 
	        {
	            return arg0 == ((View) arg1);
	        }
//	        public int getItemPosition(Object object) {
//	            return POSITION_NONE;
//	        }
	    }



	    // Set the ViewPager adapter
	    MenuPagerAdapter adapter = new MenuPagerAdapter();
	    ViewPager pager = (ViewPager) findViewById(R.id.pager);
	    pager.setAdapter(adapter);
	    
	  
	    
	    
	    class OrderPagerAdapter extends PagerAdapter {

	        public Object instantiateItem(View collection, int position) {

	            int resId = 0;
	            switch (position) {
	            case 0:
	                resId = R.id.decreasing;
	                break;
	            case 1:
	                resId = R.id.increasing;
	                break;
	            }
	            return findViewById(resId);
	        }

	        @Override
	        public int getCount() 
	        {
	            return 2;
	        }

	        @Override
	        public boolean isViewFromObject(View arg0, Object arg1) 
	        {
	            return arg0 == ((View) arg1);
	        }
	    }
	    class ByPagerAdapter extends PagerAdapter {

	        public Object instantiateItem(View collection, int position) {

	            int resId = 0;
	            switch (position) {
	            case 0:
	                resId = R.id.price_filter;
	                break;
	            case 1:
	                resId = R.id.brand_filter;
	                break;
	            case 2:
	            	resId = R.id.name_filter;
	                break;
	            }
	            
	            return findViewById(resId);
	        }
	        public void destroyItem(ViewGroup viewPager, int position, Object object) {
	        	
	            this.notifyDataSetChanged();
	        }
	        @Override
	        public int getCount() 
	        {
	            return 3;
	        }

	        @Override
	        public boolean isViewFromObject(View arg0, Object arg1) 
	        {
	            return arg0 == ((View) arg1);
	        }
	    }
	    OrderPagerAdapter adapter2 = new OrderPagerAdapter();
	    ViewPager pager2 = (ViewPager) findViewById(R.id.pager4);
	    pager2.setAdapter(adapter2);
	    
		  ByPagerAdapter adapter3 = new ByPagerAdapter();
		    ViewPager pager3 = (ViewPager) findViewById(R.id.pager3);
		    pager3.setAdapter(adapter3);
	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product, menu);
		return true;
	}
	private class ProductSearchListener implements RequestListener<EventList>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {
			
			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {
				
				Toast.makeText(ProductFragmentActivity.this, "Search could not be processed", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
		}

		public void onRequestSuccess(EventList events) {

			
			BasketSession.getProductSearch().clear();
			for(Event e: events.getBuyEvents())
			BasketSession.getProductSearch().add(e);
			for (Event e:events.getBidEvents())
			BasketSession.getProductSearch().add(e);
			((ArrayAdapter)productList.getListAdapter()).notifyDataSetChanged();
			spiceManager.shouldStop();
				
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{
		
		}


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