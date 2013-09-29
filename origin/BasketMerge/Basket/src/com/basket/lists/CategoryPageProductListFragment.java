package com.basket.lists;

import java.util.ArrayList;

import com.basket.activities.ProductPageActivity;
import com.basket.adapters.ProductAdapter;
import com.basket.general.BuyEvent;
import com.example.basket.R;
import com.example.basket.R.layout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class CategoryPageProductListFragment extends android.app.ListFragment
{
	private ArrayList<BuyEvent> foundProducts;
	private Animator mCurrentAnimator;
	private int mShortAnimationDuration;
	private RelativeLayout layout;
	private MyRenderer selectedRenderer;
	private boolean out = false;
	private View previousView;
	private MyRenderer prev;
	private boolean remove;
	private Animation centerAni;

	public void onCreate(Bundle savedInstance)
	{
		foundProducts= new ArrayList<BuyEvent>();

		super.onCreate(savedInstance);
		getActivity().setTitle("Category Name Goes Here");

		ProductAdapter adapter = new ProductAdapter(this.getActivity(),foundProducts);
		this.setListAdapter(adapter);
		layout= new RelativeLayout(this.getActivity());
		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
		this.getActivity().addContentView(layout, rlp);




	}
	public void onListItemClick(ListView l, View v, int pos ,  long id )
	{
		//this.zoomImageFromThumb(v, R.layout.zoom);
		// parent container

		int t = v.getTop() + l.getTop();
		int ls = v.getLeft() + l.getLeft();

		// create a copy of the listview and add it to the parent
		// container
		// at the same location it was in the listview
		if(!out){
			selectedRenderer = new MyRenderer(v.getContext());
			prev=selectedRenderer;

			RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(v.getWidth(),l.getHeight() );
			rlp.topMargin = t;
			rlp.leftMargin = ls;
			layout.addView(selectedRenderer, rlp);
			v.setVisibility(View.INVISIBLE);
		}

		// animate out the listView
		Animation outAni;
		if (!out){

			centerAni = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,
					Animation.RELATIVE_TO_SELF,0f,Animation.ABSOLUTE,-t);
			centerAni.setZAdjustment(Animation.ZORDER_TOP);
			centerAni.setFillAfter(true);
			centerAni.setDuration(500);
			previousView=v;
			outAni = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
					Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f,
					Animation.RELATIVE_TO_SELF, 0f);
			out=true;
			outAni.setDuration(500);
			outAni.setFillAfter(true);
			l.startAnimation(outAni);
			selectedRenderer.startAnimation(centerAni);

		}
		else{

			outAni = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1f,
					Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
					Animation.RELATIVE_TO_SELF, 0f);
			if (previousView!=null)
				previousView.setVisibility(View.VISIBLE);
			out=false;
			outAni.setDuration(500);
			outAni.setFillAfter(true);
			l.startAnimation(outAni);


		}
		//        outAni.setDuration(500);
		//        outAni.setFillAfter(true);
		outAni.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				//                ScaleAnimation scaleAni;
				//                
				//                scaleAni= new ScaleAnimation(1f, 
				//                        1f, 1f, 2f, 
				//                        Animation.RELATIVE_TO_SELF, 0.5f,
				//                        Animation.RELATIVE_TO_SELF, 0.5f);
				//                
				//                	
				//                scaleAni.setDuration(400);
				//                scaleAni.setFillAfter(true);
				//                selectedRenderer.startAnimation(scaleAni);

				if(!out)
					layout.removeView(prev);

			}
		});
		Intent productPage = new Intent(this.getActivity(),ProductPageActivity.class);
		this.startActivity(productPage);
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
			View e = View.inflate(context, R.layout.product_view, null);

			RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			//

			addView(e,rlp);
		}

	}

}
