package com.basket.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.basket.containers.AdminSession;
import com.basket.general.Adress;
import com.basket.general.User;
import com.example.basket.R;

public class EditSAddressesActivity extends Activity {

	private ListView mSAListView;
	

	private Button mSASaveButton;
	private User theUser;

	private ArrayList<Adress> shipAddresses;

	private int user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_creditcards);

		user = this.getIntent().getIntExtra("selectedUser", 0);
		theUser = AdminSession.getEditUsers().get(user);

		shipAddresses = (theUser.getShippingAdress() == null) ? new ArrayList<Adress>()
				: theUser.getShippingAdress();

		mSAListView = (ListView) findViewById(R.id.lvCCEditList);
		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				R.layout.shippingaddress_view, shipAddresses);
		mSAListView.setAdapter(adapter);
		mSAListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
				final Adress item = (Adress) parent.getItemAtPosition(position);
				view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
					@Override
					public void run() {
						shipAddresses.remove(item);
						adapter.notifyDataSetChanged();
						view.setAlpha(1);
					}
				});
			}

		});
		//TODO change id to bSAEditSave
		mSASaveButton = (Button) findViewById(R.id.bSAEditSave);
		mSASaveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.add, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.add_icon:
	            this.shipAddresses.add(new Adress("", "", "", "", 0, ""));
	            return true;
	        case R.id.action_settings:
	            
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	private class StableArrayAdapter extends ArrayAdapter<Adress> {

		HashMap<Adress, Integer> mIdMap = new HashMap<Adress, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<Adress> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			Adress item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}
}