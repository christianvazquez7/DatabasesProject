package com.basket.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.basket.containers.BasketSession;
import com.basket.general.BidEvent;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.icom.dbclass.R;
import com.basket.restrequest.DeleteBidEventRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class BidWinActivity extends Activity {
	private Button mAccept, mDecline;
	private int position;
	private SpiceManager spiceManager= new SpiceManager(CarJsonSpringAndroidSpiceService.class);
	private BidEvent e;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bid_win);
		position = this.getIntent().getIntExtra("itemClicked", 0);
		mAccept = (Button) findViewById(R.id.acceptBid);
		mAccept.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(BidWinActivity.this, "Bid accepted", Toast.LENGTH_LONG).show();;
				removeBid(1);
			}
		});
		mDecline = (Button) findViewById(R.id.declineBid);
		mDecline.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(BidWinActivity.this, "Bid declined", Toast.LENGTH_LONG).show();;
				removeBid(0);
			}
		});
		
		
		 e = BasketSession.getUser().getCurrentlySellingOnBid().get(position);
		

		((TextView)this.findViewById(R.id.bidproduct)).setText(((BidEvent) e).getBidTitle());
		((TextView)this.findViewById(R.id.winnah)).setText(((BidEvent) e).getWinningBid().getBidder());

		if (((BidEvent) e).getWinningBid()!=null)
		((TextView)this.findViewById(R.id.bidprice)).setText("$"+Double.toString(((BidEvent) e).getWinningBid().getAmmount()));
		else
			((TextView)this.findViewById(R.id.bidprice)).setText("$"+Double.toString(((BidEvent) e).getMinBid()));

		((TextView)this.findViewById(R.id.bidsupplier)).setText(((BidEvent) e).getProduct().getManufacturer());
		((TextView)this.findViewById(R.id.pp)).setText(((BidEvent) e).getProduct().getName());
		 final RatingBar minimumRating = (RatingBar)this.findViewById(R.id.bidratingBar1);
		    minimumRating.setRating(e.getRating());
		    ((TextView)this.findViewById(R.id.textView1)).setText(((BidEvent) e).getWinningBid().getBidder());
		    ((TextView)this.findViewById(R.id.pricemybasket)).setText(Double.toString(((BidEvent) e).getWinningBid().getAmmount()));

		    
	
	}

	protected void removeBid(int accepted) {
		if(!spiceManager.isStarted()){
			spiceManager.start(BidWinActivity.this);
		}
		DeleteBidEventRequest JsonSpringAndroidRequest;
		if (accepted==1)
		JsonSpringAndroidRequest = new DeleteBidEventRequest(e.getId(),1,e.getWinningBid().getBidder());
		else
			JsonSpringAndroidRequest = new DeleteBidEventRequest(e.getId(),0,e.getWinningBid().getBidder());

		spiceManager.execute(JsonSpringAndroidRequest, "del_bid", DurationInMillis.ALWAYS_EXPIRED, new BidDelListener());


	}
	private class BidDelListener implements RequestListener<Boolean>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) 
			{
				Toast.makeText(BidWinActivity.this, "User could not be accepted", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(Boolean CreatedUser) 
		{
			Toast.makeText(BidWinActivity.this, "Bid accepted", Toast.LENGTH_SHORT).show();
			BasketSession.getUser().getCurrentlySellingOnBid().remove(position);
			spiceManager.shouldStop();
			BidWinActivity.this.finish();
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bid_win, menu);
		return true;
	}

}
