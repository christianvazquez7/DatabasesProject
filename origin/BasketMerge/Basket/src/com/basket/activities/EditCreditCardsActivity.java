package com.basket.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.basket.containers.AdminSession;
import com.basket.general.CreditCard;
import com.basket.general.User;
import com.example.basket.R;

public class EditCreditCardsActivity extends Activity {

	private ListView mCCListView;
	private Button mCCSaveButton;
	private User theUser;

	private ArrayList<CreditCard> creditCards;

	private int user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_creditcards);

		user = this.getIntent().getIntExtra("selectedUser", 0);
		theUser = AdminSession.getEditUsers().get(user);

		creditCards = theUser.getCreditCards();

		mCCListView = (ListView) findViewById(R.id.lvCCEditList);
		
		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				R.layout.creditcard_view, creditCards);
		mCCListView.setAdapter(adapter);
		
		mCCListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
				final CreditCard item = (CreditCard) parent.getItemAtPosition(position);
				final int selectedCC = position;
				view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
					@Override
					public void run() {
						Intent newIntent = new Intent(EditCreditCardsActivity.this, EditSingleCCActivity.class);
						newIntent.putExtra("selectedUser", user);
						newIntent.putExtra("selectedCreditCard", selectedCC);
						startActivity(newIntent);
					}
				});
			}
		});

		mCCSaveButton = (Button) findViewById(R.id.bCCEditSave);
		mCCSaveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				theUser.setCreditCards(creditCards);

			}
		});
	}

	private class StableArrayAdapter extends ArrayAdapter<CreditCard> {

		HashMap<CreditCard, Integer> mIdMap = new HashMap<CreditCard, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<CreditCard> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			CreditCard item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}
}