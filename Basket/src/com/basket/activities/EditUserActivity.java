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
import com.basket.containers.BasketSession;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.User;
import com.basket.restrequest.DeleteUserRequest;
import com.basket.restrequest.EditRequest;
import com.example.basket.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class EditUserActivity extends Activity {

	private Button mEditCC, mEditShipAdd;

	private User theUser;
	private int user;
	private String buffPass,buffEmail,buffUser;
	private SpiceManager spiceManager= new SpiceManager(CarJsonSpringAndroidSpiceService.class);

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		user = this.getIntent().getIntExtra("selectedUser", 0);
		theUser = BasketSession.getUser();
		buffPass = theUser.getPassword();
		buffEmail = theUser.getEmail();
		buffUser = theUser.getUsername();
		
		EditText userName = (EditText) findViewById(R.id.usrnm);
		userName.setHint(theUser.getUsername());
		EditText password = (EditText) findViewById(R.id.editPassword);
		password.setHint(theUser.getPassword());
		EditText mail = (EditText) findViewById(R.id.editMail);
		mail.setHint(theUser.getEmail());

		Button userUpdate= (Button) this.findViewById(R.id.usrUpdate);
		userUpdate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) 
			{
				if (!spiceManager.isStarted())
				{
					spiceManager.start(EditUserActivity.this);
					buffUser= ((TextView)findViewById(R.id.usrnm)).getText().toString();
					EditRequest JsonSpringAndroidRequest = new EditRequest(theUser,user,theUser.getPassword(),theUser.getEmail(),buffUser);
					spiceManager.execute(JsonSpringAndroidRequest, "user_edit", DurationInMillis.ALWAYS_EXPIRED, new UserEditListener());
				}
			}
		});
		Button passUpdate= (Button) this.findViewById(R.id.passUpdate);
		passUpdate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) 
			{
				if (!spiceManager.isStarted())
				{
					spiceManager.start(EditUserActivity.this);
					buffPass= ((TextView)findViewById(R.id.editPassword)).getText().toString();
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

		mEditCC = (Button) findViewById(R.id.bEditCreditCards);
		mEditCC.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent editCCIntent = new Intent(EditUserActivity.this, EditCreditCardsActivity.class);
				editCCIntent.putExtra("selectedUser", user);
				startActivity(editCCIntent);
			}
		});
		
		mEditShipAdd = (Button) findViewById(R.id.bEditShippingAdd);
		mEditShipAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent editSAIntent = new Intent(EditUserActivity.this, EditSAddressesActivity.class);
				editSAIntent.putExtra("selectedUser", user);
				startActivity(editSAIntent);
			}
		});
		
		Button deleteAccount = (Button) this.findViewById(R.id.doneButton);
		deleteAccount.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0) 
			{
				finish();
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
			if(spiceManager.isStarted())
				spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(Boolean edit) {
			if(spiceManager.isStarted())
				spiceManager.shouldStop();
			theUser.setEmail(buffEmail);
			theUser.setPassword(buffPass);
			theUser.setUsername(buffUser);
			EditText userName = (EditText) findViewById(R.id.usrnm);
			userName.setHint(theUser.getUsername());
			userName.setText("");
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

	
}
