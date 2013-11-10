package com.basket.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.basket.containers.AdminSession;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.Product;
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

public class AdminProductDateSaleActivity extends Activity 
{
	private Product product;
	private RadioGroup sortradiogroup;
	private TextView productName;
	private SpiceManager spiceManager  = new SpiceManager(CarJsonSpringAndroidSpiceService.class);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_sales);
		productName = (TextView) findViewById(R.id.adminProductName);
		product = null;
		for(Product p : AdminSession.getProducts() ){
			if(p.getProductPId()== this.getIntent().getIntExtra("selected", 0)){
				product = p ;
				break;
			}
		}
		productName.setText(product.getName());
		sortradiogroup = (RadioGroup) findViewById(R.id.sortradiogroup);
		sortradiogroup.check(R.id.radioYear);
		type = "Year";
		Button update = (Button) this.findViewById(R.id.update);
		update.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) 
			{
				if(!spiceManager.isStarted())
				{
					spiceManager.start(AdminProductDateSaleActivity.this);
				}
				DatePicker date = (DatePicker) findViewById(R.id.datePicker1);
				//Modificar segun radio button
				sortradiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId){
						case R.id.radioYear:
							type = "Year";
							break;
						case R.id.radioMonth:
							type="Month";
							break;
						case R.id.radioWeek:
							type="Week";
							break;
						case R.id.radioDay:
							type = "Day";
							break;
						}
					}
				});
				ProductReportRequest JsonSpringAndroidRequest = new ProductReportRequest(date.getDayOfMonth(),date.getMonth(),date.getYear(),type, product.getProductPId());
				spiceManager.execute(JsonSpringAndroidRequest, "product_search", DurationInMillis.ALWAYS_EXPIRED, new ProductReportListener());

			}

		});
		
	}
	public String type = "";
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

				Toast.makeText(AdminProductDateSaleActivity.this, "Search could not be processed", Toast.LENGTH_SHORT).show();
			}
			if(spiceManager.isStarted())
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
