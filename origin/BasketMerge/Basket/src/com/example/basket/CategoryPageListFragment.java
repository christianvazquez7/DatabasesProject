package com.example.basket;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CategoryPageListFragment extends ListFragment {
	private ArrayList<Products> mProducts;
	
	ListView mProductListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mProducts.add(new Products());
		mProducts.add(new Products());
		mProducts.add(new Products());
		mProducts.add(new Products());
		mProducts.add(new Products());
		mProducts.add(new Products());
		mProducts.add(new Products());
		mProducts.add(new Products());
		
		super.onCreate(savedInstanceState);
		getActivity().setTitle("Category Title");
		
		ProductAdapter adapter = new ProductAdapter(this.getActivity(),mProducts);
		setListAdapter(adapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id){
		 String selectedValue = (String) getListAdapter().getItem(position);
		 Toast.makeText(getActivity(), selectedValue, Toast.LENGTH_SHORT).show();
		 //in this part when you click it is supposed to go to a product page

	}
}
