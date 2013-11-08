package com.basket.activities;

import java.util.LinkedList;

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
import com.basket.containers.CategoryList;
import com.basket.containers.DealList;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.User;
import com.basket.general.UserAccount;
import com.basket.restrequest.GetCategoriesRequest;
import com.basket.restrequest.GetDOTDRequest;
import com.basket.restrequest.UserRequest;
import com.example.basket.R;
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
	private GetCategoriesRequest JsonSpringAndroidRequest;
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

		mLoginButton = (Button)findViewById(R.id.loginButton);

		mLoginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("service", "click");
				if(!spiceManager.isStarted()){
					spiceManager.start(LoginActivity.this);
//					mLoginButton.setActivated(false);
//					String email =((TextView)findViewById(R.id.email)).getText().toString();
//					String password =((TextView)findViewById(R.id.password)).getText().toString();
					JsonSpringAndroidRequest = new GetCategoriesRequest();
					spiceManager.execute(JsonSpringAndroidRequest, JSON_CACHE_KEY, DurationInMillis.ALWAYS_EXPIRED, new GetCategoriesListener());
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
			Log.d("try",BasketSession.getDeals().toString());

			

			BasketSession.beginSession(User);
			Intent intent;
			intent = new Intent(LoginActivity.this,HomePageActivity.class);
			if (User.getUsername().equals("blabla"))
				intent = new Intent(LoginActivity.this,AdminPageActivity.class);
			startActivity(intent);
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}


	}
	
	
	private class GetCategoriesListener implements RequestListener<CategoryList>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				Toast.makeText(LoginActivity.this, "No connection to server", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(CategoryList cat) 
		{
			
			
			
			mLoginButton.setActivated(false);
			BasketSession.setCategories(cat.getCategories());
			GetDOTDRequest req = new GetDOTDRequest();
			spiceManager.execute(req, JSON_CACHE_KEY, DurationInMillis.ALWAYS_EXPIRED, new DealRequestListener());
//			String email =((TextView)findViewById(R.id.email)).getText().toString();
//			String password =((TextView)findViewById(R.id.password)).getText().toString();
//			UserRequest JsonSpringAndroidRequest = new UserRequest(email,password);
//			spiceManager.execute(JsonSpringAndroidRequest, JSON_CACHE_KEY, DurationInMillis.ALWAYS_EXPIRED, new UserRequestListener());

		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}


	}
	
	
	private class DealRequestListener implements RequestListener<DealList>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				Toast.makeText(LoginActivity.this, "No connection to server", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(DealList cat) 
		{
			
			BasketSession.setDeals(cat.getEvents());
			//GetDOTDRequest req = new GetDOTDRequest();
			//spiceManager.execute(JsonSpringAndroidRequest, JSON_CACHE_KEY, DurationInMillis.ALWAYS_EXPIRED, new DealRequestListener());
			String email =((TextView)findViewById(R.id.email)).getText().toString();
			String password =((TextView)findViewById(R.id.password)).getText().toString();
			UserRequest JsonSpringAndroidRequest = new UserRequest(email,password);
			spiceManager.execute(JsonSpringAndroidRequest, JSON_CACHE_KEY, DurationInMillis.ALWAYS_EXPIRED, new UserRequestListener());

		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}


	}

}
