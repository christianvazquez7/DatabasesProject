package com.basket.restrequest;

import android.util.Log;

import com.basket.general.BasketConstants;
import com.basket.general.Report;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class ProductReportRequest extends SpringAndroidSpiceRequest<Report> 
{
	private int day,month,year,pid;
	private String type;
	public ProductReportRequest(int d, int m, int y,String t, int pid) 
	{
		super(Report.class);
		Log.d("Date", "D"+d+"m"+m+"y"+y);
		day=d;
		month=m+1;
		year=y;
		type=t;
		this.pid = pid;
	}

	@Override
	public Report loadDataFromNetwork() throws Exception 
	{
		String url = BasketConstants.externalIp+"/Basket.js/ProductReport/";
		url+=day+"/"+month+"/"+ year+"/"+type+"/"+pid;
		Log.d( "request", "loading from network" );
		return getRestTemplate().getForObject(url, Report.class);
	}
}
