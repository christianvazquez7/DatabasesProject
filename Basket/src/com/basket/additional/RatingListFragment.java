package com.basket.additional;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.basket.adapters.UserRatingAdapter;
import com.basket.containers.BasketSession;
import com.basket.general.UserRating;

public class RatingListFragment extends ListFragment {
	private ArrayList<UserRating> ratings;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		ratings=BasketSession.getUserRatings();
		super.onCreate(savedInstanceState);
		UserRatingAdapter adapter = new UserRatingAdapter(this.getActivity(),ratings);
		setListAdapter(adapter);
	}
	

}
