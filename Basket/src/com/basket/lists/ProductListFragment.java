package com.basket.lists;

import java.util.ArrayList;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.basket.activities.BidEventPageActivity;
import com.basket.activities.BuyEventPageActivity;
import com.basket.adapters.ProductAdapter;
import com.basket.containers.BasketSession;
import com.basket.general.BidEvent;
import com.basket.general.BuyEvent;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.Event;
import com.basket.general.ReviewList;
import com.basket.restrequest.GetReviewsRequest;
import com.example.basket.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class ProductListFragment extends android.app.ListFragment
{
	private SpiceManager spiceManager= new SpiceManager(CarJsonSpringAndroidSpiceService.class);
	private int pos;
	private ArrayList<Event> foundProducts;
//	private Animator mCurrentAnimator;
//	private int mShortAnimationDuration;
	private RelativeLayout layout;
//	private MyRenderer selectedRenderer;
//	private boolean out = false;
//	private View previousView;
//	private MyRenderer prev;
//	private boolean remove;
//	private Animation centerAni;

	public void onCreate(Bundle savedInstance)
	{
		foundProducts= new ArrayList<Event>();
		super.onCreate(savedInstance);
		getActivity().setTitle("Product List");
		
		ProductAdapter adapter = new ProductAdapter(this.getActivity(),foundProducts);
		BasketSession.setProducts(foundProducts);
		this.setListAdapter(adapter);
		layout= new RelativeLayout(this.getActivity());
		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
		this.getActivity().addContentView(layout, rlp);

		


	}
	public void clear()
	{
		foundProducts.clear();
		
	}
	public boolean addEvent(Event event)
	{
		foundProducts.add(event);
		return true;
	}
	public void onListItemClick(ListView l, View v, int posi ,  long id )
	{
//		if (((ProductFragmentActivity)this.getActivity()).isHandlingRequest())
//		{
//			((ProductFragmentActivity)this.getActivity()).stopSpice();
//		}
//		//this.zoomImageFromThumb(v, R.layout.zoom);
//		// parent container
//
//		int t = v.getTop() + l.getTop();
//		int ls = v.getLeft() + l.getLeft();
//
//		// create a copy of the listview and add it to the parent
//		// container
//		// at the same location it was in the listview
//		if(!out){
//			selectedRenderer = new MyRenderer(v.getContext());
//			prev=selectedRenderer;
//
//			RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(v.getWidth(),l.getHeight() );
//			rlp.topMargin = t;
//			rlp.leftMargin = ls;
//			layout.addView(selectedRenderer, rlp);
//			v.setVisibility(View.INVISIBLE);
//		}
//
//		// animate out the listView
//		Animation outAni;
//		if (!out){
//
//			centerAni = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,
//					Animation.RELATIVE_TO_SELF,0f,Animation.ABSOLUTE,-t);
//			centerAni.setZAdjustment(Animation.ZORDER_TOP);
//			centerAni.setFillAfter(true);
//			centerAni.setDuration(500);
//			previousView=v;
//			outAni = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
//					Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f,
//					Animation.RELATIVE_TO_SELF, 0f);
//			out=true;
//			outAni.setDuration(500);
//			outAni.setFillAfter(true);
//			l.startAnimation(outAni);
//			selectedRenderer.startAnimation(centerAni);
//
//		}
//		else{
//
//			outAni = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1f,
//					Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
//					Animation.RELATIVE_TO_SELF, 0f);
//			if (previousView!=null)
//				previousView.setVisibility(View.VISIBLE);
//			out=false;
//			outAni.setDuration(500);
//			outAni.setFillAfter(true);
//			l.startAnimation(outAni);
//
//
//		}
//		//        outAni.setDuration(500);
//		//        outAni.setFillAfter(true);
//		outAni.setAnimationListener(new Animation.AnimationListener() {
//			@Override
//			public void onAnimationStart(Animation animation) {
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				//                ScaleAnimation scaleAni;
//				//                
//				//                scaleAni= new ScaleAnimation(1f, 
//				//                        1f, 1f, 2f, 
//				//                        Animation.RELATIVE_TO_SELF, 0.5f,
//				//                        Animation.RELATIVE_TO_SELF, 0.5f);
//				//                
//				//                	
//				//                scaleAni.setDuration(400);
//				//                scaleAni.setFillAfter(true);
//				//                selectedRenderer.startAnimation(scaleAni);
//
//				if(!out)
//					layout.removeView(prev);
//
//			}
//		});
		this.pos=posi;
		if (!spiceManager.isStarted())
		{
			spiceManager.start(getActivity());
			GetReviewsRequest a;
			if(this.foundProducts.get(pos).isBid()) 
			{
				BidEvent be= (BidEvent) this.foundProducts.get(pos);
				a = new GetReviewsRequest(be.getId(),0);
			}
			else
			{
				BuyEvent be= (BuyEvent) this.foundProducts.get(pos);
				a = new GetReviewsRequest(be.getId(),1);
			}
			spiceManager.execute(a, "", DurationInMillis.ALWAYS_EXPIRED, new GetReviewsListener());
		}
		
		
	}

	
	public class MyRenderer extends RelativeLayout {



		public MyRenderer(Context context) 
		{
			super(context);
			View e = View.inflate(context, R.layout.product_view, null);

			RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			//

			addView(e,rlp);
		}

	}
	public void onResume() {
		  //onResume happens after onStart and onActivityCreate
		LayoutTransition l = new LayoutTransition();
		l.enableTransitionType(LayoutTransition.CHANGING);
		l.setDuration(500);
		ViewGroup viewGroup = this.getListView();
		viewGroup.setLayoutTransition(l);
		  super.onResume() ; 
		}
	
	private class GetReviewsListener implements RequestListener<ReviewList>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				Toast.makeText(getActivity(), "No connection to server", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(ReviewList reviews) 
		{
			spiceManager.shouldStop();
			BasketSession.setReviews(reviews.getReviews());
			
			
			if(foundProducts.get(pos).isBid())
			{
				Intent productPage = new Intent(getActivity(),BidEventPageActivity.class);
				productPage.putExtra("selectedEvent",pos);
				startActivityForResult(productPage, 0);
			}
			else{
				Intent productPage = new Intent(getActivity(),BuyEventPageActivity.class);
				productPage.putExtra("selectedEvent",pos);
				startActivityForResult(productPage, 0);
			}
			
			

		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}


	}

}
