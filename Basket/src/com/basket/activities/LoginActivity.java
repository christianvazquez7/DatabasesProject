package com.basket.activities;

import java.util.ArrayList;

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
import com.basket.containers.EventList;
import com.basket.general.BuyEvent;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.User;
import com.basket.restrequest.AdminRequest;
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
	private TextView mCreateAccountTextView, mForgotAccountTextView;
	private Button mLoginButton;
	private static final String JSON_CACHE_KEY = "tweets_json";
	private static final int SIZE_OF_BUFFER_TO_SIMULATE_OUT_OF_MEMORY = 1000000;
	private SpiceManager spiceManager = new SpiceManager(CarJsonSpringAndroidSpiceService.class);
	private GetCategoriesRequest JsonSpringAndroidRequest;
	private UserRequest uReq;
	private AdminRequest adminRequest;
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
					JsonSpringAndroidRequest = new GetCategoriesRequest();
					spiceManager.execute(JsonSpringAndroidRequest, JSON_CACHE_KEY, DurationInMillis.ALWAYS_EXPIRED, new GetCategoriesListener());
				}

				mLoginButton.setActivated(false);

			}
		});

	}
	
	private Intent intent;
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
			Log.d("PROGRESS", "GETU");

			Log.d("try",BasketSession.getDeals().toString());



			BasketSession.beginSession(User);
			intent = new Intent(LoginActivity.this,HomePageActivity.class);
			GetRecommendationsRequest recommendations = new GetRecommendationsRequest(User);
			spiceManager.execute(recommendations, JSON_CACHE_KEY, DurationInMillis.ALWAYS_EXPIRED, new GetRecommendationsListner());

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

			Log.d("PROGRESS", "GETCAT");

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
	private class GetRecommendationsListner implements RequestListener<EventList>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				Toast.makeText(LoginActivity.this, "No connection to server", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
		}


		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}

		@Override
		public void onRequestSuccess(EventList arg0) {
			
			Log.d("PROGRESS", "Getting recomendations");

			BasketSession.setRecommendations((ArrayList<BuyEvent>) arg0.getBuyEvents());
			startActivity(intent);
			mLoginButton.setActivated(true);
			//			String email =((TextView)findViewById(R.id.email)).getText().toString();
			//			String password =((TextView)findViewById(R.id.password)).getText().toString();
			//			UserRequest JsonSpringAndroidRequest = new UserRequest(email,password);
			//			spiceManager.execute(JsonSpringAndroidRequest, JSON_CACHE_KEY, DurationInMillis.ALWAYS_EXPIRED, new UserRequestListener());


		}
	}
	private class AdminRequestListner implements RequestListener<Boolean>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {
			}
			Toast.makeText(LoginActivity.this, "Wrong Username or password", Toast.LENGTH_SHORT).show();

			spiceManager.shouldStop();
			mLoginButton.setActivated(true);
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}

		@Override
		public void onRequestSuccess(Boolean arg0) {
			Log.d("PROGRESS", "GETAD");

			Intent intent;
			if (arg0){
				intent = new Intent(LoginActivity.this,AdminPageActivity.class);
				startActivity(intent);
			}
			spiceManager.shouldStop();
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
			Log.d("PROGRESS", "GETDEAL");

			BasketSession.setDeals(cat.getEvents());
			//GetDOTDRequest req = new GetDOTDRequest();
			//spiceManager.execute(JsonSpringAndroidRequest, JSON_CACHE_KEY, DurationInMillis.ALWAYS_EXPIRED, new DealRequestListener());
			//			String email =((TextView)findViewById(R.id.email)).getText().toString();
			//			String password =((TextView)findViewById(R.id.password)).getText().toString();
			//			UserRequest JsonSpringAndroidRequest = new UserRequest(email,password);
			//			spiceManager.execute(JsonSpringAndroidRequest, JSON_CACHE_KEY, DurationInMillis.ALWAYS_EXPIRED, new UserRequestListener());


			String email =((TextView)findViewById(R.id.email)).getText().toString().trim();
			String password =((TextView)findViewById(R.id.password)).getText().toString().trim();
			if(email.contains("@")){
				adminRequest = new AdminRequest(email,password);
				spiceManager.execute(adminRequest, JSON_CACHE_KEY, DurationInMillis.ALWAYS_EXPIRED, new AdminRequestListner());
			}
			else{
				uReq = new UserRequest(email,password);
				spiceManager.execute(uReq, JSON_CACHE_KEY, DurationInMillis.ALWAYS_EXPIRED, new UserRequestListener());
			}



		}


		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}


	}
}
