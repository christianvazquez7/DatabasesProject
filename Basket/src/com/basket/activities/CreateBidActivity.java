package com.basket.activities;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.basket.containers.BasketSession;
import com.basket.general.BidEvent;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.Category;
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
	private EditText prodName, prodPrice, stopDate, prodFeat, prodDescription, prodW, prodH, prodD, prodMan, eM,eD,eY;
	private Button mCreateBuyEventButton;
	private SpiceManager spiceManager= new SpiceManager(CarJsonSpringAndroidSpiceService.class);
	private int M, D ,Y;
	private Spinner mSpinner;
	private EditText eventName;
	private EditText productId;
	private EditText dimensions;
	private EditText minBid;
	private DatePicker date;
	private TimePicker time;
	private byte[] blob;
	private static int RESULT_LOAD_IMAGE = 1;



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


		eventName= (EditText) findViewById(R.id.eventName);
		productId= (EditText) findViewById(R.id.productId);
		dimensions= (EditText) findViewById(R.id.dimensions);
		minBid= (EditText) findViewById(R.id.minBid);

		date= (DatePicker) findViewById(R.id.datePicker1);
		time= (TimePicker) findViewById(R.id.timePicker1);


		mSpinner=(Spinner) this.findViewById(R.id.spinner1);
		ArrayList<String>cats=new ArrayList<String>();
		for (Category c: BasketSession.getCategory()){
			if (c.getParent()!=null)
			{
				cats.add(c.getName());
			}
		}


		ArrayAdapter<String> catNameAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item,cats);
		catNameAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(catNameAdapter);


		final BidEvent newBidEvent = new BidEvent();
		final Product prod = new Product();

		mCreateBuyEventButton= (Button) findViewById(R.id.createBuyEvent);
		mCreateBuyEventButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(blob==null)
				{
					Toast.makeText(CreateBidActivity.this, "Please select a Picture for event.", Toast.LENGTH_LONG).show();
					return;
				}
				try{
					M = date.getMonth()+1;

					Y = date.getYear();

					D = date.getDayOfMonth();

					int hour = time.getCurrentHour();
					int minute = time.getCurrentMinute();
					String date = Y+"-"+M+"-"+D+" "+hour+":"+minute+":00";
					prod.setDimensions(dimensions.getText().toString());
					prod.setProductPId(Integer.parseInt(productId.getText().toString()));

					prod.setManufacturer(prodMan.getText().toString());
					prod.setDepth(Integer.parseInt(prodD.getText().toString()));
					prod.setWidth(Integer.parseInt(prodW.getText().toString()));
					prod.setHeight(Integer.parseInt(prodH.getText().toString()));
					prod.setName(prodName.getText().toString());
					newBidEvent.setEndingTime(date);
					newBidEvent.setPicture(blob);
					newBidEvent.setProduct(prod);
					newBidEvent.setDescription(prodDescription.getText().toString());
					newBidEvent.setFeatures(prodFeat.getText().toString());
					newBidEvent.setStartingBid(Double.parseDouble(prodPrice.getText().toString()));
					newBidEvent.setBidTitle(eventName.getText().toString());
					newBidEvent.setRating(BasketSession.getUser().getRating());
					newBidEvent.setMinBid(Double.parseDouble(minBid.getText().toString()));

					BasketSession.getUser().getCurrentlySellingOnBid().add(newBidEvent);
					if(!spiceManager.isStarted()){
						spiceManager.start(CreateBidActivity.this);					
						NewBidSellEventRequest JsonSpringAndroidRequest = new NewBidSellEventRequest(newBidEvent,BasketSession.getUser().getUserId(),(String)mSpinner.getSelectedItem());
						spiceManager.execute(JsonSpringAndroidRequest, "Bid_Sell_Create", DurationInMillis.ALWAYS_EXPIRED, new NewBidEventSellListner());}
				}
				catch (NumberFormatException e){
					Toast.makeText(CreateBidActivity.this, "Wrong input on dimensions or in price.  Make sure its a number", Toast.LENGTH_LONG).show();;
				}

			}
		});


		this.findViewById(R.id.selpic).setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) 
			{
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}

		});
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			ByteArrayOutputStream outStr = new ByteArrayOutputStream();
			Bitmap picture = BitmapFactory.decodeFile(picturePath);
			picture.compress(Bitmap.CompressFormat.JPEG, 100, outStr);
			blob = outStr.toByteArray();	            
		}


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
