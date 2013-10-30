package com.basket.adapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.basket.general.BidEvent;
import com.example.basket.R;

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
			if (currentProduct.isFinalized())
				convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.bidproduct_view4, null);			
			
		}
		
		BidEvent currentProduct = this.getItem(pos);	
		((TextView)convertView.findViewById(R.id.bidproduct)).setText(currentProduct.getProduct().getName());
		((TextView)convertView.findViewById(R.id.bidsupplier)).setText("From "+currentProduct.getProduct().getManufacturer());
	    final RatingBar minimumRating = (RatingBar)convertView.findViewById(R.id.bidratingBar1);
	    minimumRating.setRating(currentProduct.getRating());
		if(currentProduct.isFinalized()){
			if(currentProduct.getWinningBid()==null){
				((TextView)convertView.findViewById(R.id.bidprice)).setText(Double.toString(currentProduct.getStartingBid()));

			}
			else
				((TextView)convertView.findViewById(R.id.bidprice)).setText(Double.toString(currentProduct.getWinningBid().getAmmount()));

		}
		else
			((TextView)convertView.findViewById(R.id.bidprice)).setText(Double.toString(currentProduct.getStartingBid()));
		
		
		
		Log.d("try",currentProduct.getEndingTime());

		
		String datetimeString=currentProduct.getEndingTime();
//		Date result = null;
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
//		try {
//			result = formatter.parse (datetimeString);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		((TextView)convertView.findViewById(R.id.endDate)).setText(currentProduct.getEndingTime());

		
		
		//Bitmap bm = BitmapFactory.decodeByteArray(currentProduct.getPicture()., 0 ,currentProduct.getPicture().length);
//		ImageView pic =(ImageView)convertView.findViewById(R.id.bidthumb);
		//pic.setImageBitmap(bm);


		return convertView;
		
	}
}
