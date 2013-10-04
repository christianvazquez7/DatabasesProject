package com.basket.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.basket.R;

public class ChooseSubSubCategoryActivity extends Activity {

	private ListView lvSubSubCatList;
	private ArrayList<String> subSubCategoryList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_subsubcategory);

		subSubCategoryList = this.getIntent().getExtras().getStringArrayList("subcategoryList");

		lvSubSubCatList = (ListView) findViewById(R.id.lvSubSubCatList);
		
		final StableArrayAdapter adapter = new StableArrayAdapter(this, 
				android.R.layout.simple_list_item_1, subSubCategoryList);
		lvSubSubCatList.setAdapter(adapter);
		
		lvSubSubCatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				final String subsubCategory = parent.getItemAtPosition(position).toString();
				Intent i = new Intent(ChooseSubSubCategoryActivity.this, CategoryPageActivity.class);
				i.putExtra("categoryName", subsubCategory);
				startActivity(i);
			}
		});
	}

	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}



}
