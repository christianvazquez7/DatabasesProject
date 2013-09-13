package com.example.basket;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class UserListFragmet extends ListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ArrayList<UserAccount> foundProducts= new ArrayList<UserAccount>();

		foundProducts.add(new UserAccount());
		foundProducts.add(new UserAccount());
		foundProducts.add(new UserAccount());
		foundProducts.add(new UserAccount());
		foundProducts.add(new UserAccount());
		
		getActivity().setTitle("Product List");
		UserListAdapter adapter = new UserListAdapter(this.getActivity(),0,foundProducts);
		this.setListAdapter(adapter);
	}

	public void onListItemClick(ListView l, View v, int pos ,  long id )
	{
		Intent userAccountEdit = new Intent(this.getActivity(),UserEditActivity.class);
		this.getActivity().startActivity(userAccountEdit);
	}
	

}
