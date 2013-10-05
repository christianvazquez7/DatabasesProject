package com.basket.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basket.containers.BasketSession;
import com.basket.general.BidEvent;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.Product;
import com.example.basket.R;
import com.octo.android.robospice.SpiceManager;

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
				prod.setManufacturer(prodMan.getText().toString());
				try{
				prod.setDepth(Integer.parseInt(prodD.getText().toString()));
				prod.setWidth(Integer.parseInt(prodW.getText().toString()));
				prod.setHeight(Integer.parseInt(prodH.getText().toString()));
				}
				catch (NumberFormatException e){
					Toast.makeText(CreateBidActivity.this, "Wrong input on dimensions make sure its a number", Toast.LENGTH_LONG);
				}
				prod.setName(prodName.getText().toString());
				newBidEvent.setProduct(prod);
				newBidEvent.setDescription(prodDescription.getText().toString());
				newBidEvent.setFeatures(prodFeat.getText().toString());
				newBidEvent.setMinBid(Double.parseDouble(prodPrice.getText().toString()));
				
				
				BasketSession.getUser().getCurrentlySellingOnBid().add(newBidEvent);
				Toast.makeText(CreateBidActivity.this, "Added to sell", Toast.LENGTH_LONG);
				CreateBidActivity.this.finish();
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_bid, menu);
		return true;
	}

}
