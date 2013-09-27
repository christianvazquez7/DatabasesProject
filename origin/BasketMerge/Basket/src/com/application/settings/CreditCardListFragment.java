package com.application.settings;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.basket.R;

public class CreditCardListFragment extends ListFragment {
	private ArrayList<CreditCard> creditcards;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.creditcard_list, container, false);
		creditcards = CreditCardContainer.userCreditCards;
		setListAdapter(new CreditCardListAdapter(getActivity(), creditcards));


		return rootView;
	}
	public void onListItemClick(ListView l, View v, int pos ,  long id ){
		CreditCardContainer.paymentSelection = creditcards.get(pos);
		CheckoutActivity.changeCreditCard = true;
		this.getActivity().finish();
		
	}
}
