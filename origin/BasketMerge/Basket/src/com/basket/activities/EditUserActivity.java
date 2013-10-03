package com.basket.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basket.containers.AdminSession;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.User;
import com.basket.restrequest.DeleteUserRequest;
import com.basket.restrequest.EditRequest;
import com.example.basket.R;
import com.example.basket.R.id;
import com.example.basket.R.layout;
import com.example.basket.R.menu;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class EditUserActivity extends Activity {

	private Button mBEditCC, mBEditSA;
	
	private User theUser;
	private int user;
	private String buffPass,buffEmail;//buffUser,
//	private String buffSALine1, buffSALine2, buffSACity, buffSAState, buffSACountry,
//		buffBALine1, buffBALine2, buffBACity, buffBAState, buffBACountry,
//		buffCCName;
//	private int buffSAZipcode, buffBAZipcode,
//		buffCCSeccode, buffCCExpdateMonth, buffCCExpdateYear;
//	private long buffCCNum;
	private SpiceManager spiceManager= new SpiceManager(CarJsonSpringAndroidSpiceService.class);
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_edit);
		
		user =this.getIntent().getIntExtra("selectedUser", 0);
		theUser = AdminSession.getEditUsers().get(user);
		
//		buffUser= theUser.getUsername();
		buffPass = theUser.getPassword();
		buffEmail = theUser.getEmail();
		
//		buffSALine1 = theUser.getShippingAdress().get(0).getLine1();
//		buffSALine2 = theUser.getShippingAdress().get(0).getLine2();
//		buffSACity = theUser.getShippingAdress().get(0).getCity();
//		buffSAState = theUser.getShippingAdress().get(0).getState();
//		buffSAZipcode = theUser.getShippingAdress().get(0).getZipCode();
//		buffSACountry = theUser.getShippingAdress().get(0).getCountry();
//		
//		buffBALine1 = theUser.getBillingAdress().get(0).getLine1();
//		buffBALine2 = theUser.getBillingAdress().get(0).getLine2();
//		buffBACity = theUser.getBillingAdress().get(0).getCity();
//		buffBAState = theUser.getBillingAdress().get(0).getState();
//		buffBAZipcode = theUser.getBillingAdress().get(0).getZipCode();
//		buffBACountry = theUser.getBillingAdress().get(0).getCountry();
//		
//		buffCCName = theUser.getCreditCards().get(0).getNameOnCard();
//		buffCCNum = theUser.getCreditCards().get(0).getCardNumber();
//		buffCCSeccode = theUser.getCreditCards().get(0).getSecNum();
//		buffCCExpdateMonth = theUser.getCreditCards().get(0).getExpMonth();
//		buffCCExpdateYear = theUser.getCreditCards().get(0).getExpYear();

		//I don't think user name should be allowed to be changed
//		EditText userName = (EditText) findViewById(R.id.usrnm);
//		userName.setHint(theUser.getUsername());
		EditText password = (EditText) findViewById(R.id.editPassword);
		password.setHint(theUser.getPassword());
		EditText mail = (EditText) findViewById(R.id.editMail);
		mail.setHint(theUser.getEmail());
//		EditText saLine1 = (EditText) findViewById(R.id.etSALine1Edit);
//		mail.setHint(theUser.getShippingAdress().get(0).getLine1());
//		EditText saLine2 = (EditText) findViewById(R.id.etSALine2Edit);
//		mail.setHint(theUser.getShippingAdress().get(0).getLine2());
//		EditText saCity= (EditText) findViewById(R.id.etSACityEdit);
//		mail.setHint(theUser.getShippingAdress().get(0).getCity());
//		EditText saState = (EditText) findViewById(R.id.etSAStateEdit);
//		mail.setHint(theUser.getEmail());
//		EditText saZipcode = (EditText) findViewById(R.id.etSAZipcodeEdit);
//		mail.setHint(theUser.getEmail());
//		EditText saCountry = (EditText) findViewById(R.id.etSACountryEdit);
//		mail.setHint(theUser.getEmail());
//		EditText baLine1 = (EditText) findViewById(R.id.etBALine1Edit);
//		mail.setHint(theUser.getEmail());
//		EditText baLine2 = (EditText) findViewById(R.id.etBALine2Edit);
//		mail.setHint(theUser.getEmail());
//		EditText baCity= (EditText) findViewById(R.id.etBACityEdit);
//		mail.setHint(theUser.getEmail());
//		EditText baState = (EditText) findViewById(R.id.etBAStateEdit);
//		mail.setHint(theUser.getEmail());
//		EditText baZipcode = (EditText) findViewById(R.id.etBAZipcodeEdit);
//		mail.setHint(theUser.getEmail());
//		EditText baCountry = (EditText) findViewById(R.id.etBACountryEdit);
//		mail.setHint(theUser.getEmail());
		
		
		//User name not changeable
//		Button userUpdate= (Button) this.findViewById(R.id.usrUpdate);
//		userUpdate.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View arg0) 
//			{
//				if (!spiceManager.isStarted())
//				{
//					spiceManager.start(UserEditActivity.this);
//					buffUser= ((TextView)findViewById(R.id.usrnm)).getText().toString();
//					EditRequest JsonSpringAndroidRequest = new EditRequest(theUser,user,theUser.getPassword(),theUser.getEmail(),buffUser);
//					spiceManager.execute(JsonSpringAndroidRequest, "user_edit", DurationInMillis.ALWAYS_EXPIRED, new UserEditListener());
//				}
//			}
//			
//		});
		Button passUpdate= (Button) this.findViewById(R.id.passUpdate);
		passUpdate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) 
			{
				if (!spiceManager.isStarted())
				{
					spiceManager.start(EditUserActivity.this);
					buffPass = ((TextView)findViewById(R.id.editPassword)).getText().toString();
					EditRequest JsonSpringAndroidRequest = new EditRequest(theUser,user,buffPass,theUser.getEmail(),theUser.getUsername());
					spiceManager.execute(JsonSpringAndroidRequest, "user_edit", DurationInMillis.ALWAYS_EXPIRED, new UserEditListener());
				}
			}
			
		});
		
		Button emailUpdate= (Button) this.findViewById(R.id.emailUpdate);
		emailUpdate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) 
			{
				if (!spiceManager.isStarted())
				{
					spiceManager.start(EditUserActivity.this);
					buffEmail= ((TextView)findViewById(R.id.editMail)).getText().toString();
					EditRequest JsonSpringAndroidRequest = new EditRequest(theUser,user,theUser.getPassword(),buffEmail,theUser.getUsername());
					spiceManager.execute(JsonSpringAndroidRequest, "user_edit", DurationInMillis.ALWAYS_EXPIRED, new UserEditListener());
				}
			}
			
		});
		
		mBEditCC = (Button) findViewById(R.id.bEditCreditCards);
		mBEditCC.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		mBEditSA = (Button) findViewById(R.id.bEditCreditCards);
		mBEditSA.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Button deleteAccount = (Button) this.findViewById(R.id.delete_account);
		deleteAccount.setOnClickListener(new OnClickListener()
		{

		
			public void onClick(View arg0) 
			{
				if (!spiceManager.isStarted())
				{
					spiceManager.start(EditUserActivity.this);
					DeleteUserRequest JsonSpringAndroidRequest = new DeleteUserRequest(user);
					spiceManager.execute(JsonSpringAndroidRequest, "user_edit", DurationInMillis.ALWAYS_EXPIRED, new UserDeleteListener());
				}
				
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_edit, menu);
		return true;
	}
	
	private class UserEditListener implements RequestListener<Boolean>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {
			
			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {
				
				Toast.makeText(EditUserActivity.this, "Update Unsuccesful", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(Boolean edit) {
			spiceManager.shouldStop();
			theUser.setEmail(buffEmail);
			theUser.setPassword(buffPass);
//			theUser.setUsername(buffUser);
//			EditText userName = (EditText) findViewById(R.id.usrnm);
//			userName.setHint(theUser.getUsername());
//			userName.setText("");
			EditText password = (EditText) findViewById(R.id.editPassword);
			password.setHint(theUser.getPassword());
			password.setText("");
			EditText mail = (EditText) findViewById(R.id.editMail);
			mail.setText("");
			mail.setHint(theUser.getEmail());
			
			
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{
		
		}
	}

		private class UserDeleteListener implements RequestListener<Boolean>, RequestProgressListener {

			@Override
			public void onRequestFailure(SpiceException arg0) {
				
				Log.d("error",arg0.getMessage());
				if (!(arg0 instanceof RequestCancelledException)) {
					
					Toast.makeText(EditUserActivity.this, "Delete Unsuccesful", Toast.LENGTH_SHORT).show();
				}
				spiceManager.shouldStop();
			}

			@Override
			public void onRequestSuccess(Boolean edit) {
				spiceManager.shouldStop();
				AdminSession.getEditUsers().remove(user);
//				Intent back = new Intent(UserEditActivity.this,SearchAccountActivity.class);
//				startActivity(back);
				finish();
			}

			@Override
			public void onRequestProgressUpdate(RequestProgress arg0) 
			{
			
			}
	}
}
