package com.example.basket;


import android.animation.LayoutTransition;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TabHost;

public class BasketInfoPageActivity extends FragmentActivity {
	private ViewGroup viewGroup;
	private boolean tab = false;
	private android.app.Fragment basket,fragment,harvest;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basket_page);
		//Transition into info page
		LayoutTransition f = new LayoutTransition();
		f.enableTransitionType(LayoutTransition.CHANGING);
		f.setDuration(20);
		LayoutTransition l = new LayoutTransition();
		l.enableTransitionType(LayoutTransition.CHANGING);
		l.setDuration(500);


		viewGroup = (ViewGroup) findViewById(R.id.full);
		viewGroup.setLayoutTransition(l);
		ViewGroup vg =(ViewGroup) findViewById(R.id.basketContainer);
		final android.app.FragmentManager fm = this.getFragmentManager();
		basket = fm.findFragmentById(R.id.basketContainer);
		final Animation  outAni = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, -1f);
		outAni.setDuration(100);
		outAni.setFillAfter(true);

		
		if (basket == null)
		{
			basket = new BasketFragment();
			fm.beginTransaction().add(R.id.basketContainer, basket).commit();
		}
		
		if (fragment == null)
		{
			
			fragment = new ProductListFragment();
			fm.beginTransaction().add(R.id.productlistcontainer, fragment).commit();
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product, menu);
		return true;
	}

}
