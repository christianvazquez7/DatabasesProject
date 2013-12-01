package com.example.basket;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.basket.containers.BasketSession;
import com.basket.general.BidEvent;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.Review;
import com.basket.restrequest.AddReviewRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class ReviewActivity extends Activity {

	private SpiceManager spiceManager = new SpiceManager(CarJsonSpringAndroidSpiceService.class);
	private boolean forBid;
	private int forEventId;
	private int withProduct;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_review);
		forEventId = this.getIntent().getIntExtra("id", 0);
		forBid = this.getIntent().getBooleanExtra("bid", false);
		withProduct=this.getIntent().getIntExtra("pro", 0);

		Button reviewIt = (Button) this.findViewById(R.id.createBuyEvent);
		reviewIt.setOnClickListener(new OnClickListener()
		{

			
			public void onClick(View v)
			{
				if(!spiceManager.isStarted())
				{
				spiceManager.start(ReviewActivity.this);
				String title;
				String content;
				float rating;
				
				TextView t = (TextView)findViewById(R.id.buyEventProductNameET);
				title =t.getText().toString();
				
				TextView c = (TextView) findViewById(R.id.buyEventProductFeatures);
				content= c.getText().toString();
				
				 final RatingBar minimumRating = (RatingBar)findViewById(R.id.ratingbar);
				 rating= minimumRating.getRating();
				 
				 Review newReview = new Review();
				 newReview.setContent(content);
				 newReview.setRrating(rating);
				 newReview.setTitle(title);
				 newReview.setUsername(BasketSession.getUser().getUsername());
				 
				 AddReviewRequest postReview = new AddReviewRequest(newReview,BasketSession.getUser().getUsername(),forEventId,forBid,withProduct);
				 spiceManager.execute(postReview,"", DurationInMillis.ALWAYS_EXPIRED, new PReviewListener());
				}
				
			}
			
		});
	}
	private class PReviewListener implements RequestListener<Boolean>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0)
		{

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException))
			{

				Toast.makeText(ReviewActivity.this, "Review Failed", Toast.LENGTH_SHORT).show();
				spiceManager.shouldStop();
			}
			Toast.makeText(ReviewActivity.this, "Review Failed", Toast.LENGTH_SHORT).show();
			if(spiceManager.isStarted())
				spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(Boolean bool) 
		{
			spiceManager.shouldStop();
			Toast.makeText(ReviewActivity.this, "Review Posted", Toast.LENGTH_SHORT).show();
			finish();
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.review, menu);
		return true;
	}

}
