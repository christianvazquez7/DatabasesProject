package com.example.basket;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class HomePageActivity extends Activity {

	ViewFlipper mVFlipper1, mVFlipper2;
//	ImageView[] mIVDeal, mIVRecom;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		
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
		
		mVFlipper1 = (ViewFlipper) findViewById(R.id.vfDeals);
		mVFlipper2 = (ViewFlipper) findViewById(R.id.vfRecom);
		
		mVFlipper1.setFlipInterval(3000);
		mVFlipper2.setFlipInterval(3000);
		
		mVFlipper1.startFlipping();
		mVFlipper2.startFlipping();

	}
}

