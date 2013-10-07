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
import com.basket.general.Adress;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.User;
import com.basket.lists.EditShippingAddressListFragment;
import com.basket.restrequest.UpdateUserRequest;
import com.example.basket.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class EditSAddressesActivity extends FragmentActivity {

	private EditShippingAddressListFragment lf;
	private Button mSASaveButton;
	private User theUser;
	private ArrayList<Adress> shipAddresses;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_saddresses);

		theUser = BasketSession.getUser();
		shipAddresses = (theUser.getShippingAdress() == null) ? new ArrayList<Adress>(): theUser.getShippingAdress();

		FragmentManager fm = this.getSupportFragmentManager();
		lf = (EditShippingAddressListFragment) fm.findFragmentById(R.id.lvSAEditList);
		if(lf == null){
			lf = new EditShippingAddressListFragment();
			fm.beginTransaction().add(R.id.lvSAEditList, lf).commit();
		}


		mSASaveButton = (Button) findViewById(R.id.addShippingAddressButton);
		mSASaveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				shipAddresses.add(new Adress());
				Intent newIntent = new Intent(EditSAddressesActivity.this, EditSingleSAActivity.class);
				newIntent.putExtra("selectedUser", EditSAddressesActivity.this.getIntent().getIntExtra("selectedUser", 0));
				newIntent.putExtra("selectedShipAdd", shipAddresses.size()-1);
				newIntent.putExtra("createdNewAddress", "yes");
				startActivity(newIntent);
			}
		});
	}

	
	
	@Override
	protected void onResume(){
		super.onResume();
		if(lf!=null){
			((ArrayAdapter<Adress>)lf.getListAdapter()).notifyDataSetChanged();
			if(!spiceManager.isStarted())

			spiceManager.start(EditSAddressesActivity.this);
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

				Toast.makeText(EditSAddressesActivity.this, "Update Unsuccesful", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(Boolean edit) {
			spiceManager.shouldStop();
			Toast.makeText(EditSAddressesActivity.this, "Successfully updated addresses", Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}
	}
}