package com.basket.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;

import com.basket.activities.BasketActivity;
import com.basket.activities.CategoryPageActivity;
import com.basket.activities.ChooseSubSubCategoryActivity;
import com.basket.activities.PicknickActivity;
import com.basket.activities.ProductFragmentActivity;
import com.basket.adapters.SectionListAdapter;
import com.basket.general.Section;
import com.example.basket.R;

public class SlidingMenuFragment extends Fragment implements ExpandableListView.OnChildClickListener {

	private ExpandableListView sectionListView;
	private Animation animSlideup, animSlidedown;
	List<Section> sectionList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		animSlideup = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
				R.anim.slide_up); 
		animSlidedown = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
				R.anim.slide_down); 

		sectionList = createMenu();

		View view = inflater.inflate(R.layout.slidingmenu_fragment, container, false);
		this.sectionListView = (ExpandableListView) view.findViewById(R.id.slidingmenu_view);
		this.sectionListView.setGroupIndicator(null);

		SectionListAdapter sectionListAdapter = new SectionListAdapter(this.getActivity(), sectionList);

		this.sectionListView.setAdapter(sectionListAdapter); 

		//		this.sectionListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
		//			@Override
		//			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
		//				//TODO
		//				parent.setAnimation(animSlidedown);
		//				return parent.expandGroup(groupPosition);
		//			}
		//		});

		this.sectionListView.setOnChildClickListener(this);

		int count = sectionListAdapter.getGroupCount();
		for (int position = 0; position < count; position++) {
			this.sectionListView.expandGroup(position);
		}

		return view;
	}

	private List<Section> createMenu() {
		List<Section> sectionList = new ArrayList<Section>();

		Section oGeneralSection = new Section("General");
		oGeneralSection.addSectionItem(701, "Picknick", "monotone_arrow_right");
		oGeneralSection.addSectionItem(702, "Search Products", "monotone_arrow_right");
		oGeneralSection.addSectionItem(703, "My Basket", "monotone_arrow_right");


		Section oCategoriesSection = new Section("Categories");
		oCategoriesSection.setBold(true);


		Section oElectronicsSection = new Section("Electronics");
		oElectronicsSection.addSectionItem(101, "TV", "monotone_arrow_right");
		oElectronicsSection.addSectionItem(102, "Audio", "monotone_arrow_right");
		oElectronicsSection.addSectionItem(103, "Phones", "monotone_arrow_right");
		oElectronicsSection.addSectionItem(104, "Cameras", "monotone_arrow_right");
		oElectronicsSection.addSectionItem(104, "Video", "monotone_arrow_right");

		Section oComputersSection = new Section("Computers");
		oComputersSection.addSectionItem(201, "Laptops", "monotone_arrow_right");
		oComputersSection.addSectionItem(202, "Desktops", "monotone_arrow_right");
		oComputersSection.addSectionItem(203, "Tablets", "monotone_arrow_right");
		oComputersSection.addSectionItem(204, "Printers", "monotone_arrow_right");

		Section oClothingSection = new Section("Clothing");
		oClothingSection.addSectionItem(301, "Children", "monotone_arrow_right");
		oClothingSection.addSectionItem(302, "Men", "monotone_arrow_right");
		oClothingSection.addSectionItem(303, "Women", "monotone_arrow_right");

		Section oSportsSection = new Section("Sports");
		oSportsSection.addSectionItem(401, "Bicycles", "monotone_arrow_right");
		oSportsSection.addSectionItem(402, "Fishing", "monotone_arrow_right");
		oSportsSection.addSectionItem(403, "Baseball", "monotone_arrow_right");
		oSportsSection.addSectionItem(404, "Golf", "monotone_arrow_right");
		oSportsSection.addSectionItem(405, "Basketball", "monotone_arrow_right");

		Section oBooksSection = new Section("Books");
		oBooksSection.addSectionItem(501, "Children", "monotone_arrow_right");
		oBooksSection.addSectionItem(502, "Fiction", "monotone_arrow_right");
		oBooksSection.addSectionItem(503, "Technology", "monotone_arrow_right");
		oBooksSection.addSectionItem(504, "Business", "monotone_arrow_right");

		Section oShoesSection = new Section("Shoes");

		oShoesSection.addSectionItem(601, "Children", "monotone_arrow_right");
		oShoesSection.addSectionItem(602, "Women", "monotone_arrow_right");
		oShoesSection.addSectionItem(603, "Men", "monotone_arrow_right");

		sectionList.add(oGeneralSection);
		sectionList.add(oCategoriesSection);
		sectionList.add(oBooksSection);
		sectionList.add(oElectronicsSection);
		sectionList.add(oComputersSection);
		sectionList.add(oClothingSection);
		sectionList.add(oShoesSection);
		sectionList.add(oSportsSection);


		return sectionList;
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {

		if((int)id == 302 || (int)id == 303 || (int)id == 401){
			//TODO
			if((int)id == 302){
				ArrayList<String> menClothSubCatList = new ArrayList<String>();
				menClothSubCatList.add("Shirts");
				menClothSubCatList.add("Pants");
				menClothSubCatList.add("Socks");
				Intent i = new Intent(getActivity(), ChooseSubSubCategoryActivity.class);
				i.putStringArrayListExtra("subcategoryList", menClothSubCatList);
				startActivity(i);
			}
			else if((int)id == 303){
				ArrayList<String> womenClothSubCatList = new ArrayList<String>();
				womenClothSubCatList.add("Shirts");
				womenClothSubCatList.add("Pants");
				womenClothSubCatList.add("Dresses");
				Intent i = new Intent(getActivity(), ChooseSubSubCategoryActivity.class);
				i.putStringArrayListExtra("subcategoryList", womenClothSubCatList);
				startActivity(i);
			}
			else if((int)id == 401){
				ArrayList<String> bicyclesSubCatList = new ArrayList<String>();
				bicyclesSubCatList.add("Frames");
				bicyclesSubCatList.add("Wheels");
				bicyclesSubCatList.add("Helmets");
				bicyclesSubCatList.add("Parts");
				Intent i = new Intent(getActivity(), ChooseSubSubCategoryActivity.class);
				i.putStringArrayListExtra("subcategoryList", bicyclesSubCatList);
				startActivity(i);
			}
		}
		else if((int)id < 700){
			Intent cp = new Intent(getActivity(), CategoryPageActivity.class);
			String catName = this.sectionList.get(groupPosition).getSectionItems()
					.get(childPosition).getTitle();
			cp.putExtra("categoryName", catName);
			startActivity(cp);
		}
		switch ((int)id) {
		case 701:
			Intent myPicknickIntent = new Intent(getActivity(),PicknickActivity.class);
			startActivity(myPicknickIntent);
			break;
		case 702:
			Intent pl = new Intent(getActivity(),ProductFragmentActivity.class);
			startActivity(pl);
			break;
		case 703:
			Intent b = new Intent(getActivity(),BasketActivity.class);
			startActivity(b);
			break;
		case 103:
			//TODO
			break;
		case 104:
			//TODO
			break;
		case 105:
			//TODO
			break;
		case 106:
			//TODO
			break;
		case 201:
			//TODO
			break;
		case 202:
			//TODO
			break;
		case 203:
			//TODO
			break;
		case 204:
			//TODO
			break;
		}

		return false;
	}
}
