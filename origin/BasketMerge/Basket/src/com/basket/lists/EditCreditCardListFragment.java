package com.basket.lists;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.basket.activities.CheckoutActivity;
import com.basket.activities.EditCreditCardsActivity;
import com.basket.activities.EditSingleCCActivity;
import com.basket.adapters.CreditCardListAdapter;
import com.basket.containers.BasketSession;
import com.basket.containers.CreditCardContainer;
import com.basket.general.CreditCard;
import com.example.basket.R;

public class EditCreditCardListFragment extends ListFragment {
	private ArrayList<CreditCard> creditcards;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.creditcard_list, container, false);
		creditcards = BasketSession.getUser().getCreditCards();
		setListAdapter(new CreditCardListAdapter(getActivity(), creditcards));


		return rootView;
	}
	public void onListItemClick(ListView l, View v, int pos ,  long id ){
		final int selectedCC = pos;
		Intent newIntent = new Intent(this.getActivity(), EditSingleCCActivity.class);
		newIntent.putExtra("selectedUser", this.getActivity().getIntent().getIntExtra("selectedUser", 0));
		newIntent.putExtra("selectedCreditCard", selectedCC);
		startActivity(newIntent);
		
		
	}
}
