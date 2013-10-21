package com.basket.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.Report;
import com.basket.restrequest.ProductReportRequest;
import com.example.basket.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class DateSaleActivity extends Activity 
{

	private SpiceManager spiceManager  = new SpiceManager(CarJsonSpringAndroidSpiceService.class);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_date_sales);
		
		Button update = (Button) this.findViewById(R.id.update);
		update.setOnClickListener(new OnClickListener(){


			public void onClick(View arg0) 
			{
				if(!spiceManager.isStarted())
				{
				
				spiceManager.start(DateSaleActivity.this);
				DatePicker date = (DatePicker) findViewById(R.id.datePicker1);
				ProductReportRequest JsonSpringAndroidRequest = new ProductReportRequest(date.getDayOfMonth(),date.getMonth(),date.getYear(),"Year");
				spiceManager.execute(JsonSpringAndroidRequest, "product_search", DurationInMillis.ALWAYS_EXPIRED, new ProductReportListener());
				}
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.date_sale, menu);
		return true;
	}
	private class ProductReportListener implements RequestListener<Report>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) 
		{
			
			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {
				
				Toast.makeText(DateSaleActivity.this, "Search could not be processed", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(Report report) {

			spiceManager.shouldStop();
			TextView sales =(TextView) findViewById(R.id.total_sale_text);
			sales.setText(Double.toString(report.getTotalSales()));
			sales.setVisibility(View.VISIBLE);
			TextView gross =(TextView) findViewById(R.id.total_gross_view);
			gross.setText(Double.toString(report.getTotalGross()));
			gross.setVisibility(View.VISIBLE);
			
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{
		
		}


	}


}