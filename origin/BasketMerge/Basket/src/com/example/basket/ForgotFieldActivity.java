package com.example.basket;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ForgotFieldActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_field);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}


	
}
