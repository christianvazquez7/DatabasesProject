package com.basket.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.basket.containers.BasketSession;
import com.basket.general.BidEvent;
import com.basket.general.BuyEvent;
import com.basket.general.Event;
import com.basket.general.Order;
import com.basket.lists.ProductListFragment;
import com.basket.lists.Products_List_View;
import com.example.basket.R;

public class InvoiceFragment extends Fragment 
{
	private Event theEvent;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		int pos=this.getArguments().getInt("pos");
		View view = inflater.inflate(R.layout.invoice,
				container, false);
		
		android.app.Fragment fragment = this.getChildFragmentManager().findFragmentById(R.id.fragmentContainer);
		if (fragment==null)
		{
			Products_List_View productList = new Products_List_View();
			fragment = productList;
			Bundle bundle = new Bundle();
			bundle.putInt("pos", this.getArguments().getInt("pos"));
			fragment.setArguments(bundle);
			this.getChildFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragment).commit();
		}
		Order o = BasketSession.getUser().getUserOrders().get(pos);
		
		TextView t = (TextView) view.findViewById(R.id.dtime);
		t.setText(o.getsDate());
		
		TextView t2 = (TextView) view.findViewById(R.id.l1);
		t2.setText(o.getShipAdress().getLine1());
		
		TextView t3 = (TextView) view.findViewById(R.id.l2);
		t3.setText(o.getShipAdress().getLine2());
		
		TextView t4 = (TextView) view.findViewById(R.id.city);
		t4.setText(o.getShipAdress().getCity());
		
		TextView t5 = (TextView) view.findViewById(R.id.country);
		t5.setText(o.getShipAdress().getCountry());
		
		TextView t6 = (TextView) view.findViewById(R.id.zip);
		t6.setText(Integer.toString(o.getShipAdress().getZipCode()));
		
		TextView t7 = (TextView) view.findViewById(R.id.state);
		t7.setText(o.getShipAdress().getState());
		
		TextView t8 = (TextView) view.findViewById(R.id.cardnu);
		t8.setText(Long.toString(o.getCreditCard().getCardNum()));
		
		
		
			
		return view;
	}

	public void setEvent(Event currentEvent) 
	{
		// TODO Auto-generated method stub
		this.theEvent=currentEvent;
	}
}
