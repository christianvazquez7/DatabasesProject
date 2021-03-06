package com.basket.activities;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.basket.general.Adress;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.CreditCard;
import com.basket.general.User;
import com.basket.restrequest.CreateAdminRequest;
import com.basket.restrequest.CreateUserRequest;
import com.example.basket.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;


public class AdminSignUp extends Activity {

	//ET is for EditText, CC for Credit Card, SA & BA for Shipping and Billing Address, B is for button
	//A is for awesome -Pedro

	private EditText mETFName, mETLName, mETPassword, mETEmail, mETUserName, mETBDayDay, mETBDayMonth, mETBDayYear,
	mETCCName, mETCCNum, mETCCSecCode, mETCCExpMonth, mETCCExpYear,
	mETSALine1, mETSALine2, mETSACity, mETSAState, mETSACountry, mETSAZipCode,
	mETBALine1, mETBALine2, mETBACity, mETBAState, mETBACountry, mETBAZipCode;

	private User newUser;
	private Adress shippingAddress;
	private Adress billingAddress;
	private CreditCard creditCard;

	private String userName, email, password, fName, lName, bDay,
	ccName, ccAddrs,
	saLine1, saLine2, saCity, saState, saCountry,
	baLine1, baLine2, baCity, baState, baCountry;

	private long ccNumber;
	private int ccExpMonth, ccExpYear, ccSecCode,
	saZipCode, baZipCode,
	bdDay, bdMonth, bdYear, age,
	currDay, currMonth, currYear;
	private CheckBox isAdmin;
	private SpiceManager spiceManager  = new SpiceManager(CarJsonSpringAndroidSpiceService.class);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up2);
		isAdmin = (CheckBox) findViewById(R.id.isAdmin);

		Button signUp = (Button) this.findViewById(R.id.signUp);
		signUp.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) 
			{
				if (!spiceManager.isStarted())
				{
					if(!isAdmin.isChecked()){
					try{
						spiceManager.start(AdminSignUp.this);
						initializeUserFields();
						spiceManager.execute(new CreateUserRequest(newUser), new CreateAccountListener());
					}
					catch(NumberFormatException e){
						Toast.makeText(AdminSignUp.this, "Please fix number fields", Toast.LENGTH_LONG).show();;
					}
					}
					else{
						try{
							spiceManager.start(AdminSignUp.this);
							initializeUserFields();
							spiceManager.execute(new CreateAdminRequest(newUser), new CreateAccountListener());
						}
						catch(NumberFormatException e){
							Toast.makeText(AdminSignUp.this, "Please fix number fields", Toast.LENGTH_LONG).show();;
						}
					}
				}
			}
		});
	}

	private boolean initializeUserFields(){

		mETFName = (EditText) findViewById(R.id.etFNameSignUp); 
		this.fName = mETFName.getText().toString();
		mETLName = (EditText) findViewById(R.id.etLNameSignUp); 
		this.lName = mETLName.getText().toString();
		mETPassword = (EditText) findViewById(R.id.passwordSignUp); 
		this.password = mETPassword.getText().toString();
		mETEmail = (EditText) findViewById(R.id.emailSignUp);
		this.email = mETEmail.getText().toString();
		mETUserName = (EditText) findViewById(R.id.userNameSignUp);
		this.userName = mETUserName.getText().toString();


		mETBDayDay = (EditText) findViewById(R.id.etBDayDay);
		mETBDayMonth = (EditText) findViewById(R.id.etBDayMonth);
		mETBDayYear = (EditText) findViewById(R.id.etBDayYear);

		this.bdDay = Integer.parseInt(mETBDayDay.getText().toString());
		this.bdMonth = Integer.parseInt(mETBDayMonth.getText().toString());
		this.bdYear = Integer.parseInt(mETBDayYear.getText().toString());


		mETCCName = (EditText) findViewById(R.id.etCCNameSignUp);
		this.ccName = mETCCName.getText().toString();
		mETCCNum = (EditText) findViewById(R.id.etCCNumSignUp);
		this.ccNumber = Long.parseLong(mETCCNum.getText().toString());
		mETCCSecCode = (EditText) findViewById(R.id.etCCSecCodeSignUp);
		this.ccSecCode = Integer.parseInt(mETCCSecCode.getText().toString());


		mETCCExpMonth = (EditText) findViewById(R.id.etCCExpMonth);
		mETCCExpYear = (EditText) findViewById(R.id.etCCExpYear);

		this.ccExpMonth = Integer.parseInt(mETCCExpMonth.getText().toString());
		this.ccExpYear = Integer.parseInt(mETCCExpYear.getText().toString());

		mETSALine1 = (EditText) findViewById(R.id.etSALine1SignUp);
		this.saLine1 = mETSALine1.getText().toString();
		mETSALine2 = (EditText) findViewById(R.id.etSALine2SignUp);
		this.saLine2 = mETSALine2.getText().toString();
		mETSACity = (EditText) findViewById(R.id.etSACitySignUp);
		this.saCity = mETSACity.getText().toString();
		mETSAState = (EditText) findViewById(R.id.etSAStateSignUp);
		this.saState = mETSAState.getText().toString();
		mETSACountry = (EditText) findViewById(R.id.etSACountrySignUp);
		this.saCountry = mETSACountry.getText().toString();
		mETSAZipCode = (EditText) findViewById(R.id.etSAZipcodeSignUp);
		this.saZipCode = Integer.parseInt(mETSAZipCode.getText().toString());

		mETBALine1 = (EditText) findViewById(R.id.etBALine1SignUp);
		this.baLine1 = mETBALine1.getText().toString();
		mETBALine2 = (EditText) findViewById(R.id.etBALine2SignUp);
		this.baLine2 = mETBALine2.getText().toString();
		mETBACity = (EditText) findViewById(R.id.etBACitySignUp);
		this.baCity = mETBACity.getText().toString();
		mETBAState = (EditText) findViewById(R.id.etBAStateSignUp);
		this.baState = mETBAState.getText().toString();
		mETBACountry = (EditText) findViewById(R.id.etBACountrySignUp);
		this.baCountry = mETBACountry.getText().toString();
		mETBAZipCode = (EditText) findViewById(R.id.etBAZipcodeSignUp);
		this.baZipCode = Integer.parseInt(mETBAZipCode.getText().toString());

		this.shippingAddress = new Adress(saLine1, saLine2, saCity, saState, saZipCode, saCountry);
		this.billingAddress = new Adress(baLine1, baLine2, baCity, baState, baZipCode, baCountry);
		this.creditCard = new CreditCard(ccName, billingAddress, ccNumber, ccExpMonth, ccExpYear);
		this.creditCard.setBilling(billingAddress);

		this.newUser = new User(this.userName, this.email,this.password, 
				this.fName, this.lName, this.bdDay, this.bdMonth, this.bdYear);

		this.newUser.getShippingAdress().add(shippingAddress);
		this.newUser.getCreditCards().add(creditCard);

		return true;


	}
	private class CreateAccountListener implements RequestListener<Boolean>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) 
			{

				Toast.makeText(AdminSignUp.this, "User could not be created", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(Boolean CreatedUser) 
		{
			Toast.makeText(AdminSignUp.this, "User created", Toast.LENGTH_SHORT).show();
			spiceManager.shouldStop();
			AdminSignUp.this.finish();
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}


	}
}
