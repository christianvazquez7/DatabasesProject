package com.basket.lists;

import java.util.ArrayList;

import com.basket.adapters.ReviewAdapter;
import com.basket.containers.BasketSession;
import com.basket.general.Review;
import com.example.basket.R;
import com.example.basket.R.drawable;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ListView;

public class ReviewListFragment extends ListFragment
{
	private ArrayList<Review> reviews;
	
	public void onCreate(Bundle savedInstance)
	{
		
		super.onCreate(savedInstance);
		
		reviews = BasketSession.getReviewSearch();
		
	
		getActivity().setTitle("Review List");
		ReviewAdapter adapter = new ReviewAdapter(this.getActivity(),reviews);
		this.setListAdapter(adapter);
//	ListView lv =	(ListView)this.getActivity().findViewById(android.R.id.list);
//	lv.setDivider(this.getResources().getDrawable(R.drawable.custom_divider));
		
		
		
		
		
		
	}
	public void onResume() {
		  //onResume happens after onStart and onActivityCreate
		this.getListView().setDivider(this.getResources().getDrawable(R.drawable.custom_divider));
		this.getListView().setDividerHeight(1);
		  super.onResume() ; 
		}
	  
	
	
		
	
}
