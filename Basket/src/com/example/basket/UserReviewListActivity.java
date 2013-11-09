package com.example.basket;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Window;

import com.basket.lists.BasketListFragment;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class UserReviewListActivity extends FragmentActivity {
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_review_list);
		FragmentManager fm = this.getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		
		if (fragment == null)
		{
			fragment = new RatingListFragment();
			fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
		}
	}



	

}
