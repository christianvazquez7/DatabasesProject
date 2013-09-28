package com.basket.activities;

import com.example.basket.R;
import com.example.basket.R.layout;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.WindowManager;

public class SettingPopupBasketsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WindowManager.LayoutParams params = getWindow().getAttributes();  
		params.x = -100;  
		params.height = 200;  
		params.width = 400;  
		params.y = -50;  

		this.getWindow().setAttributes(params);  
		setContentView(R.layout.activity_setting_profile_popup);

	}


}
