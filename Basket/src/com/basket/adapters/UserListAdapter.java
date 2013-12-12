package com.basket.adapters;

import java.util.List;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.basket.general.User;
import com.basket.icom.dbclass.R;

public class UserListAdapter extends ArrayAdapter<User>
{private Context context;

public UserListAdapter(Context context, int resource,
		List<User> objects) 
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
	((TextView)convertView.findViewById(R.id.usrName)).setText(this.getItem(pos).getUsername());
	

	return convertView;

}
}
