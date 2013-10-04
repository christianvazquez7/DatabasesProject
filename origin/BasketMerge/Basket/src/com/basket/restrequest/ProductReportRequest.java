package com.basket.restrequest;

import android.util.Log;

import com.basket.general.Report;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class ProductReportRequest extends SpringAndroidSpiceRequest<Report> 
{
	private int day;
	private int month;
	private int year;
	private String type;
	public ProductReportRequest(int d, int m, int y,String t) 
	{
		super(Report.class);
		day=d;
		month=m;
		year=y;
		type=t;
		
	}

	@Override
	public Report loadDataFromNetwork() throws Exception 
	{
		
		String url = "http://10.0.2.2:3412/Basket.js/ProductReport/";
		url+=day+"/"+month+"/"+ year+"/"+type;
		Log.d( "request", "loading from network" );
		return getRestTemplate().getForObject(url, Report.class);
	}
}
