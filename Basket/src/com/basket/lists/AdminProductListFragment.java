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

import com.basket.activities.AdminProductDateSaleActivity;
import com.basket.activities.BidEventPageActivity;
import com.basket.activities.BuyEventPageActivity;
import com.basket.adapters.AdminProductAdapter;
import com.basket.containers.AdminSession;
import com.basket.containers.BasketSession;
import com.basket.general.BidEvent;
import com.basket.general.BuyEvent;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.Product;
import com.basket.general.ReviewList;
import com.basket.restrequest.GetReviewsRequest;
import com.basket.restrequest.ProductReportRequest;
import com.example.basket.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class AdminProductListFragment extends android.app.ListFragment
{
	private SpiceManager spiceManager= new SpiceManager(CarJsonSpringAndroidSpiceService.class);
	private int pos;
	private ArrayList<Product> foundProducts;
	private RelativeLayout layout;

	public void onCreate(Bundle savedInstance)
	{
		foundProducts= new ArrayList<Product>();
		super.onCreate(savedInstance);
		getActivity().setTitle("Product List");
		AdminProductAdapter adapter = new AdminProductAdapter(this.getActivity(),foundProducts);
		AdminSession.setProducts(foundProducts);

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
	public void onListItemClick(ListView l, View v, int posi ,  long id )
	{
		Intent i = new Intent(AdminProductListFragment.this.getActivity(), AdminProductDateSaleActivity.class);
		i.putExtra("selected", foundProducts.get(posi).getProductPId());
		startActivityForResult(i, 0);
	}


	public class MyRenderer extends RelativeLayout {
		public MyRenderer(Context context) 
		{
			super(context);
			View e = View.inflate(context, R.layout.product_view, null);

			RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
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



}
