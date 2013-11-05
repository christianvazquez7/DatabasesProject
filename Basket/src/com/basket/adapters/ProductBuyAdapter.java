package com.basket.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.basket.activities.BasketActivity;
import com.basket.containers.BasketSession;
import com.basket.general.BuyEvent;
import com.basket.general.CarJsonSpringAndroidSpiceService;
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

public class ProductBuyAdapter extends ArrayAdapter<BuyEvent>
{
	private ArrayList<BuyEvent> content;
	private Context context;
	private int pos3;
	private ProductBasket inBasket;
	private SpiceManager spiceManager=new SpiceManager(CarJsonSpringAndroidSpiceService.class);;
	
	public ProductBuyAdapter(Context activity,ArrayList<BuyEvent> products,ProductBasket baskets,int pos3)
	{
		super(activity,0,products);
		context=activity;
		content = products;
		this.pos3=pos3;
		inBasket=baskets;
	}
	public View getView(int pos,View convertView, ViewGroup parent)
	{
		final int pos2 = pos;
		if (convertView==null)
		{
			convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.product_view_buybasket, null);
		}
		BuyEvent currentProduct = (BuyEvent) this.getItem(pos);	
		((ImageView)convertView.findViewById(R.id.removeButton)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				//Remove product
				if (!spiceManager.isStarted()){
				spiceManager.start(context);
				ProductBuyAdapter.this.content.remove(pos2);
				UpdateBasketRequest JsonSpringAndroidRequest = new UpdateBasketRequest(pos3,inBasket);
				spiceManager.execute(JsonSpringAndroidRequest, "Basket_Update", DurationInMillis.ALWAYS_EXPIRED, new DeleteBasketListener());
				ProductBuyAdapter.this.notifyDataSetChanged();
				}
				
				
			}
		});
		((TextView)convertView.findViewById(R.id.product)).setText(currentProduct.getTitle());
		((TextView)convertView.findViewById(R.id.price)).setText("$"+Double.toString(currentProduct.getPrice()));
		((TextView)convertView.findViewById(R.id.supplier)).setText(currentProduct.getProduct().getManufacturer());
		((TextView)convertView.findViewById(R.id.pod)).setText(currentProduct.getProduct().getName());

		final RatingBar minimumRating = (RatingBar)convertView.findViewById(R.id.ratingBar1);
	    minimumRating.setRating(currentProduct.getRating());

		return convertView;

	}
	private class DeleteBasketListener implements RequestListener<Boolean>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {
			
			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {
				
				Toast.makeText(context, "product could not be removed", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(Boolean bool) 
		{

			
			spiceManager.shouldStop();
			Toast.makeText(context, "Product Deleted", Toast.LENGTH_SHORT).show();
				
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{
		
		}
	}
}
