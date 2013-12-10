package com.basket.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.basket.adapters.UserListAdapter;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.User;
import com.basket.general.UserList;
import com.basket.lists.UserListFragmet;
import com.basket.restrequest.AccountRequestForAdmin;
import com.example.basket.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class SearchAccountActivity extends FragmentActivity {

	private SpiceManager spiceManager=  new SpiceManager(CarJsonSpringAndroidSpiceService.class);
	private UserListFragmet userList;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_search);
		overridePendingTransition(R.anim.enter, R.anim.leave);
		userList = (UserListFragmet) this.getSupportFragmentManager().findFragmentById(R.id.accountListContainer);

		if (userList==null){
			userList=new UserListFragmet();
			this.getSupportFragmentManager().beginTransaction().add(R.id.accountListContainer, userList).commit();
		}

		Button searchButton=(Button)this.findViewById(R.id.accountSearchbtn);
		searchButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0) 
			{
				if (!spiceManager.isStarted())
				{
					spiceManager.start(SearchAccountActivity.this);
					String userName = ((TextView)findViewById(R.id.accountSearch)).getText().toString();
					Log.d("Username", userName);
					spiceManager.execute(new AccountRequestForAdmin(userName), new AdminSearchListener());
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_account, menu);
		return true;
	}
	private class AdminSearchListener implements RequestListener<UserList>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				Toast.makeText(SearchAccountActivity.this, "No User was found with the given username", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(UserList FoundUsers) 
		{
			spiceManager.shouldStop();

			for (User u : FoundUsers.getResults()){
				Log.d("try",u.toString());
			}

			userList.setList(FoundUsers.getResults());
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}


	}
	protected void onResume() 
	{
		super.onResume();
		((UserListAdapter)userList.getListAdapter()).notifyDataSetChanged();
	}

}
