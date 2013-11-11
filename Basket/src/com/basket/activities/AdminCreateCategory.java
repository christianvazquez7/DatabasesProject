package com.basket.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Spinner;

import com.example.basket.R;

public class AdminCreateCategory extends Activity {
	private Spinner mSpinner;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_category);
		mSpinner = (Spinner) findViewById(R.id.maincatspin);
		
	}
	
}
