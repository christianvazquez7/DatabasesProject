package com.basket.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.basket.containers.AdminSession;
import com.basket.containers.BasketSession;
import com.basket.general.Adress;
import com.basket.general.CreditCard;
import com.basket.general.User;
import com.example.basket.R;

public class EditSingleCCActivity extends Activity {
	private int selectedUser, selectedCreditCard;
	private int zipcode, ccSecCode, ccExpMonth, ccExpYear;
	private long ccNumber;
	private String line1, line2, city, state, country, ccName;
	
	private User theUser;
	private Adress theAddress;
	private CreditCard theCreditCard;
	
	private Button mSaveButton, mCancelButton;
	private EditText mLine1, mLine2, mCity, mState, mZipcode, mCountry, 
		mCCName, mCCNumber, mCCExpMonth, mCCExpYear, mCCSecCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//TODO layout
		setContentView(R.layout.activity_edit_single_cc);
		selectedUser = this.getIntent().getIntExtra("selectedUser", 0);
		selectedCreditCard = this.getIntent().getIntExtra("selectedCreditCard", 0);
		
		theUser = BasketSession.getUser();
		theCreditCard = theUser.getCreditCards().get(selectedCreditCard);
		theAddress = theCreditCard.getBilling();
		
		mCCName = (EditText) findViewById(R.id.etNameSingleCC); 
		mCCName.setText(theCreditCard.getName());
		mCCNumber = (EditText) findViewById(R.id.etNumberSingleCC); 
		mCCNumber.setText(Long.toString(theCreditCard.getCardNum()));
		mCCSecCode = (EditText) findViewById(R.id.etSecCodeSingleCC); 
		mCCSecCode.setText(Integer.toString(theCreditCard.getSecCode()));
		mCCExpMonth = (EditText) findViewById(R.id.etExpMonthSingleCC); 
		mCCExpMonth.setText(Integer.toString(theCreditCard.getExpMonth()));
		mCCExpYear = (EditText) findViewById(R.id.etExpYearSingleCC); 
		mCCExpYear.setText(Integer.toString(theCreditCard.getExpYear()));		
		
		mLine1 = (EditText) findViewById(R.id.etLine1SingleBA);
		mLine1.setText(theAddress.getLine1());
		mLine2 = (EditText) findViewById(R.id.etLine2SingleBA);
		mLine2.setText(theAddress.getLine2());
		mCity = (EditText) findViewById(R.id.etCitySingleBA);
		mCity.setText(theAddress.getCity());
		mState = (EditText) findViewById(R.id.etStateSingleBA);
		mState.setText(theAddress.getState());
		mZipcode = (EditText) findViewById(R.id.etZipcodeSingleBA);
		mZipcode.setText(Integer.toString(theAddress.getZipCode()));
		mCountry = (EditText) findViewById(R.id.etCountrySingleBA);
		mCountry.setText(theAddress.getCountry());
		
		mSaveButton = (Button) findViewById(R.id.bSaveSingleCC);
		mSaveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Go to previous activity
				theAddress.setLine1(mLine1.getText().toString());
				theAddress.setLine2(mLine2.getText().toString());
				theAddress.setCity(mCity.getText().toString());
				theAddress.setState(mState.getText().toString());
				theAddress.setZipCode(Integer.parseInt(mZipcode.getText().toString()));
				theAddress.setCountry(mCountry.getText().toString());
				
				theCreditCard.setName(mCCName.getText().toString());
				theCreditCard.setCardNum(Long.parseLong(mCCNumber.getText().toString()));
				theCreditCard.setBilling(theAddress);
				theCreditCard.setSecCode(Integer.parseInt(mCCSecCode.getText().toString()));
				theCreditCard.setExpDay(Integer.parseInt(mCCExpMonth.getText().toString()));
				theCreditCard.setExpYear(Integer.parseInt(mCCExpYear.getText().toString()));
				
				theUser.getCreditCards().set(selectedCreditCard, theCreditCard);
				finish();
			}
		});
		
		mCancelButton =  (Button) findViewById(R.id.bCancelSingleCC);
		mCancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO go to previous activity
				EditSingleCCActivity.this.finish();
			}
		});
		
		
		
	}
}
