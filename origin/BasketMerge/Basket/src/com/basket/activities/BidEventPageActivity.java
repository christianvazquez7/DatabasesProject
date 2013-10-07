package com.basket.activities;


import android.animation.LayoutTransition;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.TabHost;

import com.basket.containers.BasketSession;
import com.basket.fragments.HarvestFragment;
import com.basket.fragments.ProductDetailFragment;
import com.basket.fragments.ProductFragment;
import com.basket.general.BidEvent;
import com.basket.lists.ReviewListFragment;
import com.example.basket.R;

public class BidEventPageActivity extends FragmentActivity {
	private ViewGroup viewGroup;
	private BidEvent currentEvent;
	boolean tab = false;
	Fragment product,fragment,harvest,detail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		currentEvent=(BidEvent) BasketSession.getProductSearch().get(this.getIntent().getIntExtra("selectedEvent", 0));
		
		
		setContentView(R.layout.product_page2);
		LayoutTransition f = new LayoutTransition();
		f.enableTransitionType(LayoutTransition.CHANGING);
		f.setDuration(20);
		LayoutTransition l = new LayoutTransition();
		l.enableTransitionType(LayoutTransition.CHANGING);
		l.setDuration(500);
		viewGroup = (ViewGroup) findViewById(R.id.full);
		viewGroup.setLayoutTransition(l);
		ViewGroup vg =(ViewGroup) findViewById(R.id.tab2);
		// vg.setLayoutTransition(l);
		//vg.setLayoutTransition(f);

		TabHost mTabHost = (TabHost)findViewById(android.R.id.tabhost);
		mTabHost.setup();

		TabHost.TabSpec calculatorTab = mTabHost.newTabSpec("tab1");
		calculatorTab.setContent(R.id.reviews);
		calculatorTab.setIndicator("Reviews");
		
		//	   TabSpec spec = mTabHost.newTabSpec("tab1");
		//       //spec.setIndicator(mTabHost.);
		//       spec.setContent(R.id.fragmentContainer);
		//       mTabHost.addTab(spec);
		
		
		final FragmentManager fm = this.getSupportFragmentManager();
	    fragment = fm.findFragmentById(R.id.reviewFragmentContainer);
		product = fm.findFragmentById(R.id.productContainer);
		harvest=fm.findFragmentById(R.id.tab2);
		detail=fm.findFragmentById(R.id.tab3);
		
		
		//Tab 2
		TabHost.TabSpec doubletab = mTabHost.newTabSpec("tab2");
		doubletab.setContent(R.id.tab2);
		doubletab.setIndicator("Harvest");
		mTabHost.addTab(doubletab);
		
		//Animacion
		final Animation  outAni = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, -1f);
		outAni.setDuration(100);
		outAni.setFillAfter(true);
		
		//Subir y bajar la ventana
		this.findViewById(R.id.metal).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) 
			{
				if(tab)
					fm.beginTransaction().setCustomAnimations(R.anim.slide, R.anim.slide_up).add(R.id.productContainer,product).commit();
				tab=false;

			}

		});
		//Mas de subir y bajar la ventana
		this.findViewById(R.id.productContainer).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) 
			{
				if (!tab){
					//arg0.startAnimation(outAni);
					fm.beginTransaction().setCustomAnimations(R.anim.slide_up, R.anim.slide_down).remove(product).commit();
					//(R.id.productContainer, new BarFragment()).commit();
					//	fm.beginTransaction().setCustomAnimations(R.anim.slide, R.anim.slide).add(R.id.productContainer,new BarFragment()).commit();

					tab=true;
				}
				else{
					ProductFragment temp =new ProductFragment();
					temp.setEvent(currentEvent);
					fm.beginTransaction().setCustomAnimations(R.anim.slide, R.anim.slide).replace(R.id.productContainer, temp).commit();
					tab=false;
				}
			}

		});
		//View of product
		if (product == null)
		{
			product = new ProductFragment();
			((ProductFragment)product).setEvent(currentEvent);
			fm.beginTransaction().add(R.id.productContainer, product).commit();
			////			((ReviewListFragment)fragment).getListView().setDivider(this.getResources().getDrawable(R.drawable.custom_divider));
		}
		//Harvest option in tab
		if (harvest == null){
			harvest = new HarvestFragment();
			fm.beginTransaction().add(R.id.tab2, harvest).commit();
		}
		//Prdouct details in tab
		if (detail == null){
			detail = new ProductDetailFragment();
			ProductDetailFragment sp=(ProductDetailFragment)detail;
			sp.setEvent(currentEvent);
			fm.beginTransaction().add(R.id.tab3, detail).commit();

		}
		//Info Tab
		TabHost.TabSpec Infotab = mTabHost.newTabSpec("tab3");
		Infotab.setContent(R.id.tab3);
		Infotab.setIndicator("Info");
		mTabHost.addTab(Infotab);
		mTabHost.addTab(calculatorTab);
		if (fragment == null)
		{

			fragment = new ReviewListFragment();
			//			
			fm.beginTransaction().add(R.id.reviewFragmentContainer, fragment).commit();
			////			((ReviewListFragment)fragment).getListView().setDivider(this.getResources().getDrawable(R.drawable.custom_divider));
		}
		this.getActionBar().setDisplayShowTitleEnabled(false);
		this.getActionBar().setDisplayShowHomeEnabled(false);
		//Add to basket button
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product, menu);
		return true;
	}
	
}
