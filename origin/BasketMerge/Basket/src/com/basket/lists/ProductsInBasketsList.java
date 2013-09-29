package com.basket.lists;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.basket.activities.BasketActivity;
import com.basket.activities.CheckoutActivity;
import com.basket.adapters.ProductsInBasketAdapter;
import com.basket.containers.BasketSession;
import com.example.basket.R;

public class ProductsInBasketsList extends ListFragment{
	public static int basketnum =0;
	private Button mButton;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.basketproductlist, container, false);
		
		mButton = (Button) rootView.findViewById(R.id.basketCheckoutButton);
		mButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ProductsInBasketsList.this.getActivity(), CheckoutActivity.class);
				i.putExtra("CurrentListItem", BasketActivity.currentPagePager.getCurrentItem());
				i.putExtra("BuyEvent", true);
				//i.putExtra("BidEvent",true);
				startActivity(i);
				
			}
		});
		
		//list_items = getResources().getStringArray(R.array.list);
		setListAdapter(new ProductsInBasketAdapter(getActivity(), BasketSession.getUser().getBaskets().get(basketnum).getBuyEvents()));
		//setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list_items));
		
		return rootView;
	}

}
