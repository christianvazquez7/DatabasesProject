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
import com.basket.general.CreditCard;
import com.basket.general.User;
import com.basket.icom.dbclass.R;
import com.basket.restrequest.InsertCreditCardRequest;
import com.basket.restrequest.UpdateCreditCardRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class EditSingleCCActivity extends Activity {
	private int selectedUser, selectedCreditCard;
	private User theUser;
	private Adress theAddress;
	private CreditCard theCreditCard, oldCard;

	private Button mSaveButton, mCancelButton;
	private EditText mLine1, mLine2, mCity, mState, mZipcode, mCountry, 
	mCCName, mCCNumber, mCCExpMonth, mCCExpYear, mCCSecCode;

	private SpiceManager spiceManager= new SpiceManager(CarJsonSpringAndroidSpiceService.class);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//TODO layout
		setContentView(R.layout.activity_edit_single_cc);
		selectedUser = this.getIntent().getIntExtra("selectedUser", 0);
		selectedCreditCard = this.getIntent().getIntExtra("selectedCreditCard", 0);

		theUser = BasketSession.getUser();
		oldCard = theUser.getCreditCards().get(selectedCreditCard);
		theCreditCard = new CreditCard();
		theAddress = oldCard.getBilling();
		if(EditSingleCCActivity.this.getIntent().getBooleanExtra("createdNewCard", false)){
			theAddress = new Adress();
			mCCName = (EditText) findViewById(R.id.etNameSingleCC); 
			mCCNumber = (EditText) findViewById(R.id.etNumberSingleCC); 
			mCCSecCode = (EditText) findViewById(R.id.etSecCodeSingleCC); 
			mCCExpMonth = (EditText) findViewById(R.id.etExpMonthSingleCC); 
			mCCExpYear = (EditText) findViewById(R.id.etExpYearSingleCC); 

			mLine1 = (EditText) findViewById(R.id.etLine1SingleBA);
			mLine2 = (EditText) findViewById(R.id.etLine2SingleBA);
			mCity = (EditText) findViewById(R.id.etCitySingleBA);
			mState = (EditText) findViewById(R.id.etStateSingleBA);
			mZipcode = (EditText) findViewById(R.id.etZipcodeSingleBA);
			mCountry = (EditText) findViewById(R.id.etCountrySingleBA);
		}
		else
		{		
			mCCName = (EditText) findViewById(R.id.etNameSingleCC); 
			mCCName.setText(oldCard.getName());
			mCCNumber = (EditText) findViewById(R.id.etNumberSingleCC); 
			mCCNumber.setText(Long.toString(oldCard.getCardNum()));
			mCCSecCode = (EditText) findViewById(R.id.etSecCodeSingleCC); 
			mCCSecCode.setText(Integer.toString(oldCard.getSecCode()));
			mCCExpMonth = (EditText) findViewById(R.id.etExpMonthSingleCC); 
			mCCExpMonth.setText(Integer.toString(oldCard.getExpMonth()));
			mCCExpYear = (EditText) findViewById(R.id.etExpYearSingleCC); 
			mCCExpYear.setText(Integer.toString(oldCard.getExpYear()));		

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
		}
		mSaveButton = (Button) findViewById(R.id.bSaveSingleCC);
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
					theCreditCard.setName(mCCName.getText().toString());
					theCreditCard.setCardNum(Long.parseLong(mCCNumber.getText().toString()));
					theCreditCard.setSecCode(Integer.parseInt(mCCSecCode.getText().toString()));
					theCreditCard.setExpDay(Integer.parseInt(mCCExpMonth.getText().toString()));
					theCreditCard.setExpYear(Integer.parseInt(mCCExpYear.getText().toString()));
					theCreditCard.setBilling(theAddress);
					if(EditSingleCCActivity.this.getIntent().getBooleanExtra("editCard", false)){
						if(!spiceManager.isStarted()){
							spiceManager.start(EditSingleCCActivity.this);
							UpdateCreditCardRequest updateReq = new UpdateCreditCardRequest(theCreditCard, theUser, oldCard);
							spiceManager.execute(updateReq, "user_edit", DurationInMillis.ALWAYS_EXPIRED, new CreditUpdateListner());
						}
					}
					else{
						if(!spiceManager.isStarted()){
							spiceManager.start(EditSingleCCActivity.this);
							InsertCreditCardRequest updateReq = new InsertCreditCardRequest(theCreditCard, theUser);
							spiceManager.execute(updateReq, "user_edit", DurationInMillis.ALWAYS_EXPIRED, new InsertCreditCardListner());
						}
					}
					//Toast.makeText(EditSingleCCActivity.this, "Added credit card", Toast.LENGTH_SHORT).show();

				}
				catch(NumberFormatException e){
					Toast.makeText(EditSingleCCActivity.this, "Problem with seccode or exp month or exp year or cardnum", Toast.LENGTH_SHORT).show();;
				}

			}
		});

		mCancelButton =  (Button) findViewById(R.id.bCancelSingleCC);
		mCancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(EditSingleCCActivity.this.getIntent().getBooleanExtra("createdNewCard", false)){
					BasketSession.getUser().getCreditCards().remove(selectedCreditCard);
				}
				EditSingleCCActivity.this.finish();
			}
		});



	}

	@Override
	public void onBackPressed(){
		if(EditSingleCCActivity.this.getIntent().getBooleanExtra("createdNewCard", false)){
			BasketSession.getUser().getCreditCards().remove(selectedCreditCard);
		}
		super.onBackPressed();
	}
	private class CreditUpdateListner implements RequestListener<Boolean>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {
			Toast.makeText(EditSingleCCActivity.this, "Update Unsuccesful", Toast.LENGTH_SHORT).show();
			if(spiceManager.isStarted())
				spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(Boolean edit) {
			if(spiceManager.isStarted())
				spiceManager.shouldStop();
			theUser.getCreditCards().set(selectedCreditCard, theCreditCard);
			Toast.makeText(EditSingleCCActivity.this, "Update successful", Toast.LENGTH_SHORT).show();

			EditSingleCCActivity.this.finish();
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}
	}
	private class InsertCreditCardListner implements RequestListener<String>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {
			Toast.makeText(EditSingleCCActivity.this, "Update Unsuccesful", Toast.LENGTH_SHORT).show();
			if(spiceManager.isStarted())
				spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(String id) {
			if(spiceManager.isStarted())
				spiceManager.shouldStop();
			int nid=0;
			boolean problem = false;
			try{
				nid = Integer.parseInt(id);
			}
			catch(NumberFormatException e){
				Toast.makeText(EditSingleCCActivity.this, "Update Unsuccesful", Toast.LENGTH_SHORT).show();
				problem = true;
			}
			if(!problem){
				theCreditCard.setCardId(nid);
				theUser.getCreditCards().set(selectedCreditCard, theCreditCard);
				Toast.makeText(EditSingleCCActivity.this, "Success adding new credit card", Toast.LENGTH_LONG).show();

				EditSingleCCActivity.this.finish();
			}
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}
	}
}
