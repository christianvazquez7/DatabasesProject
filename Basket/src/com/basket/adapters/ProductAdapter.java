package com.basket.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.basket.general.BidEvent;
import com.basket.general.BuyEvent;
import com.basket.general.Event;
import com.example.basket.R;

public class ProductAdapter extends ArrayAdapter<Event>
{
	private Context context;
	public ProductAdapter(Context activity,ArrayList<Event> products)
	{
		super(activity,0,products);
		context=activity;
	}
	public View getView(int pos,View convertView, ViewGroup parent)
	{
		if(this.getItem(pos).isBid()){
			if (convertView==null)
			{
				convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.bidproduct_view, null);

			}
			BidEvent currentProduct = (BidEvent) this.getItem(pos);	

			((TextView)convertView.findViewById(R.id.bidproduct)).setText(currentProduct.getProduct().getName());
			((TextView)convertView.findViewById(R.id.bidprice)).setText("$"+Double.toString(currentProduct.getWinning().getAmmount()));
			((TextView)convertView.findViewById(R.id.bidsupplier)).setText(currentProduct.getProduct().getManufacturer());
			((TextView)convertView.findViewById(R.id.endDate)).setText(currentProduct.getFday()+"/"+currentProduct.getFmonth()+"/"+currentProduct.getFyear());

		}
		else{
			if (convertView==null)
			{
				convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.product_view, null);

			}
			BuyEvent currentProduct = (BuyEvent) this.getItem(pos);	

			((TextView)convertView.findViewById(R.id.product)).setText(currentProduct.getProduct().getName());
			((TextView)convertView.findViewById(R.id.price)).setText("$"+Double.toString(currentProduct.getPrice()));
			((TextView)convertView.findViewById(R.id.supplier)).setText(currentProduct.getProduct().getManufacturer());
		}
		




		//((TextView)convertView.findViewById(R.id.product))
		return convertView;

	}
}
