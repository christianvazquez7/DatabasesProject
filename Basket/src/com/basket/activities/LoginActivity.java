package com.basket.activities;

<<<<<<< HEAD
import java.util.LinkedList;

=======
>>>>>>> master2
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.basket.containers.BasketSession;
import com.basket.containers.CategoryList;
import com.basket.containers.DealList;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.User;
<<<<<<< HEAD
import com.basket.general.UserAccount;
import com.basket.restrequest.GetCategoriesRequest;
import com.basket.restrequest.GetDOTDRequest;
=======
import com.basket.restrequest.AdminRequest;
>>>>>>> master2
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
	private TextView mCreateAccountTextView, mForgotAccountTextView;
	private Button mLoginButton;
	private static final String JSON_CACHE_KEY = "tweets_json";
	private static final int SIZE_OF_BUFFER_TO_SIMULATE_OUT_OF_MEMORY = 1000000;
	private SpiceManager spiceManager = new SpiceManager(CarJsonSpringAndroidSpiceService.class);
<<<<<<< HEAD
	private GetCategoriesRequest JsonSpringAndroidRequest;
=======
	private UserRequest JsonSpringAndroidRequest;
	private AdminRequest adminRequest;
>>>>>>> master2
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		spiceManager.addListenerIfPending(User.class, JSON_CACHE_KEY, new UserRequestListener());
		LoginActivity.this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_login);

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
<<<<<<< HEAD
//					mLoginButton.setActivated(false);
//					String email =((TextView)findViewById(R.id.email)).getText().toString();
//					String password =((TextView)findViewById(R.id.password)).getText().toString();
					JsonSpringAndroidRequest = new GetCategoriesRequest();
					spiceManager.execute(JsonSpringAndroidRequest, JSON_CACHE_KEY, DurationInMillis.ALWAYS_EXPIRED, new GetCategoriesListener());
=======
					mLoginButton.setActivated(false);
					String email =((TextView)findViewById(R.id.email)).getText().toString().trim();
					String password =((TextView)findViewById(R.id.password)).getText().toString().trim();
					if(email.contains("@")){
						adminRequest = new AdminRequest(email,password);
						spiceManager.execute(adminRequest, JSON_CACHE_KEY, DurationInMillis.ALWAYS_EXPIRED, new AdminRequestListner());

					}
					else{
						JsonSpringAndroidRequest = new UserRequest(email,password);
						spiceManager.execute(JsonSpringAndroidRequest, JSON_CACHE_KEY, DurationInMillis.ALWAYS_EXPIRED, new UserRequestListener());
					}
>>>>>>> master2
				}
			}
		});

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
<<<<<<< HEAD
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
=======
			Log.d("try",User.getEmail().toString());
			//			Log.d("try",User.getFirstName().toString());
			//			Log.d("try",User.getLastName().toString());
			//			Log.d("try",User.getPassword().toString());
			//			Log.d("try",User.getUsername().toString());
			Log.d("try",User.getCurrentlySellingOnBid().toString());

			BasketSession.beginSession(User);
			Intent intent;
			intent = new Intent(LoginActivity.this,HomePageActivity.class);
			startActivity(intent);
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{
			
		}
>>>>>>> master2

		}

<<<<<<< HEAD
		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}
=======
	}
	private class AdminRequestListner implements RequestListener<Boolean>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {
>>>>>>> master2

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

<<<<<<< HEAD
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

=======
				Toast.makeText(LoginActivity.this, "Wrong Username or password", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
			mLoginButton.setActivated(true);
>>>>>>> master2
		}

	
		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}

		@Override
		public void onRequestSuccess(Boolean arg0) {
			Intent intent;
			if (arg0){
				intent = new Intent(LoginActivity.this,AdminPageActivity.class);
				startActivity(intent);
			}
			spiceManager.shouldStop();
		}


	}
}
