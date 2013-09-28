package com.basket.checkout;

import com.application.settings.CreditCardButton;
import com.application.settings.Products_In_Checkout_List_View;
import com.example.basket.R;
import com.example.basket.R.layout;
import com.example.basket.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.view.Menu;

public class CheckoutActivity extends Activity {
	public static boolean changeCreditCard =false;
	public static boolean changeShippingAddressCard = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);
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
		Fragment items = fragMan.findFragmentById(R.id.productsForCheckoutPlaceholder);

		if(items==null){
			items = new Products_In_Checkout_List_View();
			fragMan.beginTransaction().add(R.id.productsForCheckoutPlaceholder, items).commit();
		}

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
		Fragment selCardFrag = new SelectedCreditCard();
		fragMan.beginTransaction().replace(R.id.creditcardfieldplaceholder, selCardFrag).commit();
	}

	public void changeShippingAddressSelection() {
		FragmentManager fragMan = this.getFragmentManager();
		Fragment selCardFrag = new SelectedShippingAddress();
		fragMan.beginTransaction().replace(R.id.shippingaddressfieldplaceholder, selCardFrag).commit();
	}

}
