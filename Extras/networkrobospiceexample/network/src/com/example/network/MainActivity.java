package com.example.network;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class MainActivity extends Activity {
	private static final String JSON_CACHE_KEY = "tweets_json";
	private static final int REQUEST_DELAY = 10 * 1000;
	private static final int SIZE_OF_BUFFER_TO_SIMULATE_OUT_OF_MEMORY = 1000000;
	private byte[] bufferToFillMemoryFaster = new byte[SIZE_OF_BUFFER_TO_SIMULATE_OUT_OF_MEMORY];
	private Button start,stop;
	private SpiceManager spiceManager = new SpiceManager(CarJsonSpringAndroidSpiceService.class);
	private CarJsonSpringAndroidRequest JsonSpringAndroidRequest;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		start=(Button)findViewById(R.id.button1);
		stop=(Button)findViewById(R.id.button2);
		start.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				JsonSpringAndroidRequest = new CarJsonSpringAndroidRequest();
				spiceManager.execute(JsonSpringAndroidRequest, JSON_CACHE_KEY, DurationInMillis.ALWAYS_EXPIRED, new CarRequestListener());
				
			}
		});
		stop.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onStop();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



	@Override
	protected void onStart() {
		super.onStart();
		spiceManager.start(this);
		spiceManager.addListenerIfPending(ListCars.class, JSON_CACHE_KEY, new CarRequestListener());
		spiceManager.getFromCache(ListCars.class, JSON_CACHE_KEY, DurationInMillis.ALWAYS_RETURNED, new CarRequestListener());
	}

	@Override
	protected void onStop() {
		spiceManager.shouldStop();
		super.onStop();
	}

	public void startDemo() {

		
	}

	public void stopDemo() {
		if (JsonSpringAndroidRequest != null) {
			JsonSpringAndroidRequest.cancel();
		}
	}

	private class CarRequestListener implements RequestListener<ListCars>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {
			if (!(arg0 instanceof RequestCancelledException)) {
				Toast.makeText(MainActivity.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		public void onRequestSuccess(ListCars Lists) {

			if (Lists == null) {
				return;
			}
			// Toast.makeText( RestSpiceActivity.this, "Success to load Twitter data.",
			// Toast.LENGTH_SHORT ).show();
			Log.d("Request success", Lists.cars.toString());
			Log.d("Carlist", Lists.getResults().toString());
			for(Car c: Lists.getResults()){
				Log.d("Carobject", c.getMake());
			}
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) {
			// TODO Auto-generated method stub
			Log.d("Progress update requested", "Hi");
		}


	}



}
