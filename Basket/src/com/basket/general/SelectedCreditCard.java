package com.basket.general;

import com.basket.containers.CreditCardContainer;
import com.example.basket.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.TextView;

public class SelectedCreditCard extends Fragment {
	private TextView billingAddress, ccNumber, secNum, expMonth, expYear, ccName;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.creditcard_view,container, false);
		((TextView)view.findViewById(R.id.nameOnCardTextView)).setText(CreditCardContainer.paymentSelection.getName());
		((TextView)view.findViewById(R.id.creditCardNumberTextView)).setText(Long.toString(CreditCardContainer.paymentSelection.getCardNum()));
		//Tira un error
		//((TextView)view.findViewById(R.id.creditCardExpMonthTextView)).setText(CreditCardContainer.paymentSelection.getExpMonth());
		//((TextView)view.findViewById(R.id.creditCardExpYearTextView)).setText(CreditCardContainer.paymentSelection.getExpYear());

		
		return view;
	}
}
