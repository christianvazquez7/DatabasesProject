package com.basket.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basket.containers.BasketSession;
import com.basket.containers.CategoryList;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.restrequest.GetDOTDRequest;
import com.basket.restrequest.SendForgotFieldEmailRequest;
import com.example.basket.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class ForgotFieldActivity extends Activity {
	private static final String JSON_CACHE_KEY = "forgot_field";
	private Button mSendEmailButton;
	private EditText mEmailInput;
	private String mUserEmail;
	private SpiceManager spiceManager = new SpiceManager(CarJsonSpringAndroidSpiceService.class);
	private SendForgotFieldEmailRequest JsonSpringAndroidRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_field);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
		mEmailInput = (EditText) findViewById(R.id.forgotFieldEmailEditText);

		mSendEmailButton = (Button) findViewById(R.id.forgotFieldButton);
		mSendEmailButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mUserEmail = mEmailInput.getText().toString();

				if(mUserEmail!="" && mUserEmail!=null){
					if(!mUserEmail.contains("@")){
						Toast.makeText(ForgotFieldActivity.this, "Wrong input", Toast.LENGTH_SHORT).show();
					}
					else
						sendAccountDetailsEmail(mUserEmail);
				}
				else{
					Toast.makeText(ForgotFieldActivity.this, "Wrong input", Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	private void sendAccountDetailsEmail(String userEmail){
		//action to send email
		//Toast.makeText(ForgotFieldActivity.this, "Email with information sent", Toast.LENGTH_LONG).show();
		if(!spiceManager.isStarted()){
			spiceManager.start(ForgotFieldActivity.this);
			JsonSpringAndroidRequest = new SendForgotFieldEmailRequest(userEmail);
			spiceManager.execute(JsonSpringAndroidRequest, JSON_CACHE_KEY, DurationInMillis.ALWAYS_EXPIRED, new SendEmailRequestListner());
		}
	}
	private class SendEmailRequestListner implements RequestListener<Boolean>, RequestProgressListener {
		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				Toast.makeText(ForgotFieldActivity.this, "No connection to server", Toast.LENGTH_SHORT).show();
			}
			if(spiceManager.isStarted())
				spiceManager.shouldStop();
		}
//		@Override
//		public void onRequestSuccess(CategoryList cat) 
//		{
//			Log.d("PROGRESS", "GETCAT");
//			BasketSession.setCategories(cat.getCategories());
//			GetDOTDRequest req = new GetDOTDRequest();
//			spiceManager.execute(req, JSON_CACHE_KEY, DurationInMillis.ALWAYS_EXPIRED, new DealRequestListener());
//			//			String email =((TextView)findViewById(R.id.email)).getText().toString();
//			//			String password =((TextView)findViewById(R.id.password)).getText().toString();
//			//			UserRequest JsonSpringAndroidRequest = new UserRequest(email,password);
//			//			spiceManager.execute(JsonSpringAndroidRequest, JSON_CACHE_KEY, DurationInMillis.ALWAYS_EXPIRED, new UserRequestListener());
//		}
		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}
		@Override
		public void onRequestSuccess(Boolean arg0) {
			if(spiceManager.isStarted()){
				spiceManager.shouldStop();
			}
			Toast.makeText(ForgotFieldActivity.this, "Sent email", Toast.LENGTH_LONG).show();
			
		}
	}

}
