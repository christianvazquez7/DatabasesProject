package com.example.basket;

import java.util.ArrayList;

import com.basket.adapters.ProductAdapter;
import com.basket.adapters.UserRatingAdapter;
import com.basket.containers.BasketSession;
import com.basket.general.Event;
import com.basket.general.UserRating;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

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
