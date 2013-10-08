package com.basket.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.basket.general.BidEvent;
import com.basket.general.Products;
import com.example.basket.R;

public class ProductInMyShopBidAdapter extends ArrayAdapter<BidEvent>
{
	private Context context;
	public ProductInMyShopBidAdapter(Context activity,ArrayList<BidEvent> products)
	{
		super(activity,0,products);
		context=activity;
	}
	public View getView(int pos,View convertView, ViewGroup parent)
	{
		if (convertView==null)
		{
			BidEvent currentProduct = this.getItem(pos);	
			convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.bidproduct_view3, null);
			if (currentProduct.isFinalized())
				convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.bidproduct_view4, null);			
			
		}
		
		BidEvent currentProduct = this.getItem(pos);	
		((TextView)convertView.findViewById(R.id.bidproduct)).setText(currentProduct.getProduct().getName());
		((TextView)convertView.findViewById(R.id.bidsupplier)).setText("From "+currentProduct.getProduct().getManufacturer());
		if(currentProduct.isFinalized()){
			if(currentProduct.getWinning()==null){
				((TextView)convertView.findViewById(R.id.bidprice)).setText(Double.toString(15.82));

			}
			else
				((TextView)convertView.findViewById(R.id.bidprice)).setText(Double.toString(currentProduct.getWinning().getAmmount()));

		}
		else
			((TextView)convertView.findViewById(R.id.bidprice)).setText(Double.toString(currentProduct.getStartingBid()));
		if(currentProduct.getReviews()==null){
			((TextView)convertView.findViewById(R.id.bidnumOfRatings)).setText("("+Integer.toString(0)+")");

		}
		else
			((TextView)convertView.findViewById(R.id.bidnumOfRatings)).setText("("+Integer.toString(currentProduct.getReviews().size())+")");
		((TextView)convertView.findViewById(R.id.endDate)).setText(currentProduct.getFday()+"/"+currentProduct.getFmonth()+"/"+currentProduct.getFyear());

		return convertView;
		
	}
}
