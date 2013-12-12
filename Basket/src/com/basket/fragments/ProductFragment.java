package com.basket.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.basket.general.BidEvent;
import com.basket.general.BuyEvent;
import com.basket.general.Event;
import com.basket.icom.dbclass.R;


public class ProductFragment extends Fragment
{
	private Event theEvent;

	public void setEvent(Event e)
	{
		theEvent=e;
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if(theEvent.isBid()){
			BidEvent e = (BidEvent) theEvent;
			View view = inflater.inflate(R.layout.bidproduct_view2,
					container, false);
			((TextView)view.findViewById(R.id.bidproduct)).setText(((BidEvent) theEvent).getBidTitle());
			if (((BidEvent) theEvent).getWinningBid()!=null)
			((TextView)view.findViewById(R.id.bidprice)).setText("$"+Double.toString(((BidEvent) theEvent).getWinningBid().getAmmount()));
			else
				((TextView)view.findViewById(R.id.bidprice)).setText("$"+Double.toString(((BidEvent) theEvent).getMinBid()));

			((TextView)view.findViewById(R.id.bidsupplier)).setText(((BidEvent) theEvent).getProduct().getManufacturer());
			((TextView)view.findViewById(R.id.textView2)).setText(((BidEvent) theEvent).getProduct().getName());
			 final RatingBar minimumRating = (RatingBar)view.findViewById(R.id.bidratingBar1);
			    minimumRating.setRating(e.getRating());
			
			    java.util.Date date = null;
				
				SimpleDateFormat formatter, FORMATTER;
				formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				String oldDate = e.getEndingTime();
				 try {
					date = formatter.parse(oldDate);
				} catch (ParseException ed) {
					// TODO Auto-generated catch block
					ed.printStackTrace();
				}
				FORMATTER = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
				 ((TextView)view.findViewById(R.id.endDate)).setText(FORMATTER.format(date));
				
				Bitmap bm=null;
				if(e.getPicture()!=null)
				 bm = BitmapFactory.decodeByteArray(e.getPicture(), 0 ,e.getPicture().length);
				
				ImageView pic =(ImageView)view.findViewById(R.id.bidivCatPage);
				if(pic!=null)
				pic.setImageBitmap(bm);

			return view;
		}
		else{
			View view = inflater.inflate(R.layout.product_view2,
					container, false);
			((TextView)view.findViewById(R.id.product)).setText(((BuyEvent) theEvent).getBtitle());
			((TextView)view.findViewById(R.id.textView1)).setText(((BuyEvent) theEvent).getProduct().getName());
			 final RatingBar minimumRating = (RatingBar)view.findViewById(R.id.ratingBar1);
			    minimumRating.setRating(((BuyEvent) theEvent).getRating());
			((TextView)view.findViewById(R.id.price)).setText("$"+Double.toString(((BuyEvent) theEvent).getPrice()));
			((TextView)view.findViewById(R.id.supplier)).setText(((BuyEvent) theEvent).getProduct().getManufacturer());
			
			Bitmap bm=null;
			if(((BuyEvent) theEvent).getPic()!=null)
			bm = BitmapFactory.decodeByteArray(((BuyEvent) theEvent).getPic(), 0 ,((BuyEvent) theEvent).getPic().length);
			
			ImageView pic =(ImageView)view.findViewById(R.id.ivCatPage);
			if(pic!=null)
			pic.setImageBitmap(bm);
			return view;
		}

	}
	
}
