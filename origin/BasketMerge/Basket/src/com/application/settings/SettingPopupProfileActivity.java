package com.application.settings;

import com.example.basket.R;
import com.example.basket.R.layout;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class SettingPopupProfileActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		WindowManager.LayoutParams params = getWindow().getAttributes();  
		params.x = -100;  
		params.height = 200;  
		params.width = 400;  
		params.y = -50;  

		this.getWindow().setAttributes(params);  
		SettingPopupProfileActivity.this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_setting_baskets_popup);

	}


}
