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
import com.basket.general.BuyEvent;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.Product;
import com.basket.restrequest.NewBidSellEventRequest;
import com.basket.restrequest.NewBuySellEventRequest;
import com.example.basket.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class CreateBuyActivity extends Activity {


	private SpiceManager spiceManager= new SpiceManager(CarJsonSpringAndroidSpiceService.class);

	private EditText prodName, prodPrice, stopDate, prodFeat, prodDescription, prodW, prodH, prodD, prodMan;
	private Button mCreateBuyEventButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_buy);
		prodName = (EditText) findViewById(R.id.buyEventProductNameET);
		prodPrice= (EditText) findViewById(R.id.buyEventProductPrice);
		prodFeat = (EditText) findViewById(R.id.buyEventProductFeatures);
		prodDescription = (EditText) findViewById(R.id.buyEventProductDescription);
		prodW = (EditText) findViewById(R.id.buyEventProductWidth);
		prodH = (EditText) findViewById(R.id.buyEventProductHeight);
		prodD = (EditText) findViewById(R.id.buyEventProductDepth);
		prodMan = (EditText) findViewById(R.id.buyEventProductManufacturer);

		final BuyEvent newBuyEvent = new BuyEvent();
		final Product prod = new Product();

		mCreateBuyEventButton= (Button) findViewById(R.id.createBuyEvent);
		mCreateBuyEventButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				prod.setManufacturer(prodMan.getText().toString());
				try{
					prod.setDepth(Integer.parseInt(prodD.getText().toString()));
					prod.setWidth(Integer.parseInt(prodW.getText().toString()));
					prod.setHeight(Integer.parseInt(prodH.getText().toString()));
				}
				catch (NumberFormatException e){
					Toast.makeText(CreateBuyActivity.this, "Wrong input on dimensions make sure its a number", Toast.LENGTH_LONG).show();
				}
				prod.setName(prodName.getText().toString());
				newBuyEvent.setProduct(prod);
				newBuyEvent.setDescription(prodDescription.getText().toString());
				newBuyEvent.setFeatures(prodFeat.getText().toString());
				try{
					newBuyEvent.setPrice(Double.parseDouble(prodPrice.getText().toString()));
				}catch (NumberFormatException e){
					Toast.makeText(CreateBuyActivity.this, "Wrong input on price make sure its number", Toast.LENGTH_LONG).show();
				}

				BasketSession.getUser().getCurrentlySellingOnBuy().add(newBuyEvent);
				Toast.makeText(CreateBuyActivity.this, "Added to sell", Toast.LENGTH_LONG).show();;

				spiceManager.start(CreateBuyActivity.this);					
				NewBuySellEventRequest JsonSpringAndroidRequest = new NewBuySellEventRequest(newBuyEvent);
				spiceManager.execute(JsonSpringAndroidRequest, "Bid_Sell_Create", DurationInMillis.ALWAYS_EXPIRED, new NewBuyEventSellListner());

			}
		});
	}
	private class NewBuyEventSellListner implements RequestListener<Boolean>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				Toast.makeText(CreateBuyActivity.this, "Could not put item to buy sale", Toast.LENGTH_SHORT).show();
			}
			BasketSession.getUser().getCurrentlySellingOnBuy().remove(BasketSession.getUser().getCurrentlySellingOnBuy().size()-1);
			spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(Boolean bool) 
		{


			spiceManager.shouldStop();
			Toast.makeText(CreateBuyActivity.this, "Successfully created bid sale", Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_buy, menu);
		return true;
	}

}
