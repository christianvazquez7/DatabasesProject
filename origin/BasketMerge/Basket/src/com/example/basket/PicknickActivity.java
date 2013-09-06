package com.example.basket;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class PicknickActivity extends Activity {
	private Button mSetting1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picknick);
		mSetting1 = (Button) findViewById(R.id.Button01);
		mSetting1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(PicknickActivity.this,SettingPopupActivity.class);
				startActivity(i);
				
			}
		});
	}



}
