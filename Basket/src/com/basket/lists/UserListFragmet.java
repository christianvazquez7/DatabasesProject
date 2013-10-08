package com.basket.lists;

import java.util.ArrayList;
import java.util.List;

import com.basket.activities.UserEditActivity;
import com.basket.adapters.UserListAdapter;
import com.basket.containers.AdminSession;
import com.basket.general.User;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

public class UserListFragmet extends ListFragment {

	
	private List<User> userList= new ArrayList<User>();
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AdminSession.setEditUsers(userList);
		getActivity().setTitle("Product List");
		UserListAdapter adapter = new UserListAdapter(this.getActivity(),0,userList);
		this.setListAdapter(adapter);
	}

	public void onListItemClick(ListView l, View v, int pos ,  long id )
	{
		Intent userAccountEdit = new Intent(this.getActivity(),UserEditActivity.class);
		userAccountEdit.putExtra("selectedUser", pos);
		this.getActivity().startActivityForResult(userAccountEdit, 1);
	}

	public void setList(List<User> results) 
	{
		this.userList.clear();
		for (User usr: results)
			userList.add(usr);
		((UserListAdapter)this.getListAdapter()).notifyDataSetChanged();
	}
	
	public void onResume() {
		  //onResume happens after onStart and onActivityCreate
		LayoutTransition l = new LayoutTransition();
		l.enableTransitionType(LayoutTransition.CHANGING);
		l.setDuration(500);
		ViewGroup viewGroup = this.getListView();
		viewGroup.setLayoutTransition(l);
		  super.onResume() ; 
		}
	

}
