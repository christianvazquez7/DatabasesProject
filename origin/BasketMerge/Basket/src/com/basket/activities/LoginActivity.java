package com.basket.activities;

import java.util.LinkedList;

import org.springframework.web.client.HttpClientErrorException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basket.containers.BasketSession;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.User;
import com.basket.general.UserAccount;
import com.basket.restrequest.UserRequest;
import com.example.basket.R;
import com.example.basket.R.anim;
import com.example.basket.R.id;
import com.example.basket.R.layout;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class LoginActivity extends Activity {
	private LinkedList<UserAccount> mUserList;
	private EditText mUsernameEditText, mPasswordEditText;
	private TextView mCreateAccountTextView, mForgotAccountTextView;
	private Button mLoginButton;
	private static final int REQUEST_DELAY = 10 * 1000;
	private static final String JSON_CACHE_KEY = "tweets_json";
	private static final int SIZE_OF_BUFFER_TO_SIMULATE_OUT_OF_MEMORY = 1000000;
	private byte[] bufferToFillMemoryFaster = new byte[SIZE_OF_BUFFER_TO_SIMULATE_OUT_OF_MEMORY];
	private Button start,stop;
	private SpiceManager spiceManager = new SpiceManager(CarJsonSpringAndroidSpiceService.class);
	private UserRequest JsonSpringAndroidRequest;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		spiceManager.addListenerIfPending(User.class, JSON_CACHE_KEY, new UserRequestListener());
		LoginActivity.this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_login);

		//
		mCreateAccountTextView = (TextView) this.findViewById(R.id.createAccount);

		mCreateAccountTextView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (spiceManager.isStarted())
				{
					spiceManager.cancelAllRequests();
					spiceManager.shouldStop();
				}
				Intent signup = new Intent(LoginActivity.this,SignUpActivity.class);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				startActivity(signup);

			}
		});
		//
		mForgotAccountTextView = (TextView)findViewById(R.id.forgotField);

		mForgotAccountTextView.setOnClickListener(new View.OnClickListener() {

			@Override 
			public void onClick(View v) {
				if (spiceManager.isStarted())
				{
					spiceManager.cancelAllRequests();
					spiceManager.shouldStop();
				}
				Intent forgot = new Intent(LoginActivity.this, ForgotFieldActivity.class);
				startActivity(forgot);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			}
		});
		//
		mLoginButton = (Button)findViewById(R.id.bEditCreditCards);
		mLoginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("service", "click");
				if(!spiceManager.isStarted()){
					spiceManager.start(LoginActivity.this);
					mLoginButton.setActivated(false);
					String email =((TextView)findViewById(R.id.email)).getText().toString();
					String password =((TextView)findViewById(R.id.password)).getText().toString();
					JsonSpringAndroidRequest = new UserRequest(email,password);
					spiceManager.execute(JsonSpringAndroidRequest, JSON_CACHE_KEY, DurationInMillis.ALWAYS_EXPIRED, new UserRequestListener());

				}
			}
		});

	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	private class UserRequestListener implements RequestListener<User>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				Toast.makeText(LoginActivity.this, "Wrong Username or password", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
			mLoginButton.setActivated(true);
		}

		@Override
		public void onRequestSuccess(User User) 
		{
			spiceManager.shouldStop();
			Log.d("try",User.toString());
			BasketSession.beginSession(User);
			Intent intent = new Intent(LoginActivity.this,FrontPageActivity.class);
			startActivity(intent);
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}


	}

}
