package com.basket.adapters;

import java.util.List;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.basket.general.UserRating;
import com.basket.icom.dbclass.R;


public class UserRatingAdapter extends ArrayAdapter<UserRating>
{private Context context;

public UserRatingAdapter(Context context,
		List<UserRating> objects) 
{
	super(context, 0, objects);
	this.context=context;
	// TODO Auto-generated constructor stub
}

public View getView(int pos,View convertView, ViewGroup parent)
{
	if (convertView==null)
	{
		convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.u_rating_view, null);

	}
	((TextView)convertView.findViewById(R.id.rater)).setText(this.getItem(pos).getRater());
	final RatingBar minimumRating = (RatingBar)convertView.findViewById(R.id.rating);
    minimumRating.setRating(this.getItem(pos).getStars());
	

	return convertView;

}
}
