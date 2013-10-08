package com.basket.lists;

import android.animation.LayoutTransition;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.basket.activities.BasketActivity;
import com.basket.activities.CheckoutActivity;
import com.basket.adapters.ProductBuyAdapter;
import com.basket.containers.BasketSession;
import com.example.basket.R;

public class ProductsInBuyBasketsList extends ListFragment{
	public static int basketnum =0;
	private Button mButton;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.basketproductlist, container, false);

		mButton = (Button) rootView.findViewById(R.id.basketCheckoutButton);
		mButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(BasketSession.getUser().getBaskets().get(ProductsInBuyBasketsList.this.getArguments().getInt("pos")).getBuyEvents().size()==0){
					Toast.makeText(ProductsInBuyBasketsList.this.getActivity(), "Empty basket", Toast.LENGTH_LONG).show();
				}
				else{
					Intent i = new Intent(ProductsInBuyBasketsList.this.getActivity(), CheckoutActivity.class);
					i.putExtra("basketNum", getArguments().getInt("pos"));
					i.putExtra("CurrentListItem", BasketActivity.currentPagePager.getCurrentItem());
					i.putExtra("BuyEvent", true);
					//i.putExtra("BidEvent",true);
					startActivity(i);
				}

			}
		});



		//list_items = getResources().getStringArray(R.array.list);
		if(this.getArguments().getInt("pos") < BasketSession.getUser().getBaskets().size())
		setListAdapter(new ProductBuyAdapter(getActivity(), BasketSession.getUser().getBaskets().get(this.getArguments().getInt("pos")).getBuyEvents(),BasketSession.getUser().getBaskets().get(this.getArguments().getInt("pos")),this.getArguments().getInt("pos") ));
		//setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list_items));

		return rootView;
	}
	public void onResume()
	{
		super.onResume();
		LayoutTransition l = new LayoutTransition();
		l.enableTransitionType(LayoutTransition.CHANGING);
		l.setDuration(500);
		ViewGroup viewGroup = (ViewGroup) this.getListView();
		viewGroup.setLayoutTransition(l);

		ArrayAdapter a =((ArrayAdapter)this.getListAdapter());
		if (a!=null)
			a.notifyDataSetChanged();
		BasketActivity.currentPagePager.getAdapter().notifyDataSetChanged();




	}

}
