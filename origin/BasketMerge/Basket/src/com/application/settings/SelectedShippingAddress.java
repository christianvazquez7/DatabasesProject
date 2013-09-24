package com.application.settings;

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

public class SelectedShippingAddress extends Fragment {
//	private TextView billingAddress, ccNumber, secNum, expMonth, expYear, ccName;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.shippingaddress_view,container, false);
		
//		billingAddress = (TextView)view.findViewById(R.id.billingAddressTextView2);
//		ccNumber = (TextView) view.findViewById(R.id.creditCardNumberTextView2);
//		secNum = (TextView) view.findViewById(R.id.creditCardSecNumTextView2);
//		expMonth = (TextView) view.findViewById(R.id.creditCardExpMonthTextView2);
//		expYear = (TextView) view.findViewById(R.id.creditCardExpYearTextView2);
//		ccName = (TextView)	view.findViewById(R.id.nameOnCardTextView2);
//		
//		ccName.setText(CreditCardContainer.paymentSelection.getNameOnCard());
//		expYear.setText(Integer.toString(CreditCardContainer.paymentSelection.getExpYear()));
//		expMonth.setText(Integer.toString(CreditCardContainer.paymentSelection.getExpMonth()));
//		secNum.setText(Integer.toString(CreditCardContainer.paymentSelection.getSecNum()));
//		ccNumber.setText(Long.toString(CreditCardContainer.paymentSelection.getCardNumber()));
//		billingAddress.setText(CreditCardContainer.paymentSelection.getBillingAddress());
		
		
		return view;
	}
}
