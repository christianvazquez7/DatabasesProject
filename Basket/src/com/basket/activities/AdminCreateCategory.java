package com.basket.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.basket.containers.BasketSession;
import com.basket.general.Category;
import com.example.basket.R;

public class AdminCreateCategory extends Activity {
	private Spinner mSpinner;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_category);
		mSpinner = (Spinner) findViewById(R.id.maincatspin);
		ArrayList<String> catName = new ArrayList<String>();
		for(Category c:BasketSession.getCategory()){
			if(c.getParent()!=null){
				if(c.getParent().getParent()!=null){
					continue;
				}
			}
			else{
				//				if(c.getChild()!= null && c.getChild().size()>0){
				//					for(Category cat: c.getChild()){
				//						catName.add(cat.getName());
				//					}
				//				}
				catName.add(c.getName());
				ArrayList<Category> arr = c.getChild();
				if(arr!=null&&arr.size()>0){
					for(Category d : c.getChild()){
						catName.add(d.getName());
					}
				}
			}


		}
		ArrayAdapter<String> catNameAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item,catName);
		catNameAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(catNameAdapter);
	}

}
