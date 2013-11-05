package com.basket.adapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.basket.general.BidEvent;
import com.basket.general.BuyEvent;
import com.example.basket.R;

public class BidProductInOrderAdapter extends ArrayAdapter<BidEvent>
{
	private Context context;
	public BidProductInOrderAdapter(Context activity,ArrayList<BidEvent> products)
	{
		super(activity,0,products);
		context=activity;
	}
	public View getView(int pos,View convertView, ViewGroup parent)
	{
		if (convertView==null)
		{
			convertView=((Activity)context).getLayoutInflater().inflate(R.layout.bidproduct_view, null);
			
		}

		BidEvent currentProduct = (BidEvent) this.getItem(pos);	
		
		Log.d("try",currentProduct.toString());
		((TextView)convertView.findViewById(R.id.theProduct)).setText(currentProduct.getProduct().getName()); 

		((TextView)convertView.findViewById(R.id.bidproduct)).setText(currentProduct.getbTitle()); 
		
		if(currentProduct.getWinningBid()==null)
			((TextView)convertView.findViewById(R.id.bidprice)).setText("$"+Double.toString(currentProduct.getMinBid()));
		else
		((TextView)convertView.findViewById(R.id.bidprice)).setText("$"+Double.toString(currentProduct.getWinningBid().getAmmount()));
		
		((TextView)convertView.findViewById(R.id.bidsupplier)).setText(currentProduct.getProduct().getManufacturer());
		
		
		java.util.Date date = null;
		
		SimpleDateFormat formatter, FORMATTER;
		formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String oldDate = currentProduct.getEndingTime();
		 try {
			date = formatter.parse(oldDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FORMATTER = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		 ((TextView)convertView.findViewById(R.id.endDate)).setText(FORMATTER.format(date));
		
		
		
		 final RatingBar minimumRating = (RatingBar)convertView.findViewById(R.id.bidratingBar1);
		    minimumRating.setRating(currentProduct.getRating());
		    
			Bitmap bm=null;
			if(currentProduct.getPicture()!=null)
			 bm = BitmapFactory.decodeByteArray(currentProduct.getPicture(), 0 ,currentProduct.getPicture().length);
			
			ImageView pic =(ImageView)convertView.findViewById(R.id.bidthumb);
			if(pic!=null)
			pic.setImageBitmap(bm);

		return convertView;
		
	}
}
