package com.example.basket;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class UserListAdapter extends ArrayAdapter<UserAccount>
{private Context context;

public UserListAdapter(Context context, int resource,
		List<UserAccount> objects) 
{
	super(context, 0, objects);
	this.context=context;
	// TODO Auto-generated constructor stub
}

public View getView(int pos,View convertView, ViewGroup parent)
{
	if (convertView==null)
	{
		convertView=((FragmentActivity)context).getLayoutInflater().inflate(R.layout.user_account_list_view, null);

	}

	return convertView;

}
}
