package com.basket.activities;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.basket.containers.AddressContainer;
import com.basket.containers.BasketSession;
import com.basket.containers.CreditCardContainer;
import com.basket.fragments.CreditCardButton;
import com.basket.fragments.SelectAddressButton;
import com.basket.general.BuyEvent;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.Order;
import com.basket.general.SelectedCreditCard;
import com.basket.general.SelectedShippingAddress;
import com.basket.lists.Products_In_Checkout_List_View;
import com.basket.restrequest.PlaceOrderRequest;
import com.example.basket.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class CheckoutActivity extends Activity {
	public static boolean changeCreditCard =false;
	public static boolean changeShippingAddressCard = false;
	private Fragment items,selCardFrag,selShipFrag;
	private Order orderToPlace;
	private SpiceManager spiceManager = new SpiceManager(CarJsonSpringAndroidSpiceService.class);


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);
		int number = getIntent().getIntExtra("basketNum", -1);
		List<BuyEvent>pro = BasketSession.getUser().getBaskets().get(number).getBuyEvents();
		double total =0;
		for (BuyEvent e :pro)
		{
			total+=e.getPrice()*e.getitem_quantity();
		}
		TextView t =(TextView)this.findViewById(R.id.totaltextview);
		t.setText("$"+Double.toString(total));

		FragmentManager fragMan = this.getFragmentManager();
		Fragment pickCardButtonFrag = fragMan.findFragmentById(R.id.creditcardfieldplaceholder);
		if(pickCardButtonFrag==null){
			pickCardButtonFrag = new CreditCardButton();
			fragMan.beginTransaction().add(R.id.creditcardfieldplaceholder, pickCardButtonFrag).commit();
		}
		Fragment pickAddressButtonFrag = fragMan.findFragmentById(R.id.shippingaddressfieldplaceholder);
		if(pickAddressButtonFrag==null){
			pickAddressButtonFrag = new SelectAddressButton();
			fragMan.beginTransaction().add(R.id.shippingaddressfieldplaceholder, pickAddressButtonFrag).commit();
		}
		items = fragMan.findFragmentById(R.id.productsForCheckoutPlaceholder);

		if(items==null){
			items = new Products_In_Checkout_List_View();
			fragMan.beginTransaction().add(R.id.productsForCheckoutPlaceholder, items).commit();
		}

		Button checkOut = (Button) findViewById(R.id.CheckOut);
		checkOut.setOnClickListener(new OnClickListener()
		{


			public void onClick(View arg0) 
			{
				Products_In_Checkout_List_View list = (Products_In_Checkout_List_View) items;
				if (CreditCardContainer.paymentSelection==null || AddressContainer.shippingSelection==null || list.getProducts2().size()==0)
				{

				}
				else
				{
					if(!spiceManager.isStarted())
					{
						Date now = new Date();
						
						orderToPlace = new Order();
						orderToPlace.setsDate(now.toString());

						orderToPlace.setAccount(1);
						orderToPlace.setBuyEvents(list.getProducts2());
						orderToPlace.setCreditCard(CreditCardContainer.paymentSelection);
						orderToPlace.setDay(1);
						orderToPlace.setMonth(5);
						orderToPlace.setYear(2005);
						orderToPlace.setShipAdress(AddressContainer.shippingSelection);
						spiceManager.start(CheckoutActivity.this);
						spiceManager.execute(new PlaceOrderRequest(orderToPlace,BasketSession.getUser(),getIntent().getIntExtra("basketNum", 0)), new PlaceOrderListener());
					}
				}
			}

		});

	}
	@Override
	protected void onStart(){
		super.onStart();
		if(changeCreditCard){
			this.changeCreditCardSelection();
			changeCreditCard = false;
		}
		if(changeShippingAddressCard){
			this.changeShippingAddressSelection();
			changeShippingAddressCard = false;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.checkout, menu);
		return true;


	}

	public void changeCreditCardSelection() {
		FragmentManager fragMan = this.getFragmentManager();
		selCardFrag = new SelectedCreditCard();
		fragMan.beginTransaction().replace(R.id.creditcardfieldplaceholder, selCardFrag).commit();
	}

	public void changeShippingAddressSelection() {
		FragmentManager fragMan = this.getFragmentManager();
		selShipFrag = new SelectedShippingAddress();
		fragMan.beginTransaction().replace(R.id.shippingaddressfieldplaceholder, selShipFrag).commit();
	}
	private class PlaceOrderListener implements RequestListener<Boolean>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) 
			{

				Toast.makeText(CheckoutActivity.this, "Order could not be placed", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(Boolean CreatedUser) 
		{
			spiceManager.shouldStop();
			BasketSession.getUser().getUserOrders().add(orderToPlace);
			BasketSession.getUser().getBaskets().remove(getIntent().getIntExtra("basketNum", 0));
			Toast.makeText(CheckoutActivity.this, "Order placed", Toast.LENGTH_SHORT).show();
			CheckoutActivity.this.finish();
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}


	}

}
