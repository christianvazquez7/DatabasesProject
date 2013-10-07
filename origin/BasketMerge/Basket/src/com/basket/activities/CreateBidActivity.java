package com.basket.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basket.containers.BasketSession;
import com.basket.general.BidEvent;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.Product;
import com.basket.restrequest.NewBidSellEventRequest;
import com.example.basket.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class CreateBidActivity extends Activity {
	private EditText prodName, prodPrice, stopDate, prodFeat, prodDescription, prodW, prodH, prodD, prodMan;
	private Button mCreateBuyEventButton;
	private SpiceManager spiceManager= new SpiceManager(CarJsonSpringAndroidSpiceService.class);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_bid);
		prodName = (EditText) findViewById(R.id.buyEventProductNameET);
		prodPrice= (EditText) findViewById(R.id.buyEventProductPrice);
		prodFeat = (EditText) findViewById(R.id.buyEventProductFeatures);
		prodDescription = (EditText) findViewById(R.id.buyEventProductDescription);
		prodW = (EditText) findViewById(R.id.buyEventProductWidth);
		prodH = (EditText) findViewById(R.id.buyEventProductHeight);
		prodD = (EditText) findViewById(R.id.buyEventProductDepth);
		prodMan = (EditText) findViewById(R.id.buyEventProductManufacturer);

		final BidEvent newBidEvent = new BidEvent();
		final Product prod = new Product();

		mCreateBuyEventButton= (Button) findViewById(R.id.createBuyEvent);
		mCreateBuyEventButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try{
					prod.setManufacturer(prodMan.getText().toString());
					prod.setDepth(Integer.parseInt(prodD.getText().toString()));
					prod.setWidth(Integer.parseInt(prodW.getText().toString()));
					prod.setHeight(Integer.parseInt(prodH.getText().toString()));
					prod.setName(prodName.getText().toString());
					newBidEvent.setProduct(prod);
					newBidEvent.setDescription(prodDescription.getText().toString());
					newBidEvent.setFeatures(prodFeat.getText().toString());
					newBidEvent.setMinBid(Double.parseDouble(prodPrice.getText().toString()));
					
					BasketSession.getUser().getCurrentlySellingOnBid().add(newBidEvent);
					
					spiceManager.start(CreateBidActivity.this);					
					NewBidSellEventRequest JsonSpringAndroidRequest = new NewBidSellEventRequest(newBidEvent);
					spiceManager.execute(JsonSpringAndroidRequest, "Bid_Sell_Create", DurationInMillis.ALWAYS_EXPIRED, new NewBidEventSellListner());
				}
				catch (NumberFormatException e){
					Toast.makeText(CreateBidActivity.this, "Wrong input on dimensions or in price.  Make sure its a number", Toast.LENGTH_LONG);
				}
				
			}
		});


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_bid, menu);
		return true;
	}
	private class NewBidEventSellListner implements RequestListener<Boolean>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				Toast.makeText(CreateBidActivity.this, "Could not put item to bid sale", Toast.LENGTH_SHORT).show();
			}
			BasketSession.getUser().getCurrentlySellingOnBid().remove(BasketSession.getUser().getCurrentlySellingOnBid().size()-1);
			spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(Boolean bool) 
		{


			spiceManager.shouldStop();
			Toast.makeText(CreateBidActivity.this, "Successfully created bid sale", Toast.LENGTH_SHORT).show();
			CreateBidActivity.this.finish();
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}
	}
}
