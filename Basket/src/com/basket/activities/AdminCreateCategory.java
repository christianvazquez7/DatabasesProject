package com.basket.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.basket.containers.BasketSession;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.Category;
import com.basket.icom.dbclass.R;
import com.basket.restrequest.AdminCreateCatReq;
import com.basket.restrequest.GetCatParent;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class AdminCreateCategory extends Activity {
	private Spinner mSpinner;
	private SpiceManager spiceManager=  new SpiceManager(CarJsonSpringAndroidSpiceService.class);
	private EditText catNameET,subCatName;
	private Button catBut, subCatBut;
	private Category c;
	ArrayList<String> catName;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_category);
		catBut = (Button) findViewById(R.id.catCreate);
		subCatBut = (Button) findViewById(R.id.subCatCreate);
		catNameET = (EditText) findViewById(R.id.catName);
		subCatName = (EditText) findViewById(R.id.subCatName);

		catBut.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!spiceManager.isStarted())
				{
					spiceManager.start(AdminCreateCategory.this);
					c = new Category();
					c.setName(catNameET.getText().toString());
					spiceManager.execute(new AdminCreateCatReq(c), new AdminCreateCategoryListner());
				}
			}
		});
		subCatBut.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!spiceManager.isStarted())
				{
					spiceManager.start(AdminCreateCategory.this);
					c = new Category();
					c.setName(subCatName.getText().toString());
					Category temp = new  Category();
					temp.setName((String)mSpinner.getSelectedItem());
					c.setParent(temp);
					spiceManager.execute(new GetCatParent(temp), new GetCatParentListner1P());
				}
			}
		});
		mSpinner = (Spinner) findViewById(R.id.maincatspin);
		catName = new ArrayList<String>();
		for(Category c:BasketSession.getCategory()){
			catName.add(c.getName());
		}
		ArrayAdapter<String> catNameAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item,catName);
		catNameAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(catNameAdapter);
	}
	private class AdminCreateCategoryListner implements RequestListener<Boolean>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				Toast.makeText(AdminCreateCategory.this, "Could not create category", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(Boolean newCat) 
		{
			spiceManager.shouldStop();
			if(newCat){
				Toast.makeText(AdminCreateCategory.this, "Success", Toast.LENGTH_SHORT).show();
				BasketSession.getCategory().add(c);
				catName.add(c.getName());
			}
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}


	}
	private class GetCatParentListner1P implements RequestListener<String>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				Toast.makeText(AdminCreateCategory.this, "Could not create sub category", Toast.LENGTH_SHORT).show();
			}
			if(spiceManager.isStarted())
				spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(String catParent) 
		{
			
			//Gets parent's parent
			if(catParent==null||catParent.equals(""))
			{
				spiceManager.execute(new AdminCreateCatReq(c), new AdminCreateCategoryListner());
			}
			else{
				Category parent1 = new Category();
				parent1.setName(catParent);
				spiceManager.execute(new GetCatParent(parent1), new GetCatParentListner2P());
			}

		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}


	}
	private class GetCatParentListner2P implements RequestListener<String>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				Toast.makeText(AdminCreateCategory.this, "Could not create sub category", Toast.LENGTH_SHORT).show();
			}
			if(spiceManager.isStarted())
				spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(String catParent) 
		{
			
			if(catParent==null||catParent.equals("")||catParent.equals("null"))
			{
				spiceManager.execute(new AdminCreateCatReq(c), new AdminCreateCategoryListner());
			}
			else{
				spiceManager.shouldStop();
				Toast.makeText(AdminCreateCategory.this, "Could not create sub category", Toast.LENGTH_LONG).show();
			}

		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}


	}
}
