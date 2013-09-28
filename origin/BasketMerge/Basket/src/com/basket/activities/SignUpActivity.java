package com.basket.activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.basket.fragments.DatePickerFragment;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.User;
import com.basket.restrequest.CreateUserRequest;
import com.example.basket.R;
import com.example.basket.R.id;
import com.example.basket.R.layout;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;


public class SignUpActivity extends Activity {
	private TextView mAgePickPopup;
	private User newUser;
	private SpiceManager spiceManager  = new SpiceManager(CarJsonSpringAndroidSpiceService.class);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		mAgePickPopup = (TextView) findViewById(R.id.ageTextView);
		mAgePickPopup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DatePickerFragment();
			    newFragment.show(getFragmentManager(), "datePicker");
				
			}
		});
		Button signUp = (Button) this.findViewById(R.id.signUp);
		signUp.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				if (!spiceManager.isStarted())
				{
					spiceManager.start(SignUpActivity.this);
					String userName = ((TextView)findViewById(R.id.userNameSignUp)).getText().toString();
					String email = ((TextView)findViewById(R.id.emailSignUp)).getText().toString();
					String password =((TextView)findViewById(R.id.passwordSignUp)).getText().toString();
					newUser = new User(userName,email,password);
					spiceManager.execute(new CreateUserRequest(newUser), new CreateAccountListener());

					
				}
				
			}
			
		});
	}
	
	
	private class CreateAccountListener implements RequestListener<Boolean>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) 
			{

				Toast.makeText(SignUpActivity.this, "User could not be created", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(Boolean CreatedUser) 
		{
			Toast.makeText(SignUpActivity.this, "User created", Toast.LENGTH_SHORT).show();
			spiceManager.shouldStop();
			
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}


	}
}
