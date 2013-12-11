package com.basket.adapters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.basket.general.BidEvent;
import com.basket.general.BuyEvent;
import com.basket.general.Event;
import com.example.basket.R;

public class ProductAdapter extends ArrayAdapter<Event>
{
	private Context context;
	public ProductAdapter(Context activity,ArrayList<Event> products)
	{
		super(activity,0,products);
		context=activity;
	}
	public View getView(int pos,View convertView, ViewGroup parent)
	{
		if(this.getItem(pos).isBid()){


			convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.bidproduct_view, null);

			BidEvent currentProduct = (BidEvent) this.getItem(pos);	

			Log.d("try",currentProduct.toString());
			((TextView)convertView.findViewById(R.id.theProduct)).setText(currentProduct.getProduct().getName()); 

			((TextView)convertView.findViewById(R.id.bidproduct)).setText(currentProduct.getbTitle()); 
			if(currentProduct.getWinningBid()==null)
				((TextView)convertView.findViewById(R.id.bidprice)).setText("$"+Double.toString(currentProduct.getStartingBid()));
			else
				((TextView)convertView.findViewById(R.id.bidprice)).setText("$"+Double.toString(currentProduct.getWinningBid().getAmmount()));
			((TextView)convertView.findViewById(R.id.bidsupplier)).setText(currentProduct.getProduct().getManufacturer());
			
			java.util.Date date = null;
			
			DateFormat formatter, FORMATTER=null;
			formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			formatter.setTimeZone(TimeZone.getTimeZone("GMT-0500"));
			String oldDate = currentProduct.getEndingTime();
			 try {
				date = formatter.parse(oldDate.substring(0, 19));
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


		}
		else{

			convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.product_view, null);


			BuyEvent currentProduct = (BuyEvent) this.getItem(pos);	
			((TextView)convertView.findViewById(R.id.theProductb)).setText(currentProduct.getProduct().getName());
			((TextView)convertView.findViewById(R.id.product)).setText(currentProduct.getBtitle());
			((TextView)convertView.findViewById(R.id.price)).setText("$"+Double.toString(currentProduct.getPrice()));
			((TextView)convertView.findViewById(R.id.supplier)).setText(currentProduct.getProduct().getManufacturer());
			 final RatingBar minimumRating = (RatingBar)convertView.findViewById(R.id.ratingBar1);
			    minimumRating.setRating(currentProduct.getRating());
			    
			    
			    

				Bitmap bm=null;
				if(currentProduct.getPic()!=null)
				 bm = BitmapFactory.decodeByteArray(currentProduct.getPic(), 0 ,currentProduct.getPic().length);
				
				ImageView pic =(ImageView)convertView.findViewById(R.id.thumb);
				if(pic!=null)
				pic.setImageBitmap(bm);

		}





		//((TextView)convertView.findViewById(R.id.product))
		return convertView;

	}
}
