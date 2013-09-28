package com.basket.checkout;

import java.util.ArrayList;
import java.util.HashMap;

public class AddressContainer {
	
	public static Address shippingSelection;
	private static AddressContainer sessionAddressContainer;
	public static ArrayList<Address> userAddresses;
	private AddressContainer(){
		userAddresses=new ArrayList<Address>();
		userAddresses.add(new Address("123 cat avenue"));
		shippingSelection = null;
	}
	public static AddressContainer getAddressContainer(){
		if (sessionAddressContainer==null){
			sessionAddressContainer=new AddressContainer();
			
		}
		
		return sessionAddressContainer;
	}
}
