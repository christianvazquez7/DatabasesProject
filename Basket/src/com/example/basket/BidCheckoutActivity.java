package com.example.basket;

import java.util.Date;

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

import com.basket.activities.CheckoutActivity;
import com.basket.containers.AddressContainer;
import com.basket.containers.BasketSession;
import com.basket.containers.CreditCardContainer;
import com.basket.fragments.CreditCardButton;
import com.basket.fragments.SelectAddressButton;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.general.Order;
import com.basket.general.SelectedCreditCard;
import com.basket.general.SelectedShippingAddress;
import com.basket.lists.Bid_Order_List;
import com.basket.lists.Products_In_Checkout_List_View;
import com.basket.restrequest.BPlaceOrderRequest;
import com.basket.restrequest.PlaceOrderRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class BidCheckoutActivity extends Activity {
	public static boolean changeCreditCard =false;
	public static boolean changeShippingAddressCard = false;
	private Fragment items,selCardFrag,selShipFrag;
	private Order orderToPlace;
	private SpiceManager spiceManager = new SpiceManager(CarJsonSpringAndroidSpiceService.class);
	private double total =0;
	private int number;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);
		
		total=BasketSession.getBidCheckout().getWinningBid().getAmmount();
		
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
			items = new Bid_Order_List();
			fragMan.beginTransaction().add(R.id.productsForCheckoutPlaceholder, items).commit();
		}

		Button checkOut = (Button) findViewById(R.id.CheckOut);
		checkOut.setOnClickListener(new OnClickListener()
		{


			public void onClick(View arg0) 
			{
				Bid_Order_List list = (Bid_Order_List) items;
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
						orderToPlace.setBidEvent(BasketSession.getBidCheckout());
						orderToPlace.setCreditCard(CreditCardContainer.paymentSelection);
						orderToPlace.setDay(1);
						orderToPlace.setMonth(5);
						orderToPlace.setYear(2005);
						java.util.Date dt = new java.util.Date();

						java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							
						String currentTime = sdf.format(dt);
						orderToPlace.setShipAdress(AddressContainer.shippingSelection);
						spiceManager.start(BidCheckoutActivity.this);
						spiceManager.execute(new BPlaceOrderRequest(orderToPlace,BasketSession.getUser().getUserId(),CreditCardContainer.paymentSelection.getCardId(),CreditCardContainer.paymentSelection.getBilling().getAid(),AddressContainer.shippingSelection.getAid(),currentTime,total,BasketSession.getBidCheckout().getId()), new PlaceOrderListener());
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
			CheckoutActivity.changeCreditCard = false;
			BidCheckoutActivity.changeCreditCard = false;

		}
		if(changeShippingAddressCard){
			this.changeShippingAddressSelection();
			BidCheckoutActivity.changeShippingAddressCard = false;
			CheckoutActivity.changeShippingAddressCard =false;
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

				Toast.makeText(BidCheckoutActivity.this, "Order could not be placed", Toast.LENGTH_SHORT).show();
			}
			spiceManager.shouldStop();
		}

		@Override
		public void onRequestSuccess(Boolean CreatedUser) 
		{
			spiceManager.shouldStop();
			BasketSession.getUser().getUserOrders().add(orderToPlace);
			Toast.makeText(BidCheckoutActivity.this, "Order placed", Toast.LENGTH_SHORT).show();
			BidCheckoutActivity.this.finish();
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}


	}

}