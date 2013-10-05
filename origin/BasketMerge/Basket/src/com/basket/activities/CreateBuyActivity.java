package com.basket.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basket.containers.BasketSession;
import com.basket.general.BuyEvent;
import com.basket.general.Product;
import com.example.basket.R;

public class CreateBuyActivity extends Activity {



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
				
				CreateBuyActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_buy, menu);
		return true;
	}

}
