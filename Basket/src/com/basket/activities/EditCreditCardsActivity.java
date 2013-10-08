package com.basket.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.basket.containers.BasketSession;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.CreditCard;
import com.basket.general.User;
import com.basket.lists.EditCreditCardListFragment;
import com.basket.restrequest.UpdateUserRequest;
import com.example.basket.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class EditCreditCardsActivity extends FragmentActivity {

	private EditCreditCardListFragment cclist;
	private Button mCCSaveButton;
	private User theUser;
	private ArrayList<CreditCard> creditCards;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_creditcards);

		theUser = BasketSession.getUser();
		creditCards = theUser.getCreditCards();

		FragmentManager supportMan = this.getSupportFragmentManager();
		cclist = (EditCreditCardListFragment) supportMan.findFragmentById(R.id.lvCCEditListContainer);
		if(cclist == null){
			cclist=new EditCreditCardListFragment();
			supportMan.beginTransaction().add(R.id.lvCCEditListContainer, cclist).commit();
		}

		mCCSaveButton = (Button) findViewById(R.id.addCreditCardButton);
		mCCSaveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				creditCards.add(new CreditCard());
				Intent newIntent = new Intent(EditCreditCardsActivity.this, EditSingleCCActivity.class);
				newIntent.putExtra("selectedUser", EditCreditCardsActivity.this.getIntent().getIntExtra("selectedUser", 0));
				newIntent.putExtra("selectedCreditCard", creditCards.size()-1);
				newIntent.putExtra("createdNewCard", true);
				startActivity(newIntent);
			}
		});
	}
	
	
	@Override
	protected void onResume(){
		super.onResume();
		if(cclist != null){
			((ArrayAdapter<CreditCard>)cclist.getListAdapter()).notifyDataSetChanged();
			if(!spiceManager.isStarted())
			spiceManager.start(EditCreditCardsActivity.this);
			UpdateUserRequest JsonSpringAndroidRequest = new UpdateUserRequest(theUser);
			spiceManager.execute(JsonSpringAndroidRequest, "user_edit", DurationInMillis.ALWAYS_EXPIRED, new UserEditListener());
		}
		
	}
	private SpiceManager spiceManager= new SpiceManager(CarJsonSpringAndroidSpiceService.class);


	
	private class UserEditListener implements RequestListener<Boolean>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				Toast.makeText(EditCreditCardsActivity.this, "Update Unsuccesful", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(Boolean edit) {
			spiceManager.shouldStop();
			Toast.makeText(EditCreditCardsActivity.this, "Successfully updated credit cards", Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}
	}
}