package com.basket.fragments;

import com.basket.activities.CreditCardSelectActivity;
import com.example.basket.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;

public class CreditCardButton extends Fragment {
	private Button mCreditCardSelectButton;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.select_creditcard_frag_button,container, false);
		
		mCreditCardSelectButton = (Button) view.findViewById(R.id.creditcardselectbuttonfrag);
		mCreditCardSelectButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(CreditCardButton.this.getActivity(), CreditCardSelectActivity.class);
				startActivityForResult(i,1);
			}
		});
		return view;
	}
}
