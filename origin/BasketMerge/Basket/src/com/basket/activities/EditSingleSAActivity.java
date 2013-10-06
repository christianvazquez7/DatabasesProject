package com.basket.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basket.containers.AdminSession;
import com.basket.containers.BasketSession;
import com.basket.general.Adress;
import com.basket.general.User;
import com.example.basket.R;

public class EditSingleSAActivity extends Activity {

	private int selectedUser, selectedShipAdd;
	private int zipcode;
	private String line1, line2, city, state, country;

	private User theUser;
	private Adress theAddress;

	private Button mSaveButton, mCancelButton;
	private EditText mLine1, mLine2, mCity, mState, mZipcode, mCountry;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_single_sa);
		selectedUser = this.getIntent().getIntExtra("selectedUser", 0);
		selectedShipAdd = this.getIntent().getIntExtra("selectedShipAdd", 0);

		theUser = BasketSession.getUser();
		theAddress = theUser.getShippingAdress().get(selectedShipAdd);
		if(this.getIntent().getBooleanExtra("createdNewAddress", false)){
			mLine1 = (EditText) findViewById(R.id.etLine1SingleSA);
			mLine2 = (EditText) findViewById(R.id.etLine2SingleSA);
			mCity = (EditText) findViewById(R.id.etCitySingleSA);
			mState = (EditText) findViewById(R.id.etStateSingleSA);
			mZipcode = (EditText) findViewById(R.id.etZipcodeSingleSA);
			mCountry = (EditText) findViewById(R.id.etCountrySingleSA);
		}
		else{
			mLine1 = (EditText) findViewById(R.id.etLine1SingleSA);
			mLine1.setText(theAddress.getLine1());
			mLine2 = (EditText) findViewById(R.id.etLine2SingleSA);
			mLine2.setText(theAddress.getLine2());
			mCity = (EditText) findViewById(R.id.etCitySingleSA);
			mCity.setText(theAddress.getCity());
			mState = (EditText) findViewById(R.id.etStateSingleSA);
			mState.setText(theAddress.getState());
			mZipcode = (EditText) findViewById(R.id.etZipcodeSingleSA);
			mZipcode.setText(Integer.toString(theAddress.getZipCode()));
			mCountry = (EditText) findViewById(R.id.etCountrySingleSA);
			mCountry.setText(theAddress.getCountry());
		}
		mSaveButton = (Button) findViewById(R.id.bSaveSingleSA);
		mSaveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try{
					theAddress.setLine1(mLine1.getText().toString());
					theAddress.setLine2(mLine2.getText().toString());
					theAddress.setCity(mCity.getText().toString());
					theAddress.setState(mState.getText().toString());
					theAddress.setZipCode(Integer.parseInt(mZipcode.getText().toString()));
					theAddress.setCountry(mCountry.getText().toString());
					theUser.getShippingAdress().set(selectedShipAdd, theAddress);
					Toast.makeText(EditSingleSAActivity.this, "Added address", Toast.LENGTH_SHORT).show();;
					finish();
				}
				catch(NumberFormatException e){
					Toast.makeText(EditSingleSAActivity.this, "Error in the zip code", Toast.LENGTH_SHORT);
				}
			}
		});

		mCancelButton =  (Button) findViewById(R.id.bCancelSingleSA);
		mCancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(EditSingleSAActivity.this.getIntent().getBooleanExtra("createdNewAddress", false));
				{
					BasketSession.getUser().getShippingAdress().remove(selectedShipAdd);
				}
				EditSingleSAActivity.this.finish();
			}
		});



	}

}
