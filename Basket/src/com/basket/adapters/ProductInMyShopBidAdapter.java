package com.basket.adapters;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.basket.general.BidEvent;
import com.basket.icom.dbclass.R;

public class ProductInMyShopBidAdapter extends ArrayAdapter<BidEvent>
{
	private Context context;
	public ProductInMyShopBidAdapter(Context activity,ArrayList<BidEvent> products)
	{
		super(activity,0,products);
		context=activity;
	}
	public View getView(int pos,View convertView, ViewGroup parent)
	{
		if (convertView==null)
		{
			BidEvent currentProduct = this.getItem(pos);	
			convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.bidproduct_view3, null);
			if (currentProduct.isFinished())
				convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.bidproduct_view4, null);			
			
		}
		
		BidEvent currentProduct = this.getItem(pos);	
		((TextView)convertView.findViewById(R.id.bidproduct)).setText(currentProduct.getProduct().getName());
		((TextView)convertView.findViewById(R.id.bidsupplier)).setText("From "+currentProduct.getProduct().getManufacturer());
	    final RatingBar minimumRating = (RatingBar)convertView.findViewById(R.id.bidratingBar1);
	    minimumRating.setRating(currentProduct.getRating());
		if(currentProduct.isFinished())
		{
			convertView.setBackgroundColor(context.getResources().getColor(R.color.orange));
			if(currentProduct.getWinningBid()==null)
			{
				((TextView)convertView.findViewById(R.id.bidprice)).setText(Double.toString(currentProduct.getStartingBid()));

			}
			else
				((TextView)convertView.findViewById(R.id.bidprice)).setText(Double.toString(currentProduct.getWinningBid().getAmmount()));

		}
		else
		{
			if(currentProduct.getWinningBid()==null)
			{
				((TextView)convertView.findViewById(R.id.bidprice)).setText(Double.toString(currentProduct.getStartingBid()));

			}
			else
				((TextView)convertView.findViewById(R.id.bidprice)).setText(Double.toString(currentProduct.getWinningBid().getAmmount()));
		}
		
		

		Bitmap bm=null;
		if(currentProduct.getPicture()!=null)
		 bm = BitmapFactory.decodeByteArray(currentProduct.getPicture(), 0 ,currentProduct.getPicture().length);
		
		if(bm==null&&currentProduct.getPicture()!=null){

			ByteArrayInputStream imageStream = new ByteArrayInputStream(currentProduct.getPicture());
			bm= BitmapFactory.decodeStream(imageStream);
		}
		
		ImageView pic =(ImageView)convertView.findViewById(R.id.bidthumb);
		if(pic!=null)
		pic.setImageBitmap(bm);
		

		
		java.util.Date date = null;
		
		SimpleDateFormat formatter, FORMATTER;
		formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String oldDate = currentProduct.getEndingTime();
		 try {
			date = formatter.parse(oldDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FORMATTER = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		if(date!=null)
		 ((TextView)convertView.findViewById(R.id.endDate)).setText(FORMATTER.format(date));
		else
			 ((TextView)convertView.findViewById(R.id.endDate)).setText(currentProduct.getEndingTime());

		
		
	

		return convertView;
		
	}
}
