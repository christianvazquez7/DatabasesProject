package com.basket.activities;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.basket.containers.BasketSession;
import com.basket.general.BuyEvent;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.Category;
import com.basket.general.Product;
import com.basket.icom.dbclass.R;
import com.basket.restrequest.ByteContainer;
import com.basket.restrequest.NewBuySellEventRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class CreateBuyActivity extends Activity {

    private static int RESULT_LOAD_IMAGE = 1;
	private SpiceManager spiceManager= new SpiceManager(CarJsonSpringAndroidSpiceService.class);
	private Spinner mSpinner;


	private EditText prodName, prodPrice, stopDate, prodFeat, prodDescription, prodW, prodH, prodD, prodMan;
	private Button mCreateBuyEventButton;
	private byte[] blob;
	private EditText eventName;
	private EditText productId;
	private EditText dimensions;
	private EditText quantity;
	private String imageEncoded;
	private ByteBuffer buffer;
	private BuyEvent newBuyEvent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_buy);
		
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
		
		eventName= (EditText) findViewById(R.id.eventName);
		productId= (EditText) findViewById(R.id.productId);
		dimensions= (EditText) findViewById(R.id.dimensions);
		quantity= (EditText) findViewById(R.id.Quantity);
		

		prodName = (EditText) findViewById(R.id.buyEventProductNameET);
		prodPrice= (EditText) findViewById(R.id.buyEventProductPrice);
		prodFeat = (EditText) findViewById(R.id.buyEventProductFeatures);
		prodDescription = (EditText) findViewById(R.id.buyEventProductDescription);
		prodW = (EditText) findViewById(R.id.buyEventProductWidth);
		prodH = (EditText) findViewById(R.id.buyEventProductHeight);
		prodD = (EditText) findViewById(R.id.buyEventProductDepth);
		prodMan = (EditText) findViewById(R.id.buyEventProductManufacturer);

		 newBuyEvent = new BuyEvent();
		final Product prod = new Product();

		mCreateBuyEventButton= (Button) findViewById(R.id.createBuyEvent);
		mCreateBuyEventButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				
				int q=0;
				if(blob==null)
				{
					Toast.makeText(CreateBuyActivity.this, "Please select a Picture for event.", Toast.LENGTH_LONG).show();
					return;
				}
				prod.setManufacturer(prodMan.getText().toString());
				try{
					q=Integer.parseInt(quantity.getText().toString());
					prod.setDepth(Integer.parseInt(prodD.getText().toString()));
					prod.setWidth(Integer.parseInt(prodW.getText().toString()));
					prod.setHeight(Integer.parseInt(prodH.getText().toString()));
					prod.setProductPId(Integer.parseInt(productId.getText().toString()));
				}
				catch (NumberFormatException e){
					Toast.makeText(CreateBuyActivity.this, "Wrong input on dimensions make sure its a number", Toast.LENGTH_LONG).show();
				}
				prod.setDimensions(dimensions.getText().toString());
				prod.setDimensions(dimensions.getText().toString());
				
				
				prod.setName(prodName.getText().toString());
				newBuyEvent.setProduct(prod);
				newBuyEvent.setPic(blob);
				newBuyEvent.setBtitle(eventName.getText().toString());
				newBuyEvent.setDescription(prodDescription.getText().toString());
				newBuyEvent.setFeatures(prodFeat.getText().toString());
				try{
					newBuyEvent.setPrice(Double.parseDouble(prodPrice.getText().toString()));
					BasketSession.getUser().getCurrentlySellingOnBuy().add(newBuyEvent);

					spiceManager.start(CreateBuyActivity.this);			
					newBuyEvent.setEndoded(imageEncoded);
					NewBuySellEventRequest JsonSpringAndroidRequest = new NewBuySellEventRequest(newBuyEvent, BasketSession.getUser().getUserId(),q,(String)mSpinner.getSelectedItem(),imageEncoded);
					spiceManager.execute(JsonSpringAndroidRequest, "Bid_Sell_Create", DurationInMillis.ALWAYS_EXPIRED, new NewBuyEventSellListner());

					
				}catch (NumberFormatException e){
					Toast.makeText(CreateBuyActivity.this, "Wrong input on price make sure its number", Toast.LENGTH_LONG).show();
				}

				
			}
		});
		
		this.findViewById(R.id.selectPicture).setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) 
			{
				   Intent i = new Intent(
	                        Intent.ACTION_PICK,
	                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	                 
	                startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
			
		});
	}
	private class NewBuyEventSellListner implements RequestListener<ByteContainer>, RequestProgressListener {

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
		public void onRequestSuccess(ByteContainer bool) 
		{

			newBuyEvent.setId(bool.getValue());
			spiceManager.shouldStop();
			Toast.makeText(CreateBuyActivity.this, "Successfully put product on sale", Toast.LENGTH_SHORT).show();
			CreateBuyActivity.this.finish();
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
	            
//	            
//	            BitmapFactory.Options options = new BitmapFactory.Options();
//
//	            options.inSampleSize = 2;
	             Bitmap picture = BitmapFactory.decodeFile(picturePath);
	             picture.compress(Bitmap.CompressFormat.PNG, 100, outStr);
	             blob = outStr.toByteArray();	 
	 			Log.d("try",blob+" ");
	 			
	 		

	            imageEncoded = Base64.encodeToString(blob,Base64.DEFAULT);
	        }
	     
	     
	    }

}
