package com.basket.adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.basket.general.Bid;
import com.example.basket.R;
import com.example.basket.R.id;

public class BidsInSellingProductAdapter extends ArrayAdapter<Bid>
{
	private Context context;
	public BidsInSellingProductAdapter(Context activity,ArrayList<Bid> bids)
	{
		super(activity,0,bids);
		context=activity;
	}
	public View getView(int pos,View convertView, ViewGroup parent)
	{
		if (convertView==null)
		{
			convertView=((Activity)context).getLayoutInflater().inflate(R.layout.bid_item_view, null);
			
		}
		Bid currentBid = this.getItem(pos);	
		if(currentBid!=null)
		{
			Date date =null;
			SimpleDateFormat formatter, FORMATTER;
			formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			String oldDate = currentBid.getBidTime();
			 try
			{
				 date = formatter.parse(oldDate);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FORMATTER = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			((TextView)convertView.findViewById(id.bidamount)).setText("$"+Double.toString(currentBid.getAmmount()));
			((TextView)convertView.findViewById(id.bidderName)).setText(currentBid.getBidder());
			if(date!=null)
				((TextView)convertView.findViewById(id.bidDate)).setText(FORMATTER.format(date));
			else
			((TextView)convertView.findViewById(id.bidDate)).setText(currentBid.getBidTime());
		

		}
		return convertView;
		
	}
}
