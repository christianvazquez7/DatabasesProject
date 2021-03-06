package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.Report;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class SalesReportRequest extends SpringAndroidSpiceRequest<Report> 
{
	private int day;
	private int month;
	private int year;
	private String type;
	public SalesReportRequest(int d, int m, int y,String t) 
	{
		super(Report.class);
		Log.d("Date", "D"+d+"m"+m+"y"+y);
		day=d;
		month=m+1;
		year=y;
		type=t;
		
	}

	@Override
	public Report loadDataFromNetwork() throws Exception 
	{
		String url = BasketConstants.externalIp+"/Basket.js/SalesReport/";
		url+=day+"/"+month+"/"+ year+"/"+type;
		Log.d( "request", "loading from network" );
		return getRestTemplate().getForObject(url, Report.class);
	}
}
