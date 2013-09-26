
package com.example.basket;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class CategoryPageActivity extends FragmentActivity {

	@Override
	public void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_categorypage);

		FragmentManager fm = this.getFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fcCategoryPage);
		
		if(fragment == null){
			fragment = new CategoryPageProductListFragment();
			fm.beginTransaction().add(R.id.fcCategoryPage, fragment).commit();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product, menu);
		return true;
	}

}
