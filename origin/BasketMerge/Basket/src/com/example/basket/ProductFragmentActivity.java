package com.example.basket;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class ProductFragmentActivity extends SlidingFragmentActivity {

	
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
			
			fragment = new ProductListFragment();
			fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
			
		}
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
	            viewPager.removeView((View) object);
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

}
