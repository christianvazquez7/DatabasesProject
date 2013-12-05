package com.basket.activities;


import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.basket.containers.BasketSession;
import com.basket.containers.Deal;
import com.basket.containers.EventList;
import com.basket.general.BuyEvent;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.ReviewList;
import com.basket.restrequest.GetReviewsRequest;
import com.basket.restrequest.RegisterDeviceRequest;
import com.example.basket.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class HomePageActivity extends Activity {
	///////////////////////////////////////////////////////////////////////////
	//Notifications
	///////////////////////////////////////////////////////////////////////////
	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	/**
	 * Substitute you own sender ID here. This is the project number you got
	 * from the API Console, as described in "Getting Started."
	 */
	String SENDER_ID = "860573449089";

	GoogleCloudMessaging gcm;
	AtomicInteger msgId = new AtomicInteger();
	Context context;
	String regid;
	private SpiceManager spiceManager= new SpiceManager(CarJsonSpringAndroidSpiceService.class);

	////////////////////////////////////////////////////////////////////////////
	private static final String JSON_CACHE_KEY = "tweets_json";

	private SlidingMenu slidingMenu;

	ViewFlipper mVFlipper1, mVFlipper2;
	Animation animIn, animOut;
	//	ImageView[] mIVDeal, mIVRecom;
	private BuyEvent buyevent, temp;
	private int pos,i;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_home_page);

		slidingMenu = new SlidingMenu(this);

		slidingMenu.setMode(SlidingMenu.LEFT);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingMenu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
		slidingMenu.setShadowDrawable(R.drawable.slidingmenu_shadow);
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		slidingMenu.setFadeDegree(0.35f);
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidingMenu.setMenu(R.layout.slidingmenu);
		slidingMenu.setShadowWidth(3);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		/*mIVDeal[0] = (ImageView) findViewById(R.id.iViewDeal_1);
		mIVDeal[1] = (ImageView) findViewById(R.id.iViewDeal_2);
		mIVDeal[2] = (ImageView) findViewById(R.id.iViewDeal_3);
		mIVRecom[0] = (ImageView) findViewById(R.id.iViewRecom_1);
		mIVRecom[1] = (ImageView) findViewById(R.id.iViewRecom_2);
		mIVRecom[2] = (ImageView) findViewById(R.id.iViewRecom_3);

		mIVDeal[0].setBackground(this.getResources().getDrawable(R.drawable.laptop_image));
		mIVDeal[1].setBackground(this.getResources().getDrawable(R.drawable.camera_image));
		mIVDeal[2].setBackground(this.getResources().getDrawable(R.drawable.tablet_image));
		mIVRecom[0].setBackground(this.getResources().getDrawable(R.drawable.book_image));
		mIVRecom[1].setBackground(this.getResources().getDrawable(R.drawable.tennis_image));
		mIVRecom[2].setBackground(this.getResources().getDrawable(R.drawable.tv_image));*/

		animIn = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_up); 
		animOut = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_down); 


		LayoutInflater inf = LayoutInflater.from(getApplicationContext());
		mVFlipper1 = (ViewFlipper) findViewById(R.id.vfDeals);
		mVFlipper2 = (ViewFlipper) findViewById(R.id.vfRecom);
		for(i =0; i<BasketSession.getDeals().size();i++)
			//		for (Deal d : BasketSession.getDeals())
		{
			Deal d = BasketSession.getDeals().get(i);
			View a = inf.inflate(R.layout.blank, null);
			TextView t = (TextView) a.findViewById(R.id.deal_name);
			t.setText(d.getTitle());

			Bitmap bm=null;
			if(((BuyEvent)d.getEve()).getPic()!=null)
				bm = BitmapFactory.decodeByteArray(((BuyEvent)d.getEve()).getPic(), 0 ,((BuyEvent)d.getEve()).getPic().length);

			ImageView pic =(ImageView)a.findViewById(R.id.dpic);
			temp = d.getEve();
			if(pic!=null){
				pic.setImageBitmap(bm);
				pic.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (!spiceManager.isStarted())
						{
							productPage =  new Intent(HomePageActivity.this,BuyEventPageActivity.class);
							productPage.putExtra("selectedEvent",i);
							spiceManager.start(HomePageActivity.this);
							productPage.putExtra("fromDeals", true);
							GetReviewsRequest a;
							a = new GetReviewsRequest(temp.getId(),1);
							spiceManager.execute(a, "", DurationInMillis.ALWAYS_EXPIRED, new GetReviewsListener());
						}
					}
				});
			}

			mVFlipper1.addView(a);

		}
		for(int i = 0;i<BasketSession.getRecommendations().size();i++){
			BuyEvent e = BasketSession.getRecommendations().get(i);
			pos = i;
			View a = inf.inflate(R.layout.blank, null);
			TextView t = (TextView) a.findViewById(R.id.deal_name);
			t.setText(e.getTitle());
			buyevent = e;
			byte[] K = e.getPic();
			Bitmap bm=null;
			if(e.getPic()!=null)
				bm = BitmapFactory.decodeByteArray(e.getPic(), 0 ,e.getPic().length);

			ImageView pic =(ImageView)a.findViewById(R.id.dpic);
			pic.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!spiceManager.isStarted())
					{
						productPage =  new Intent(HomePageActivity.this,BuyEventPageActivity.class);
						productPage.putExtra("selectedEvent",pos);
						spiceManager.start(HomePageActivity.this);
						GetReviewsRequest a;
						a = new GetReviewsRequest(buyevent.getId(),1);
						spiceManager.execute(a, "", DurationInMillis.ALWAYS_EXPIRED, new GetReviewsListener());
					}
				}
			});
			if(pic!=null){
				if(K.length ==0){
					pic.setImageResource(R.drawable.ic_launcher);
				}
				else
					pic.setImageBitmap(bm);
			}
			mVFlipper2.addView(a);
		}


		mVFlipper1.setFlipInterval(6000);
		mVFlipper2.setFlipInterval(6000);

		mVFlipper1.startFlipping();
		mVFlipper2.startFlipping();


		context = getApplicationContext();

		if (checkPlayServices()) {
			gcm = GoogleCloudMessaging.getInstance(this);
			regid = getRegistrationId(context);
			if(!spiceManager.isStarted())
				spiceManager.start(HomePageActivity.this);
			RegisterDeviceRequest JsonSpringAndroidRequest = new RegisterDeviceRequest(regid);
			spiceManager.execute(JsonSpringAndroidRequest, "user_edit", DurationInMillis.ALWAYS_EXPIRED, new RegDevListener());

			if (regid.length() == 0) {
				registerInBackground();
			}


		} else {
			Log.i("No play services", "No valid Google Play Services APK found.");
		}
	}
	private Intent productPage;
	@Override
	public void onBackPressed() {
		if ( slidingMenu.isMenuShowing()) {
			slidingMenu.toggle();
		}
		else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ( keyCode == KeyEvent.KEYCODE_MENU ) {
			this.slidingMenu.toggle();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.slidingMenu.toggle();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		// Check device for Play Services APK. Se deberia hacer pero enfogona....
		//checkPlayServices();
		GetRecommendationsRequest recommendations = new GetRecommendationsRequest(BasketSession.getUser());
		spiceManager.execute(recommendations, JSON_CACHE_KEY, DurationInMillis.ALWAYS_EXPIRED, new GetRecommendationsListner());

	}
	/**
	 * Check the device to make sure it has the Google Play Services APK. If
	 * it doesn't, display a dialog that allows users to download the APK from
	 * the Google Play Store or enable it in the device's system settings.
	 */
	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i("Checking play services", "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}

	/**
	 * Stores the registration ID and the app versionCode in the application's
	 * {@code SharedPreferences}.
	 *
	 * @param context application's context.
	 * @param regId registration ID
	 */
	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getGcmPreferences(context);
		int appVersion = getAppVersion(context);
		Log.i("Saving reg id in app ver", "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	/**
	 * Gets the current registration ID for application on GCM service, if there is one.
	 * <p>
	 * If result is empty, the app needs to register.
	 *
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGcmPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.length()==0) {
			Log.i("Getting reg id", "Registration not found.");
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i("Getting reg id", "App version changed.");
			return "";
		}
		Log.d("Registration", registrationId);
		return registrationId;
	}

	/**
	 * Registers the application with GCM servers asynchronously.
	 * <p>
	 * Stores the registration ID and the app versionCode in the application's
	 * shared preferences.
	 */
	private void registerInBackground() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					regid = gcm.register(SENDER_ID);
					msg = "Device registered, registration ID=" + regid;

					// You should send the registration ID to your server over HTTP, so it
					// can use GCM/HTTP or CCS to send messages to your app.
					// sendRegistrationIdToBackend();

					// For this demo: we don't need to send it because the device will send
					// upstream messages to a server that echo back the message using the
					// 'from' address in the message.

					// Persist the regID - no need to register again.
					if(!spiceManager.isStarted())
						spiceManager.start(HomePageActivity.this);
					RegisterDeviceRequest JsonSpringAndroidRequest = new RegisterDeviceRequest(regid);
					spiceManager.execute(JsonSpringAndroidRequest, "user_edit", DurationInMillis.ALWAYS_EXPIRED, new RegDevListener());

					storeRegistrationId(context, regid);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
					// If there is an error, don't just keep trying to register.
					// Require the user to click a button again, or perform
					// exponential back-off.
				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				//mDisplay.append(msg + "\n");
			}
		}.execute(null, null, null);
	}
	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGcmPreferences(Context context) {
		// This sample app persists the registration ID in shared preferences, but
		// how you store the regID in your app is up to you.
		return getSharedPreferences(HomePageActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
	}
	//    /**
	//     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP or CCS to send
	//     * messages to your app. Not needed for this demo since the device sends upstream messages
	//     * to a server that echoes back the message using the 'from' address in the message.
	//     */
	//    private void sendRegistrationIdToBackend() {
	//      // Your implementation here.
	//    }

	private class RegDevListener implements RequestListener<Boolean>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				//Toast.makeText(HomePageActivity.this, "Failed to register Unsuccesful", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(Boolean edit) {
			spiceManager.shouldStop();
			//Toast.makeText(EditSingleCCActivity.this, "Successfully updated addresses", Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}
	}

	private class GetReviewsListener implements RequestListener<ReviewList>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				Toast.makeText(HomePageActivity.this, "No connection to server", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(ReviewList reviews) 
		{
			spiceManager.shouldStop();
			BasketSession.setReviews(reviews.getReviews());


			Intent productPage = new Intent(HomePageActivity.this,BuyEventPageActivity.class);
			productPage.putExtra("fromHP", true);
			startActivityForResult(productPage, 0);
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) {
			// TODO Auto-generated method stub

		}



	}
	private class GetRecommendationsListner implements RequestListener<EventList>, RequestProgressListener {
		@Override
		public void onRequestFailure(SpiceException arg0) {
			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {
				Toast.makeText(HomePageActivity.this, "Could not get recommendations", Toast.LENGTH_SHORT).show();
			}
			if(spiceManager.isStarted())
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
			if(spiceManager.isStarted())
				spiceManager.shouldStop();
			LayoutInflater inf = LayoutInflater.from(getApplicationContext());

			for(int i = 0;i<BasketSession.getRecommendations().size();i++){
				BuyEvent e = BasketSession.getRecommendations().get(i);
				pos = i;
				View a = inf.inflate(R.layout.blank, null);
				TextView t = (TextView) a.findViewById(R.id.deal_name);
				t.setText(e.getTitle());
				buyevent = e;
				byte[] K = e.getPic();
				Bitmap bm=null;
				if(e.getPic()!=null)
					bm = BitmapFactory.decodeByteArray(e.getPic(), 0 ,e.getPic().length);

				ImageView pic =(ImageView)a.findViewById(R.id.dpic);
				pic.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (!spiceManager.isStarted())
						{
							productPage =  new Intent(HomePageActivity.this,BuyEventPageActivity.class);
							productPage.putExtra("selectedEvent",pos);
							spiceManager.start(HomePageActivity.this);
							GetReviewsRequest a;
							a = new GetReviewsRequest(buyevent.getId(),1);
							spiceManager.execute(a, "", DurationInMillis.ALWAYS_EXPIRED, new GetReviewsListener());
						}
					}
				});
				if(pic!=null){
					if(K.length ==0){
						pic.setImageResource(R.drawable.ic_launcher);
					}
					else
						pic.setImageBitmap(bm);
				}
				mVFlipper2.addView(a);
			}
		}
	}
}

