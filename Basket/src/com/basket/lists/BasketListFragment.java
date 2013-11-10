package com.basket.lists;



import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.CalendarContract.Events;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.basket.adapters.BasketAdapter;
import com.basket.containers.BasketSession;
import com.basket.general.BidEvent;
import com.basket.general.BuyEvent;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.Event;
import com.basket.general.ProductBasket;
import com.basket.restrequest.NewBasketRequest;
import com.basket.restrequest.UpdateBasketRequest;
import com.example.basket.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class BasketListFragment extends android.app.ListFragment
{
	private ArrayList<ProductBasket> foundBaskets;
	private Animator mCurrentAnimator;
	private int mShortAnimationDuration;
	private RelativeLayout layout;
	private int currentPos=0;
	private SpiceManager spiceManager = new SpiceManager(CarJsonSpringAndroidSpiceService.class);
	ProductBasket defaultPB;
	private boolean test = true;
	public void onCreate(Bundle savedInstance)
	{
		foundBaskets= BasketSession.getUser().getBaskets();	
		if(foundBaskets.size()==0){
			defaultPB = new ProductBasket();
			defaultPB.setBidEvents(new ArrayList<BidEvent>());
			defaultPB.setBuyEvents(new ArrayList<BuyEvent>());
			defaultPB.setName("Default");
			if(!spiceManager.isStarted())
				spiceManager.start(getActivity());					
			NewBasketRequest JsonSpringAndroidRequest = new NewBasketRequest(defaultPB);
			BasketSession.getUser().getBaskets().add(defaultPB);
			spiceManager.execute(JsonSpringAndroidRequest, "Basket_Update", DurationInMillis.ALWAYS_EXPIRED, new NewBasketListener());

		}

		super.onCreate(savedInstance);
		foundBaskets= BasketSession.getUser().getBaskets();	

		getActivity().setTitle("Basket List");
		BasketAdapter adapter = new BasketAdapter(this.getActivity(),foundBaskets);
		this.setListAdapter(adapter);
		layout= new RelativeLayout(this.getActivity());
		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
		this.getActivity().addContentView(layout, rlp);
	}
	public void onListItemClick(ListView l, View v, int pos ,  long id )
	{
		spiceManager.start(getActivity());
		currentPos=pos;

		boolean found = false;
		for(int i = 0; i<BasketSession.getUser().getBaskets().get(currentPos).getBuyEvents().size();i++)
		{
			List<BuyEvent> list = BasketSession.getUser().getBaskets().get(currentPos).getBuyEvents();
			List<Event> inList = BasketSession.getProductSearch();
			int in = BasketSession.getUser().getBaskets().get(currentPos).getBuyEvents().get(i).getId();
			int out = ((BuyEvent) BasketSession.getProductSearch().get(getActivity().getIntent().getIntExtra("selected", 0))).getId();
			if( in == out)
			{
				BasketSession.getUser().getBaskets().get(currentPos).getBuyEvents().get(i).setitem_quantity(BasketSession.getUser().getBaskets().get(currentPos).getBuyEvents().get(i).getitem_quantity()+1);
				found = true;
				break;
			}
		}
		
		if(!found)
		{
			BasketSession.getUser().getBaskets().get(currentPos).getBuyEvents().add((BuyEvent) BasketSession.getProductSearch().get(getActivity().getIntent().getIntExtra("selected", 0)));
		}
	
		UpdateBasketRequest JsonSpringAndroidRequest = new UpdateBasketRequest(pos,foundBaskets.get(pos));
		spiceManager.execute(JsonSpringAndroidRequest, "Basket_Update", DurationInMillis.ALWAYS_EXPIRED, new BasketUpdateListener());

	}

	private void zoomImageFromThumb(final View thumbView, int imageResId) {
		// If there's an animation in progress, cancel it immediately and proceed with this one.
		if (mCurrentAnimator != null) {
			mCurrentAnimator.cancel();
		}

		// Load the high-resolution "zoomed-in" image.
		final View expandedImageView = thumbView;


		// Calculate the starting and ending bounds for the zoomed-in image. This step
		// involves lots of math. Yay, math.
		final Rect startBounds = new Rect();
		final Rect finalBounds = new Rect();
		final Point globalOffset = new Point();

		// The start bounds are the global visible rectangle of the thumbnail, and the
		// final bounds are the global visible rectangle of the container view. Also
		// set the container view's offset as the origin for the bounds, since that's
		// the origin for the positioning animation properties (X, Y).
		thumbView.getGlobalVisibleRect(startBounds);
		// thumbView.findViewById(R.id.fragmentContainer).getGlobalVisibleRect(finalBounds, globalOffset);
		startBounds.offset(-globalOffset.x, -globalOffset.y);
		finalBounds.offset(-globalOffset.x, -globalOffset.y);

		// Adjust the start bounds to be the same aspect ratio as the final bounds using the
		// "center crop" technique. This prevents undesirable stretching during the animation.
		// Also calculate the start scaling factor (the end scaling factor is always 1.0).
		float startScale;
		if ((float) finalBounds.width() / finalBounds.height()
				> (float) startBounds.width() / startBounds.height()) {
			// Extend start bounds horizontally
			startScale = (float) startBounds.height() / finalBounds.height();
			float startWidth = startScale * finalBounds.width();
			float deltaWidth = (startWidth - startBounds.width()) / 2;
			startBounds.left -= deltaWidth;
			startBounds.right += deltaWidth;
		} else {
			// Extend start bounds vertically
			startScale = (float) startBounds.width() / finalBounds.width();
			float startHeight = startScale * finalBounds.height();
			float deltaHeight = (startHeight - startBounds.height()) / 2;
			startBounds.top -= deltaHeight;
			startBounds.bottom += deltaHeight;
		}

		// Hide the thumbnail and show the zoomed-in view. When the animation begins,
		// it will position the zoomed-in view in the place of the thumbnail.
		thumbView.setAlpha(0f);
		expandedImageView.setVisibility(View.VISIBLE);

		// Set the pivot point for SCALE_X and SCALE_Y transformations to the top-left corner of
		// the zoomed-in view (the default is the center of the view).
		expandedImageView.setPivotX(0f);
		expandedImageView.setPivotY(0f);

		// Construct and run the parallel animation of the four translation and scale properties
		// (X, Y, SCALE_X, and SCALE_Y).
		AnimatorSet set = new AnimatorSet();
		set
		.play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left,
				finalBounds.left))
				.with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top,
						finalBounds.top))
						.with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
						.with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f));
		set.setDuration(mShortAnimationDuration);
		set.setInterpolator(new DecelerateInterpolator());
		set.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				mCurrentAnimator = null;
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				mCurrentAnimator = null;
			}
		});
		set.start();
		mCurrentAnimator = set;

		// Upon clicking the zoomed-in image, it should zoom back down to the original bounds
		// and show the thumbnail instead of the expanded image.
		final float startScaleFinal = startScale;
		expandedImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mCurrentAnimator != null) {
					mCurrentAnimator.cancel();
				}

				// Animate the four positioning/sizing properties in parallel, back to their
				// original values.
				AnimatorSet set = new AnimatorSet();
				set
				.play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left))
				.with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
				.with(ObjectAnimator
						.ofFloat(expandedImageView, View.SCALE_X, startScaleFinal))
						.with(ObjectAnimator
								.ofFloat(expandedImageView, View.SCALE_Y, startScaleFinal));
				set.setDuration(mShortAnimationDuration);
				set.setInterpolator(new DecelerateInterpolator());
				set.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						thumbView.setAlpha(1f);
						expandedImageView.setVisibility(View.GONE);
						mCurrentAnimator = null;
					}

					@Override
					public void onAnimationCancel(Animator animation) {
						thumbView.setAlpha(1f);
						expandedImageView.setVisibility(View.GONE);
						mCurrentAnimator = null;
					}
				});
				set.start();
				mCurrentAnimator = set;
			}
		});
	}

	public class MyRenderer extends RelativeLayout {
		public MyRenderer(Context context) 
		{
			super(context);
			View e = View.inflate(context, R.layout.basket_overview, null);

			RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			//

			addView(e,rlp);
		}

	}
	private class BasketUpdateListener implements RequestListener<Boolean>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {


				Toast.makeText(getActivity(), "Item could not be added", Toast.LENGTH_SHORT).show();
			}
			Toast.makeText(getActivity(), "Item  added", Toast.LENGTH_SHORT).show();
			//BasketSession.getUser().getBaskets().get(currentPos).getBuyEvents().remove(BasketSession.getProductSearch().get(getActivity().getIntent().getIntExtra("selectedEvent", 0)));
			if (spiceManager.isStarted()) {
				spiceManager.shouldStop();

			}
		}

		@Override
		public void onRequestSuccess(Boolean bool) 
		{


			spiceManager.shouldStop();
			Toast.makeText(getActivity(), "Product Added to Basket", Toast.LENGTH_SHORT).show();

			getActivity().finish();

		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}
	}
	private class NewBasketListener implements RequestListener<Boolean>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				Toast.makeText(getActivity(), "Basket could not be created", Toast.LENGTH_SHORT).show();
			}
			BasketSession.getUser().getBaskets().remove(BasketSession.getUser().getBaskets().size()-1);
			spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(Boolean bool) 
		{


			spiceManager.shouldStop();
			//BasketSession.getUser().getBaskets().add(defaultPB);
			Toast.makeText(getActivity(), "Basket Created", Toast.LENGTH_SHORT).show();
			test = false;
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}
	}
}
