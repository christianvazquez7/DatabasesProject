package com.basket.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.basket.general.Review;
import com.example.basket.R;

public class ReviewAdapter extends ArrayAdapter<Review> {
	private Context context;
	public ReviewAdapter(Context activity,ArrayList<Review> products)
	{
		super(activity,0,products);
		context=activity;
	}
	public View getView(int pos,View convertView, ViewGroup parent)
	{
		if (convertView==null)
		{
			convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.review_view, null);
			
		}
		Review currentProduct = this.getItem(pos);
		TextView title = (TextView) convertView.findViewById(R.id.reviewT);
		title.setText(currentProduct.getTitle());
		TextView content = (TextView) convertView.findViewById(R.id.content);
		content.setText(currentProduct.getContent());
		TextView reviewer = (TextView) convertView.findViewById(R.id.reviewer);
		reviewer.setText("-"+currentProduct.getUsername());
		final RatingBar minimumRating = (RatingBar)convertView.findViewById(R.id.bidratingBar1);
	    minimumRating.setRating(currentProduct.getRrating());

	
		
		return convertView;
		
	}
}
