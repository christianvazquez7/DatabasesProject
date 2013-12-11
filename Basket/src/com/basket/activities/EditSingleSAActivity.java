package com.basket.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basket.containers.BasketSession;
import com.basket.general.Adress;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.User;
import com.basket.restrequest.InsertCreditCardRequest;
import com.basket.restrequest.InsertShipAddRequest;
import com.basket.restrequest.UpdateAddressRequest;
import com.example.basket.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class EditSingleSAActivity extends Activity {

	private int selectedUser, selectedShipAdd;
	private int zipcode;
	private String line1, line2, city, state, country;

	private User theUser;
	private Adress theAddress, oldAddress;
	private SpiceManager spiceManager= new SpiceManager(CarJsonSpringAndroidSpiceService.class);

	private Button mSaveButton, mCancelButton;
	private EditText mLine1, mLine2, mCity, mState, mZipcode, mCountry;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_single_sa);
		selectedUser = this.getIntent().getIntExtra("selectedUser", 0);
		selectedShipAdd = this.getIntent().getIntExtra("selectedShipAdd", 0);

		theUser = BasketSession.getUser();
		theAddress = new Adress();
		oldAddress = theUser.getShippingAdress().get(selectedShipAdd);
		if(this.getIntent().getBooleanExtra("createdNewAddress", false))
		{
			mLine1 = (EditText) findViewById(R.id.etLine1SingleSA);
			mLine2 = (EditText) findViewById(R.id.etLine2SingleSA);
			mCity = (EditText) findViewById(R.id.etCitySingleSA);
			mState = (EditText) findViewById(R.id.etStateSingleSA);
			mZipcode = (EditText) findViewById(R.id.etZipcodeSingleSA);
			mCountry = (EditText) findViewById(R.id.etCountrySingleSA);
		}
		else{
			mLine1 = (EditText) findViewById(R.id.etLine1SingleSA);
			mLine1.setText(oldAddress.getLine1());
			mLine2 = (EditText) findViewById(R.id.etLine2SingleSA);
			mLine2.setText(oldAddress.getLine2());
			mCity = (EditText) findViewById(R.id.etCitySingleSA);
			mCity.setText(oldAddress.getCity());
			mState = (EditText) findViewById(R.id.etStateSingleSA);
			mState.setText(oldAddress.getState());
			mZipcode = (EditText) findViewById(R.id.etZipcodeSingleSA);
			mZipcode.setText(Integer.toString(oldAddress.getZipCode()));
			mCountry = (EditText) findViewById(R.id.etCountrySingleSA);
			mCountry.setText(oldAddress.getCountry());
		}
		mSaveButton = (Button) findViewById(R.id.bSaveSingleSA);
		mSaveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(EditSingleSAActivity.this.getIntent().getStringExtra("createdNewAddress") == null){
					try{
						theAddress.setLine1(mLine1.getText().toString());
						theAddress.setLine2(mLine2.getText().toString());
						theAddress.setCity(mCity.getText().toString());
						theAddress.setState(mState.getText().toString());
						theAddress.setZipCode(Integer.parseInt(mZipcode.getText().toString()));
						theAddress.setCountry(mCountry.getText().toString());

					}
					catch(NumberFormatException e){
						Toast.makeText(EditSingleSAActivity.this, "Error in the zip code", Toast.LENGTH_SHORT);
					}
					if(!spiceManager.isStarted()){
						spiceManager.start(EditSingleSAActivity.this);
						UpdateAddressRequest updateReq = new UpdateAddressRequest(theAddress, theUser, oldAddress);
						spiceManager.execute(updateReq, "user_edit", DurationInMillis.ALWAYS_EXPIRED, new AddressUpdateListner());
					}
				}
				else{
					try{
						theAddress.setLine1(mLine1.getText().toString());
						theAddress.setLine2(mLine2.getText().toString());
						theAddress.setCity(mCity.getText().toString());
						theAddress.setState(mState.getText().toString());
						theAddress.setZipCode(Integer.parseInt(mZipcode.getText().toString()));
						theAddress.setCountry(mCountry.getText().toString());

					}
					catch(NumberFormatException e){
						Toast.makeText(EditSingleSAActivity.this, "Error in the zip code", Toast.LENGTH_SHORT);
					}
					if(!spiceManager.isStarted()){
						spiceManager.start(EditSingleSAActivity.this);
						InsertShipAddRequest updateReq = new InsertShipAddRequest(theAddress, theUser);
						spiceManager.execute(updateReq, "user_edit", DurationInMillis.ALWAYS_EXPIRED, new InsertShipAddListner());
					}
				}

			}
		});

		mCancelButton =  (Button) findViewById(R.id.bCancelSingleSA);
		mCancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(EditSingleSAActivity.this.getIntent().getStringExtra("createdNewAddress")!=null){

					if(EditSingleSAActivity.this.getIntent().getStringExtra("createdNewAddress").equals("true"));
					{
						BasketSession.getUser().getShippingAdress().remove(selectedShipAdd);
					}
				}
				EditSingleSAActivity.this.finish();
			}
		});



	}
	@Override
	public void onBackPressed(){
		if(EditSingleSAActivity.this.getIntent().getStringExtra("createdNewAddress")!=null){
			if(EditSingleSAActivity.this.getIntent().getStringExtra("createdNewAddress").equals("true"));
			{
				BasketSession.getUser().getShippingAdress().remove(selectedShipAdd);
			}
		}
		super.onBackPressed();
	}
	private class AddressUpdateListner implements RequestListener<Boolean>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {
			Toast.makeText(EditSingleSAActivity.this, "Update Unsuccesful", Toast.LENGTH_SHORT).show();
			if(spiceManager.isStarted())

				spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(Boolean edit) {
			if(spiceManager.isStarted())
				spiceManager.shouldStop();
			theUser.getShippingAdress().set(selectedShipAdd, theAddress);
			finish();
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}
	}
	private class InsertShipAddListner implements RequestListener<String>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {
			Toast.makeText(EditSingleSAActivity.this, "Update Unsuccesful", Toast.LENGTH_SHORT).show();
			if(spiceManager.isStarted())
				spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(String insertId) {
			if(spiceManager.isStarted())
				spiceManager.shouldStop();
			int nid=0;
			boolean problem = false;
			try{
				nid = Integer.parseInt(insertId);
			}
			catch(NumberFormatException e){
				Toast.makeText(EditSingleSAActivity.this, "Update Unsuccesful", Toast.LENGTH_SHORT).show();
				problem = true;
			}
			if(!problem){
				theAddress.setAid(nid);

				theUser.getShippingAdress().set(selectedShipAdd, theAddress);
				Toast.makeText(EditSingleSAActivity.this, "Success adding new address", Toast.LENGTH_LONG).show();

				EditSingleSAActivity.this.finish();
			}
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}
	}
}
