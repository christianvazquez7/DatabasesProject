package com.basket.activities;


import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.example.basket.R;
import com.example.basket.R.anim;
import com.example.basket.R.dimen;
import com.example.basket.R.drawable;
import com.example.basket.R.id;
import com.example.basket.R.layout;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class HomePageActivity extends Activity {

	private SlidingMenu slidingMenu;
	
	ViewFlipper mVFlipper1, mVFlipper2;
	Animation animIn, animOut;
//	ImageView[] mIVDeal, mIVRecom;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_home_page);
		
		slidingMenu = new SlidingMenu(this);
	
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
        slidingMenu.setShadowDrawable(R.drawable.slidingmenu_shadow);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.slidingmenu);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
		
		/*mIVDeal[0] = (ImageView) findViewById(R.id.iViewDeal_1);
		mIVDeal[1] = (ImageView) findViewById(R.id.iViewDeal_2);
		mIVDeal[2] = (ImageView) findViewById(R.id.iViewDeal_3);
		mIVRecom[0] = (ImageView) findViewById(R.id.iViewRecom_1);
		mIVRecom[1] = (ImageView) findViewById(R.id.iViewRecom_2);
		mIVRecom[2] = (ImageView) findViewById(R.id.iViewRecom_3);
		
		mIVDeal[0].setBackground(this.getResources().getDrawable(R.drawable.laptop_image));
		mIVDeal[1].setBackground(this.getResources().getDrawable(R.drawable.camera_image));
		mIVDeal[2].setBackground(this.getResources().getDrawable(R.drawable.tablet_image));
		mIVRecom[0].setBackground(this.getResources().getDrawable(R.drawable.book_image));
		mIVRecom[1].setBackground(this.getResources().getDrawable(R.drawable.tennis_image));
		mIVRecom[2].setBackground(this.getResources().getDrawable(R.drawable.tv_image));*/
		
        animIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up); 
        animOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down); 
        
        
        
		mVFlipper1 = (ViewFlipper) findViewById(R.id.vfDeals);
		mVFlipper2 = (ViewFlipper) findViewById(R.id.vfRecom);
		
		mVFlipper1.setFlipInterval(3000);
		mVFlipper2.setFlipInterval(3000);
		
		mVFlipper1.startFlipping();
		mVFlipper2.startFlipping();

	}
	
	@Override
    public void onBackPressed() {
        if ( slidingMenu.isMenuShowing()) {
            slidingMenu.toggle();
        }
        else {
            super.onBackPressed();
        }
    }
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_MENU ) {
            this.slidingMenu.toggle();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            this.slidingMenu.toggle();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}

