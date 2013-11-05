package com.basket.adapters;

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
import com.basket.general.BuyEvent;
import com.basket.general.Products;
import com.example.basket.R;

public class ProductsInMyShopBuyAdapter extends ArrayAdapter<BuyEvent>
{
	private Context context;
	public ProductsInMyShopBuyAdapter(Context activity,ArrayList<BuyEvent> products)
	{
		super(activity,0,products);
		context=activity;
	}
	public View getView(int pos,View convertView, ViewGroup parent)
	{
		if (convertView==null)
		{
			convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.product_view, null);
			
		}
		BuyEvent currentProduct = this.getItem(pos);
		
		((TextView)convertView.findViewById(R.id.product)).setText(currentProduct.getProduct().getName());
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
		
		return convertView;
		
	}
}
