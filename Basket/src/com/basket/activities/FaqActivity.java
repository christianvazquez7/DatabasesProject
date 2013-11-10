package com.basket.activities;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.basket.adapters.ExpandableListAdapter;
import com.example.basket.R;
import com.example.basket.R.id;
import com.example.basket.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;
public class FaqActivity extends Activity {
	List<String> groupList;
	List<String> childList;
	Map<String, List<String>> laptopCollection;
	ExpandableListView expListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		createGroupList();

		createCollection();

		expListView = (ExpandableListView) findViewById(R.id.laptop_list);
		final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
				this, groupList, laptopCollection);
		expListView.setAdapter(expListAdapter);

		setGroupIndicatorToRight();

		expListView.setOnChildClickListener(new OnChildClickListener() {

			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				final String selected = (String) expListAdapter.getChild(
						groupPosition, childPosition);
				Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
				.show();

				return true;
			}
		});
	}

	private void createGroupList() {
		groupList = new ArrayList<String>();
		groupList.add("How to checkout");
		groupList.add("How to make a new basket");
		groupList.add("How to view categories");
		groupList.add("How to add to basket");
		groupList.add("How to access admin settings");
	}

	private void createCollection() {
		// preparing laptops collection(child)
		String[] hpModels = { "In your basket press checkout and then the checkout button." };
		String[] hclModels = { "Admin settings are only available to administrators" };
		String[] lenovoModels = { "On the home page swipe left" };
		String[] sonyModels = { "In the baskets page press the menu button" };
		String[] dellModels = { "Press add to basket in product page" };

		laptopCollection = new LinkedHashMap<String, List<String>>();

		for (String laptop : groupList) {
			if (laptop.equals("How to checkout")) {
				loadChild(hpModels);
			} else if (laptop.equals("How to add to basket"))
				loadChild(dellModels);
			else if (laptop.equals("How to make a new basket"))
				loadChild(sonyModels);
			else if (laptop.equals("How to access admin settings"))
				loadChild(hclModels);
			else
				loadChild(lenovoModels);

			laptopCollection.put(laptop, childList);
		}
	}

	private void loadChild(String[] laptopModels) {
		childList = new ArrayList<String>();
		for (String model : laptopModels)
			childList.add(model);
	}

	private void setGroupIndicatorToRight() {
		/* Get the screen width */
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;

		expListView.setIndicatorBounds(width - getDipsFromPixel(35), width
				- getDipsFromPixel(5));
	}

	// Convert pixel to dip
	public int getDipsFromPixel(float pixels) {
		// Get the screen's density scale
		final float scale = getResources().getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		return (int) (pixels * scale + 0.5f);
	}



}