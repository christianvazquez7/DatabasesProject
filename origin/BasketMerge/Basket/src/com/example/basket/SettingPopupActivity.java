package com.example.basket;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.WindowManager;

public class SettingPopupActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WindowManager.LayoutParams params = getWindow().getAttributes();  
       params.x = -100;  
       params.height = 200;  
       params.width = 400;  
       params.y = -50;  
  
       this.getWindow().setAttributes(params);  
       setContentView(R.layout.activity_setting_popup);

	}


}
